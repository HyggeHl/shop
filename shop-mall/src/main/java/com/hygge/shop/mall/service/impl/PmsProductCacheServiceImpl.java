package com.hygge.shop.mall.service.impl;

import com.hygge.shop.common.service.RedisService;
import com.hygge.shop.mall.service.PmsProductCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: shop
 * @description: 商品缓存服务
 * @author: hygge
 * @create: 2023/05/24
 */
@Service
public class PmsProductCacheServiceImpl implements PmsProductCacheService {
  @Autowired
  private RedisService redisService;

  @Value("${redis.database}")
  private String REDIS_DATABASE;
  @Value("${redis.key.stock}")
  private String REDIS_KEY_STOCK;
  @Value("${redis.key.stockLock}")
  private String REDIS_KEY_STOCKLOCK;
  @Value("${redis.expire.stockLock}")
  private Long REDIS_EXPIRE_STOCKLOCK;

  @Override
  public Boolean setStockLock(String lockKey, String lockValue) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_STOCKLOCK + ":" + lockKey;
    return redisService.setIfAbsent(key, lockValue, REDIS_EXPIRE_STOCKLOCK, TimeUnit.SECONDS);
  }

  @Override
  public String getStockLock(String lockKey) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_STOCKLOCK + ":" + lockKey;
    return (String) redisService.get(key);
  }

  @Override
  public void setStock(String goodsId, Object stock) {
    String key =  REDIS_DATABASE + ":" + REDIS_KEY_STOCK + ":" + goodsId;
    redisService.set(key, stock);
  }

  @Override
  public Integer getStock(String goodsId) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_STOCK + ":" + goodsId;
    return (Integer) redisService.get(key);
  }
}
