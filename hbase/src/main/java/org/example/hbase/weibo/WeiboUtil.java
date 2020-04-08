package org.example.hbase.weibo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 @author yulshi
 @create 2020/03/29 08:57
 */
public class WeiboUtil {

  private static Configuration conf = HBaseConfiguration.create();

  // 创建命名空间
  public static void createNamespace(String ns) throws IOException {

    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Admin admin = conn.getAdmin()) {
        admin.createNamespace(NamespaceDescriptor.create(ns).build());
      }
    }

  }

  // 创建表
  public static void createTable(String tableName, int versions, String... cfs) throws IOException {

    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Admin admin = conn.getAdmin()) {
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (String cf : cfs) {
          descriptor.addFamily(
                  new HColumnDescriptor(cf)
                          .setMaxVersions(versions));
        }
        admin.createTable(descriptor);
      }
    }

  }

  // 发布微博
  public static void createData(String uid, String content) throws IOException {

    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Table contTable = conn.getTable(TableName.valueOf(Constant.CONTENT));
           Table relaTable = conn.getTable(TableName.valueOf(Constant.RELATIONS));
           Table inboxTable = conn.getTable(TableName.valueOf(Constant.INBOX))) {

        // 向微博内容表中添加数据
        String rowkey = System.currentTimeMillis() + "_" + uid;
        Put put = new Put(Bytes.toBytes(rowkey))
                .addColumn(Bytes.toBytes("info"),
                        Bytes.toBytes("content"),
                        Bytes.toBytes(content));
        contTable.put(put);

        // 获取该用户的所有fans，并插入数据到微博收件箱表
        Get get = new Get(Bytes.toBytes(uid))
                .addFamily(Bytes.toBytes("funs"));
        Cell[] cells = relaTable.get(get).rawCells();

        if (cells != null && cells.length > 0) {
          List<Put> puts = new ArrayList<>(cells.length);
          for (Cell cell : cells) {
            byte[] fanUid = CellUtil.cloneQualifier(cell);
            puts.add(new Put(fanUid)
                    .addColumn(Bytes.toBytes("info"),
                            Bytes.toBytes(uid),
                            Bytes.toBytes(rowkey)));
          }
          inboxTable.put(puts);
        }
      }

    }

  }

  /**
   * 关注用户
   * 1. 在用户关系表中
   *   a）添加操作人的attends
   *   b）添加被操作人的fans
   * 2. 在收件箱表中
   *   a）在微博内容表中获取被关注者的近期的3条数据
   *   b）在收件箱表中，添加操作人的关注者信息
   * @param uid
   * @param uids
   */
  public static void addAttend(String uid, String... uids) throws IOException {

    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Table contTable = conn.getTable(TableName.valueOf(Constant.CONTENT));
           Table relaTable = conn.getTable(TableName.valueOf(Constant.RELATIONS));
           Table inboxTable = conn.getTable(TableName.valueOf(Constant.INBOX))) {

        // 创建操作者的put对象
        List<Put> puts = new ArrayList<>();

        Put relaPut = new Put(Bytes.toBytes(uid));
        for (String attend : uids) {
          relaPut.addColumn(
                  Bytes.toBytes("attends"),
                  Bytes.toBytes(attend),
                  Bytes.toBytes(attend));
          // 创建被关注者的put对象
          Put fansPut = new Put(Bytes.toBytes(attend))
                  .addColumn(
                          Bytes.toBytes("fans"),
                          Bytes.toBytes(uid),
                          Bytes.toBytes(uid));
          puts.add(fansPut);
        }
        puts.add(relaPut);
        relaTable.put(puts);

        // 获取内容表中被关注者的rowkey，并添加到收件箱表
        Put inboxPut = new Put(Bytes.toBytes(uid));
        for (String attend : uids) {
          Scan scan = new Scan(
                  Bytes.toBytes(attend),
                  Bytes.toBytes(attend + "|"));
          ResultScanner results = contTable.getScanner(scan);
          for (Result result : results) {
            // 统一时间戳
            byte[] row = result.getRow();
            String rowkey = Bytes.toString(row);
            String[] split = rowkey.split("_");
            inboxPut.addColumn(
                    Bytes.toBytes("info"),
                    Bytes.toBytes(attend),
                    Long.parseLong(split[1]),
                    row);
          }
        }
        inboxTable.put(inboxPut);

      }
    }
  }

  /**
   * 取关用户
   * 1. 删除操作者关注的用户
   * 2. 删除取关用户的fans
   * 3. 从收件箱表中删除取关用户
   */
  public static void delAttend(String uid, String... uids) throws IOException {
    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Table relaTable = conn.getTable(TableName.valueOf(Constant.RELATIONS));
           Table inboxTable = conn.getTable(TableName.valueOf(Constant.INBOX))) {

        // 删除relations表中的数据
        List<Delete> deletes = new ArrayList<>();

        Delete relDel = new Delete(Bytes.toBytes(uid));
        for (String attend : uids) {
          // 创建操纵者的关注
          relDel.addColumns(
                  Bytes.toBytes("attends"),
                  Bytes.toBytes(attend));
          // 删除fans
          Delete fansDel = new Delete(Bytes.toBytes(attend))
                  .addColumns(Bytes.toBytes("fans"), Bytes.toBytes(attend));
          deletes.add(fansDel);
        }
        deletes.add(relDel);
        relaTable.delete(deletes);

        Delete inboxDel = new Delete(Bytes.toBytes(uid));
        for (String id : uids) {
          inboxDel.addColumns(Bytes.toBytes("info"), Bytes.toBytes(id));
        }
        inboxTable.delete(inboxDel);

      }
    }
  }


  // 获取关注用户的微博内容（初始化页面）
  public static void printInit(String uid) throws IOException {

    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Table contentTable = conn.getTable(TableName.valueOf(Constant.CONTENT));
           Table inboxTable = conn.getTable(TableName.valueOf(Constant.INBOX))) {

        Get get = new Get(Bytes.toBytes(uid));
        get.setMaxVersions();

        Result result = inboxTable.get(get);
        Cell[] cells = result.rawCells();
        List<Get> gets = new ArrayList<>();
        for (Cell cell : cells) {
          byte[] contetRow = CellUtil.cloneValue(cell);
          gets.add(new Get(contetRow));
        }

        Result[] results = contentTable.get(gets);
        for (Result res : results) {
          for (Cell cell : res.rawCells()) {
            System.out.println("RK:" + Bytes.toString(CellUtil.cloneRow(cell))
                    + "\tContent: " + Bytes.toString(CellUtil.cloneValue(cell)));
          }
        }

      }

    }

  }


  // 查看某个人所有微博内容
  public static void printData(String uid) throws IOException {
    try (Connection conn = ConnectionFactory.createConnection(conf)) {
      try (Table table = conn.getTable(TableName.valueOf(Constant.CONTENT))) {

        Scan scan = new Scan();

        // 使用filetr来过滤想要的行
        Filter rowFilter = new RowFilter(
                CompareFilter.CompareOp.EQUAL,
                new SubstringComparator(uid + "_"));
        scan.setFilter(rowFilter);

        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
          Cell[] cells = result.rawCells();
          for (Cell cell : cells) {
            System.out.println("RK:" + Bytes.toString(CellUtil.cloneRow(cell))
                    + "\tContent: " + Bytes.toString(CellUtil.cloneValue(cell)));
          }
        }

      }
    }
  }

}
