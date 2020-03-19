package com.example.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 @author yulshi
 @create 2020/03/19 09:21
 */
public class TestZookeeper {

  private final ZooKeeper zkClient;

  public TestZookeeper() throws IOException {
    String connectString = "localhost:2181";
    int sessionTimeout = 1000;
    zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
      @Override
      public void process(WatchedEvent event) {
        printRoot();
      }
    });
  }

  public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

    TestZookeeper test = new TestZookeeper();
    test.createNode();
    test.printRoot();
    Thread.sleep(Long.MAX_VALUE);

  }

  public void createNode() throws KeeperException, InterruptedException {
    String path = zkClient.create("/greeting",
            "hello_ephemeral".getBytes(),
            ZooDefs.Ids.OPEN_ACL_UNSAFE,
            CreateMode.PERSISTENT);
    System.out.println(path);
  }

  public void printRoot() {
    try {
      List<String> children = zkClient.getChildren("/", true);
      children.forEach(System.out::println);
    } catch (KeeperException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
