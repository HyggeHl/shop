package com.hygge.shop.stock.receiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hygge.shop.common.cache.LocalCache;
import com.hygge.shop.common.entity.MessageConfig;
import com.hygge.shop.stock.entity.OmsOrder;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @program: shop
 * @description: 库存消息直连接收者
 * @author: hygge
 * @create: 2023/06/12
 */
@Component
//@RabbitListener(queues = "DirectQueue")
@RabbitListener(queues = {MessageConfig.DirectQueue_0, MessageConfig.DirectQueue_1})
//@RabbitListener(queues = MessageConfig.DirectQueue_1)
@RabbitListener(queues = MessageConfig.DirectQueue_2)
@RabbitListener(queues = MessageConfig.DirectQueue_3)
public class OmsStockDirectReceiver {
  Logger logger = LoggerFactory.getLogger(OmsStockDirectReceiver.class);

  LocalCache localCache = new LocalCache();

  @RabbitHandler
  void getDeductStockMessage(OmsOrder omsOrder, Message message, Channel channel) throws IOException {
    try {
      int a = 10 / 0;
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }catch (Exception e) {

      Integer value = (Integer)localCache.get(String.valueOf(message.getMessageProperties().getDeliveryTag()));

      if(value == null)
        localCache.add(String.valueOf(message.getMessageProperties().getDeliveryTag()), 1, 10000);
      else if(value == 3) {
        logger.error("消费消息失败，消息内容Tag：{}", message.getMessageProperties().getDeliveryTag());
        logger.warn("已加入重试表、、、、、、、");
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        return;
      }else {
        localCache.add(String.valueOf(message.getMessageProperties().getDeliveryTag()), ++value, 10000);
      }
      logger.info("签收失败，消息内容Tag：{}", message.getMessageProperties().getDeliveryTag());
      throw new RuntimeException("签收异常");
    }
  }

  @RabbitHandler
  public void onMessage(MessageConfig messageCont, Message message, Channel channel) throws InterruptedException, IOException {

    // 这里随机睡一会，模拟业务处理时候的耗时
    long l = new Random(1000).nextLong();
    TimeUnit.MILLISECONDS.sleep(l);

    logger.info("[{}][OmsStockDirectReceiver onMessage][线程编号:{} 消息内容：{}]", LocalDateTime.now(), Thread.currentThread().getId(), messageCont);
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
  }

  @RabbitHandler
  void getDeductStockMessage(String omsOrderStr) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    OmsOrder omsOrder = objectMapper.readValue(omsOrderStr, OmsOrder.class);
    System.out.println("getDeductStockMessage消费者收到消息  : " + omsOrder);
  }
}
