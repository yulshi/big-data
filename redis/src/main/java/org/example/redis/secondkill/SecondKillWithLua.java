package org.example.redis.secondkill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Random;

/**
 @author yulshi
 @create 2020/03/21 18:34
 */
public class SecondKillWithLua {

  public static void main(String[] args) {
    String prodid = "1001";
    String userid = "" + new Random().nextInt(10000);

    String secKillScript = "...";

    try (JedisPool pool = new JedisPool()) {

      try (Jedis jedis = pool.getResource()) {

        String sha1 = jedis.scriptLoad(secKillScript);
        Object result = jedis.evalsha(sha1, 2, userid, prodid);

        String reString = String.valueOf(result);

        if ("0".equals(reString)) {
          System.out.println("已抢空");
        } else if ("1".equals(reString)) {
          System.out.println("抢购成功");
        } else if ("2".equals(reString)) {
          System.out.println("该用户已抢过");
        } else {
          System.out.println("抢购异常");
        }

      }

    }


  }
}
