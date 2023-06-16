package com.hygge.shop.mall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @program: shop
 * @description: 直连交换器配置
 * @author: hygge
 * @create: 2023/06/12
 */
@Configuration
public class DirectRabbitConfig {

  //队列 起名：DirectQueue
  @Bean
  public Queue DirectQueue_0() {
    // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
    // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
    // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
    //   return new Queue("TestDirectQueue",true,true,false);
    //一般设置一下队列的持久化就好,其余两个就是默认false
    return creatQueue("DirectQueue_0");
  }

  @Bean
  public Queue DirectQueue_1() {
    return creatQueue("DirectQueue_1");
  }

  @Bean
  public Queue DirectQueue_2() {
    return creatQueue("DirectQueue_2");
  }

  @Bean
  public Queue DirectQueue_3() {
    return creatQueue("DirectQueue_3");
  }

  //Direct交换机 起名：DirectExchange
  @Bean
  DirectExchange DirectExchange() {
    return new DirectExchange("DirectExchange",true,false);
  }

  //绑定  将队列和交换机绑定, 并设置用于匹配键：bindingDirect
  @Bean
  Binding bindingDirect_0() {
    return BindingBuilder.bind(DirectQueue_0()).to(DirectExchange()).with("DirectRouting_0");
  }

  @Bean
  Binding bindingDirect_1() {
    return BindingBuilder.bind(DirectQueue_1()).to(DirectExchange()).with("DirectRouting_1");
  }

  @Bean
  Binding bindingDirect_2() {
    return BindingBuilder.bind(DirectQueue_2()).to(DirectExchange()).with("DirectRouting_2");
  }

  @Bean
  Binding bindingDirect_3() {
    return BindingBuilder.bind(DirectQueue_3()).to(DirectExchange()).with("DirectRouting_3");
  }

  @Bean
  DirectExchange lonelyDirectExchange() {
    return new DirectExchange("lonelyDirectExchange");
  }

  /**
   * 创建一个 单活 模式的队列
   * 注意 ：
   * <p>
   * 如果一个队列已经创建为非x-single-active-consumer，而你想更改其为x-single-active-consumer，要把之前创建的队列删除
   *
   * @param name
   * @return queue
   */
  private Queue creatQueue(String name) {
    // 创建一个 单活模式 队列
    HashMap<String, Object> args = new HashMap<>();
    args.put("x-single-active-consumer", true);
    return new Queue(name, true, false, false, args);
  }
}
