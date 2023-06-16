package com.hygge.shop.stock.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hygge.shop.stock.entity.OmsOrder;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

/**
 * @program: shop
 * @description: rabbitmq手动确认消息监听
 * @author: hygge
 * @create: 2023/06/13
 */
@Component
public class RabbitMyAckReceiver implements ChannelAwareMessageListener {

  @Override
  public void onMessage(Message message, Channel channel) throws Exception {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();

    byte[] body = message.getBody();
    ByteArrayInputStream bodyInputStream = new ByteArrayInputStream(body);
    ByteArrayOutputStream result = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    for (int length; (length = bodyInputStream.read(buffer)) != -1; ) {
      result.write(buffer, 0, length);
    }
    String objStr = result.toString("UTF-8");

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      OmsOrder omsOrder = objectMapper.readValue(objStr, OmsOrder.class);
      bodyInputStream.close();
      result.close();

      if("DirectQueue".equals(message.getMessageProperties().getConsumerQueue())) {
        System.out.println("RabbitMyAckReceiver:" + omsOrder);
        System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
      }else if("fanout.A".equals(message.getMessageProperties().getConsumerQueue())) {
        System.out.println("RabbitMyAckReceiver:" + omsOrder);
        System.out.println("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
      }
      channel.basicAck(deliveryTag, true); //第二个参数，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//          channel.basicReject(deliveryTag, true);//第二个参数，true会重新放回队列，所以需要自己根据业务逻辑判断什么时候使用拒绝
    } catch (Exception e) {
      bodyInputStream.close();
      result.close();
      channel.basicReject(deliveryTag, false);
      e.printStackTrace();
    }
  }
}
