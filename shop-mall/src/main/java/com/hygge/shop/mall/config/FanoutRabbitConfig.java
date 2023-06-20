package com.hygge.shop.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: shop
 * @description: 扇形交换器配置
 * @author: hygge
 * @create: 2023/06/13
 */
//@Configuration
public class FanoutRabbitConfig {
//
//  /**
//   *  创建三个队列 ：fanout.A   fanout.B  fanout.C
//   *  将三个队列都绑定在交换机 fanoutExchange 上
//   *  因为是扇型交换机, 路由键无需配置,配置也不起作用
//   */
//
//
//  @Bean
//  public Queue queueFanoutA() {
//    return new Queue("fanout.A");
//  }
//
//  @Bean
//  public Queue queueFanoutB() {
//    return new Queue("fanout.B");
//  }
//
//  @Bean
//  public Queue queueFanoutC() {
//    return new Queue("fanout.C", true, false, false);
//  }
//
//  @Bean
//  FanoutExchange fanoutExchange() {
//    return new FanoutExchange("FanoutExchange");
//  }
//
//  @Bean
//  Binding bindingExchangeA() {
//    return BindingBuilder.bind(queueFanoutA()).to(fanoutExchange());
//  }
//
//  @Bean
//  Binding bindingExchangeB() {
//    return BindingBuilder.bind(queueFanoutB()).to(fanoutExchange());
//  }
//
//  @Bean
//  Binding bindingExchangeC() {
//    return BindingBuilder.bind(queueFanoutC()).to(fanoutExchange());
//  }
}
