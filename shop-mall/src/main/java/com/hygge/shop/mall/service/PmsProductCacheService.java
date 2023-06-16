package com.hygge.shop.mall.service;

/**
 * @program: shop
 * @description: 商品缓存服务
 * @author: hygge
 * @create: 2023/05/24
 */
public interface PmsProductCacheService {

  /**
   * 商品库存加锁
   */
  Boolean setStockLock(String lockKey, String lockValue);

  /**
   * 获取商品库存锁
   */
  String getStockLock(String lockKey);

  /**
   * 设置商品库存
   */
  void setStock(String key, Object value);

  /**
   * 获取商品库存
   */
  Integer getStock(String key);
}
