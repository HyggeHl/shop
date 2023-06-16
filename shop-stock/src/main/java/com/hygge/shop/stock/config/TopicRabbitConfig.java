package com.hygge.shop.stock.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: shop
 * @description: 主题交换机
 * @author: hygge
 * @create: 2023/06/13
 */
//@Configuration
public class TopicRabbitConfig {

//  //绑定键
//  public final static String omsOrder = "topic.omsOrder";
//  public final static String all = "topic.all";
//
//  @Bean
//  public Queue omsOrderQueue() {
//    return new Queue(TopicRabbitConfig.omsOrder);
//  }
//
//  @Bean
//  public Queue allQueue() {
//    return new Queue(TopicRabbitConfig.all);
//  }
//
//  @Bean
//  TopicExchange exchange() {
//    return new TopicExchange("TopicExchange");
//  }
//
//
//  //将omsOrderQueue和topicExchange绑定,而且绑定的键值为topic.omsOrder
//  //这样只要是消息携带的路由键是topic.omsOrder,才会分发到该队列
//  @Bean
//  Binding bindingExchangeMessage() {
//    return BindingBuilder.bind(omsOrderQueue()).to(exchange()).with(omsOrder);
//  }
//
//  //将allQueue和topicExchange绑定,而且绑定的键值为用上通配路由键规则topic.#
//  // 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
//  @Bean
//  Binding bindingExchangeMessage2() {
//    return BindingBuilder.bind(allQueue()).to(exchange()).with("topic.#");
//  }
}
