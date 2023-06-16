package com.hygge.shop.mall.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: shop
 * @description: rabbitmq配置类
 * @author: hygge
 * @create: 2023/06/13
 */
@Configuration
public class RabbitConfig {

  @Bean
  public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
    RabbitTemplate rabbitTemplate = new RabbitTemplate();
    rabbitTemplate.setConnectionFactory(connectionFactory);
    //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
    rabbitTemplate.setMandatory(true);

    rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
      /**
       * 不管成功于否，都会调用。
       */
      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("ConfirmCallback:     "+"相关数据："+correlationData);
        System.out.println("ConfirmCallback:     "+"确认情况："+ack);
        System.out.println("ConfirmCallback:     "+"原因："+cause);
      }
    });

    rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
      /**
       * 如果消息未从路由成功发送到队列那么会走这个回调，这里会把消息的整个明细返回
       * 也就是说出错了，才会调用哦。
       */
      @Override
      public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("ReturnCallback:     "+"消息："+returnedMessage.getMessage());
        System.out.println("ReturnCallback:     "+"回应码："+returnedMessage.getReplyCode());
        System.out.println("ReturnCallback:     "+"回应信息："+returnedMessage.getReplyText());
        System.out.println("ReturnCallback:     "+"交换机："+returnedMessage.getExchange());
        System.out.println("ReturnCallback:     "+"路由键："+returnedMessage.getRoutingKey());
      }
    });

    return rabbitTemplate;
  }
}
