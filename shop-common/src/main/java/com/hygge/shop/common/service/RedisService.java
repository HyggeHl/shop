package com.hygge.shop.common.service;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.concurrent.TimeUnit;

/**
 * @program: shop
 * @description: Redis操作Service
 * @author: hygge
 * @create: 2023/05/06
 */
public interface RedisService {

  /**
   * 保存属性
   */
  void set(String key, Object value);

  /**
   * 保存属性
   */
  void set(String key, Object value, long time);

  /**
   * 不存在则保存属性
   */
  Boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit);

  /**
   * 获取属性
   */
  Object get(String key);

  /**
   * 删除属性
   */
  Boolean del(String key);

  /**
   * 设置过期时间
   */
  Boolean expire(String key, long time);

  /**
   * 获取过期时间
   */
  long getExpire(String key);

  /**
   * 判断是否有该属性
   */
  Boolean hasKey(String key);

  /**
   * 按delta递增
   */
  long incr(String key, long delta);

  /**
   * 按delta递减
   */
  long decr(String key, long delta);
}
