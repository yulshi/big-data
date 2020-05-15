package com.example.netty.tcpproto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yulshi
 * @create 2020/01/23 20:52
 */
@Data
@NoArgsConstructor
public class MessageProtocol {
  private int length;
  private byte[] content;

  public MessageProtocol(byte[] content) {
    this.length = content.length;
    this.content = content;
  }
}
