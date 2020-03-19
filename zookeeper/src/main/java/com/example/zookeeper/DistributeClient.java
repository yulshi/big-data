package com.example.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 @author yulshi
 @create 2020/03/19 10:24
 */
public class DistributeClient {

  ZooKeeper zkClient;

  public static void main(String[] args) throws Exception {

    // 获取zookee连接
    DistributeClient client = new DistributeClient();
    client.getConnection();

    // 注册监听
    client.getChildren();

    // 业务逻辑处理
    client.doThings();

  }

  private void doThings() throws InterruptedException {
    Thread.sleep(Long.MAX_VALUE);
  }

  private void getChildren() throws KeeperException, InterruptedException {

    List<String> hosts = new ArrayList<>();
    List<String> children = zkClient.getChildren("/servers", true);
    for (String child : children) {
      byte[] data = zkClient.getData("/servers/" + child, false, null);
      hosts.add(new String(data));
    }
    System.out.println(hosts);

  }

  private void getConnection() throws IOException {

    zkClient = new ZooKeeper("localhost:2181", 2000, new Watcher() {
      @Override
      public void process(WatchedEvent event) {
        try {
          getChildren();
        } catch (KeeperException e) {
          e.printStackTrace();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
