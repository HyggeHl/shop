package com.hygge.shop.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: shop
 * @description: 消息配置
 * @author: hygge
 * @create: 2023/06/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageConfig {

  public static final String DirectQueue_0 = "DirectQueue_0";
  public static final String DirectQueue_1 = "DirectQueue_1";
  public static final String DirectQueue_2 = "DirectQueue_2";
  public static final String DirectQueue_3 = "DirectQueue_3";

  public static final int QUEUE_COUNT = 4;

  public static final String EXCHANGE = "DirectExchange";

  private int id;
  private String msg;
}
