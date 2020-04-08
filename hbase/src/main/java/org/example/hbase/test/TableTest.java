package org.example.hbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

/**
 @author yulshi
 @create 2020/03/27 22:43
 */
public class TableTest implements Closeable {

  private final Connection connection;

  public TableTest() throws IOException {
    Configuration conf = HBaseConfiguration.create();
    connection = ConnectionFactory.createConnection(conf);
  }

  @Override
  public void close() throws IOException {
    if (connection != null) {
      connection.close();
    }
  }

  public boolean tableExists(String tableName) throws IOException {
    try (Admin admin = connection.getAdmin()) {
      return admin.tableExists(TableName.valueOf(tableName));
    }
  }

  public void createTable(String tableName, String... cfs) throws IOException {

    if (tableExists(tableName)) {
      System.out.println("table '" + tableName + "' exists!!!");
      return;
    }

    try (Admin admin = connection.getAdmin()) {
      HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
      for (String cf : cfs) {
        descriptor.addFamily(new HColumnDescriptor(cf));
      }
    }
  }


  public void deleteTable(String tableName) throws IOException {

    if (!tableExists(tableName)) {
      System.out.println("表不存在！！！");
      return;
    }

    try (Admin admin = connection.getAdmin()) {

      admin.disableTable(TableName.valueOf(tableName));

      admin.deleteTable(TableName.valueOf(tableName));

    }

  }

  public void putData(String tableName, String rowkey, String cf, String col, String value) throws IOException {

    try (Table table = connection.getTable(TableName.valueOf(tableName))) {

      Put put = new Put(toBytes(rowkey));
      put.addColumn(toBytes(cf), toBytes(col), toBytes(value));

      table.put(put);
    }

  }

  public void deleteData(String tableName, String rowkey, String cf, String col) throws IOException {

    try (Table table = connection.getTable(TableName.valueOf(tableName))) {

      Delete delete = new Delete(toBytes(rowkey));

      if (cf != null) {
        if (col != null) {
          delete.addColumns(toBytes(cf), toBytes(col));
        } else {
          delete.addFamily(toBytes(cf));
        }
      }

      table.delete(delete);

    }

  }

  public void scan(String tableName) throws IOException {
    try (Table table = connection.getTable(TableName.valueOf(tableName))) {
      ResultScanner scanner = table.getScanner(new Scan());
      for (Result result : scanner) {

        String rowkey = Bytes.toString(result.getRow());
        System.out.println("------" + rowkey + "---------");

        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
          System.out.print(Bytes.toString(CellUtil.cloneRow(cell)) + "\t");
          System.out.print(Bytes.toString(CellUtil.cloneFamily(cell)) + ":");
          System.out.print(Bytes.toString(CellUtil.cloneQualifier(cell)) + "\t");
          System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
        }

      }
    }
  }

  public void getData(String tableName, String rowkey, String cf, String col) throws IOException {

    try (Table table = connection.getTable(TableName.valueOf(tableName))) {

      Get get = new Get(toBytes(rowkey));
      if (cf != null) {
        if (col != null) {
          get.addColumn(toBytes(cf), toBytes(col));
        } else {
          get.addFamily(toBytes(cf));
        }
      }

      Result result = table.get(get);

      for (Cell cell : result.rawCells()) {
        System.out.print(Bytes.toString(CellUtil.cloneRow(cell)) + "\t");
        System.out.print(Bytes.toString(CellUtil.cloneFamily(cell)) + ":");
        System.out.print(Bytes.toString(CellUtil.cloneQualifier(cell)) + "\t");
        System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
      }

    }

  }

  public static void main(String[] args) {

    try (TableTest tt = new TableTest()) {

//      tt.putData("student", "1001", "info", "name", "Bailey");
//      tt.putData("student", "1001", "info", "gender", "male");
//      tt.putData("student", "1002", "info", "name", "Trudy");
//      tt.putData("student", "1002", "info", "gender", "female");
//      tt.deleteData("student", "1001", "info", "name");
//      tt.scan("student");
      tt.getData("student", "1001", "info", "name");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
