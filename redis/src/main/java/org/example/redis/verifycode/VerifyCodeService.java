package org.example.redis.verifycode;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Closeable;
import java.io.IOException;

/**
 @author yulshi
 @create 2020/03/20 15:37
 */
public class VerifyCodeService implements Closeable {


  private final JedisPool pool;

  private final String PREFIX = "vc:";
  private final String SUFFIX_CODE = ":code";
  private final String SUFFIX_COUNT = ":count";

  public VerifyCodeService() {
    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    config.setMaxIdle(16);
    config.setMaxIdle(16);
    pool = new JedisPool(config, "localhost");
  }

  /**
   * 生成验证码
   *
   * @param mobileNumber
   * @return
   */
  public boolean generate(String mobileNumber) {

    String codeKey = PREFIX + mobileNumber + SUFFIX_CODE;
    String countKey = PREFIX + mobileNumber + SUFFIX_COUNT;

    try (Jedis jedis = pool.getResource()) {
      // 检查是否已经达到当前最大次数（三次）
      String count = jedis.get(countKey);
      if (count == null || Integer.valueOf(count) < 3) {
        // 生成验证码
        jedis.setex(codeKey, 60 * 2, generateCode(6));
        // count+1
        if (count == null) {
          jedis.setex(countKey, 60 * 60 * 24, "1");
        } else {
          jedis.incr(countKey);
        }
        return true;
      } else {
        // 超过了3次，不再生成验证码
        return false;
      }
    }
  }

  /**
   * 验证验证码
   * @param mobileNumber
   * @param code
   * @return
   */
  public boolean verify(String mobileNumber, String code) {

    try (Jedis jedis = pool.getResource()) {
      String codeKey = PREFIX + mobileNumber + SUFFIX_CODE;
      String savedCode = jedis.get(codeKey);
      return code.equals(savedCode);
    }

  }

  private String generateCode(int len) {
    StringBuilder codeBuilder = new StringBuilder();
    for (int i = 0; i < len; i++) {
      codeBuilder.append((int) (Math.random() * 10));
    }
    return codeBuilder.toString();
  }

  @Override
  public void close() throws IOException {
    pool.close();
  }

  public static void main(String[] args) {
    try {
      try (VerifyCodeService verifyCodeService = new VerifyCodeService()) {
        System.out.println(verifyCodeService.generate("13911111111"));
        //System.out.println(verifyCodeService.verify("13911111111", "118364"));
      }
    } catch (IOException e) {
      e.printStackTrace(System.out);
    }
  }

}
