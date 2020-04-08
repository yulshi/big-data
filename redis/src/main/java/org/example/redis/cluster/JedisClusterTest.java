package org.example.redis.cluster;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 @author yulshi
 @create 2020/03/21 18:06
 */
public class JedisClusterTest {

  public static void main(String[] args) {

    Set<HostAndPort> hostAndPorts = new HashSet<>();
    hostAndPorts.add(new HostAndPort("localhost", 6379));

    JedisCluster cluster = new JedisCluster(hostAndPorts);

    cluster.set("k1", "v1");
    System.out.println(cluster.get("k1"));

    cluster.close();

  }
}
