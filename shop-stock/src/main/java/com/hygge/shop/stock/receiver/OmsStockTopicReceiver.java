package com.hygge.shop.stock.receiver;

import com.hygge.shop.stock.entity.OmsOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: shop
 * @description: 库存主题消息接收
 * @author: hygge
 * @create: 2023/06/13
 */
//@Component
public class OmsStockTopicReceiver {

  @RabbitListener(queues = "topic.omsOrder")
  void getOmsOrderQueueMsg(OmsOrder omsOrder) {
    System.out.println("getOmsOrderQueueMsg消费者收到消息  : " + omsOrder);
  }

  @RabbitListener(queues = "topic.all")
  void getAllQueueMsg(OmsOrder omsOrder) {
    System.out.println("getAllQueueMsg消费者收到消息  : " + omsOrder);
  }
}
