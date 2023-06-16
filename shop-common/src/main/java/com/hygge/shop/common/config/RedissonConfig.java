package com.hygge.shop.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: shop
 * @description: redisson配置
 * @author: hygge
 * @create: 2023/05/31
 */
@Configuration
public class RedissonConfig {

  @Bean
  public RedissonClient redisson() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://116.62.140.246:6379");
    return Redisson.create(config);
  }
}
