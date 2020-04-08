package org.example.redis.secondkill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 @author yulshi
 @create 2020/03/20 22:58
 */
public class SecondKill implements Closeable {

  private static String PREFIX = "sc:";
  private static String SUFFIX_QT = ":qt"; // 库存数量
  private static String SUFFIX_USERS = ":users"; // 已经抢到的用户的集合

  private final JedisPool pool;
  private final String prodId;
  private final String quantityKey;
  private final String usersKey;

  public SecondKill(String prodId) {
    this.pool = new JedisPool();
    this.prodId = prodId;
    this.quantityKey = PREFIX + prodId + SUFFIX_QT;
    this.usersKey = PREFIX + prodId + SUFFIX_USERS;
  }

  public boolean grab(String userId) {

    try (Jedis jedis = pool.getResource()) {

      jedis.watch(quantityKey, usersKey);

      // 查看该用户是否已经抢到货了
      Boolean alreadyGrabbed = jedis.sismember(usersKey, userId);
      if (alreadyGrabbed) {
        System.out.println("您已经抢到了，请关注下一次活动");
        return false;
      }

      // 判断还有没有货
      String quantity = jedis.get(quantityKey);
      if (quantity == null) {
        System.out.println("请检查产品ID是否正确！！！");
        return false;
      }

      int qt = Integer.valueOf(quantity);
      if (qt <= 0) {
        System.out.println("货已被抢完，敬请关注下一次秒杀");
        return false;
      }

      // 开始抢
      Transaction multi = jedis.multi();
      multi.sadd(usersKey, userId);
      multi.decr(quantityKey);
      List<Object> results = multi.exec();
      if (results == null || results.isEmpty()) {
        System.out.println("秒杀失败");
        return false;
      } else {
        System.out.println("秒杀成功");
        return true;
      }

    }
  }

  @Override
  public void close() throws IOException {
    pool.close();
  }

  public static void main(String[] args) {
    try {
      try(SecondKill sc = new SecondKill("1001")) {
        System.out.println(sc.grab("Timmy"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
