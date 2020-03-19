package com.example.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/19 10:12
 */
public class DistributeServer {

  ZooKeeper zkClient = null;

  public static void main(String[] args) throws Exception {

    String hostname = args[0];

    // 连接zookeeper集群
    DistributeServer server = new DistributeServer();
    server.getConnection();

    // 注册节点
    server.register(hostname);

    // 业务逻辑处理
    server.doThings();

  }

  private void doThings() throws InterruptedException {
    Thread.sleep(Long.MAX_VALUE);
  }

  private void register(String hostname) throws KeeperException, InterruptedException {
    String path = zkClient.create("/servers/server",
            hostname.getBytes(),
            ZooDefs.Ids.OPEN_ACL_UNSAFE,
            CreateMode.EPHEMERAL_SEQUENTIAL);
    System.out.println(hostname + " is online");
  }

  private void getConnection() throws IOException {
    zkClient = new ZooKeeper("localhost:2181", 2000, new Watcher() {
      @Override
      public void process(WatchedEvent event) {

      }
    });
  }
}
