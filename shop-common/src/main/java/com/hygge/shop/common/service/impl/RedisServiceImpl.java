package com.hygge.shop.common.service.impl;

import com.hygge.shop.common.service.RedisService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @program: shop
 * @description: Redis服务实现
 * @author: hygge
 * @create: 2023/05/06
 */
@Service
public class RedisServiceImpl implements RedisService {

  @Autowired
  private RedisTemplate redisTemplate;
  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @Override
  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void set(String key, Object value, long time) {
    redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
  }

  @Override
  public Boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
    return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
  }

  @Override
  public Object get(String key) {
    return redisTemplate.opsForValue().get(key);
  }

  @Override
  public Boolean del(String key) {
    return redisTemplate.delete(key);
  }

  @Override
  public Boolean expire(String key, long time) {
    return redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
  }

  @Override
  public long getExpire(String key) {
    return redisTemplate.getExpire(key);
  }

  @Override
  public Boolean hasKey(String key) {
    return redisTemplate.hasKey(key);
  }

  @Override
  public long incr(String key, long delta) {
    return redisTemplate.opsForValue().increment(key, delta);
  }

  @Override
  public long decr(String key, long delta) {
    return redisTemplate.opsForValue().decrement(key, delta);
  }
}
