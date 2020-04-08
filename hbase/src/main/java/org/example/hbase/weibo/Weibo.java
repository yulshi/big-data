package org.example.hbase.weibo;

import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/29 09:02
 */
public class Weibo {

  public void init() throws IOException {

    // 创建相关命名空间和表
    WeiboUtil.createNamespace(Constant.NAMESPACE);
    WeiboUtil.createTable(Constant.CONTENT, 1,"info");
    WeiboUtil.createTable(Constant.RELATIONS, 1, "attends", "fans");
    WeiboUtil.createTable(Constant.INBOX, 2, "info");


  }

  public static void main(String[] args) throws IOException {

    Weibo weibo = new Weibo();
//    weibo.init();

  }

}
