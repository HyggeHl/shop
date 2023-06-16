package com.hygge.shop.stock.receiver;

import com.hygge.shop.stock.entity.OmsOrder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: shop
 * @description: 扇形交换器消息消费者
 * @author: hygge
 * @create: 2023/06/13
 */
//@Component
public class OmsStockFanoutReceiver {

//  @RabbitListener(queues = "fanout.A")
  public void processA(OmsOrder omsOrder) {
    System.out.println("fanout Receiver A接收到消息 : " + omsOrder);
  }

  @RabbitListener(queues = "fanout.B")
  public void processB(OmsOrder omsOrder) {
    System.out.println("fanout Receiver B接收到消息 : " + omsOrder);
  }

  @RabbitListener(queues = "fanout.C")
  public void processC(OmsOrder omsOrder) {
    System.out.println("fanout Receiver C接收到消息 : " + omsOrder);
  }
}
