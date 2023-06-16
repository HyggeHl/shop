package com.hygge.shop.mall.service.impl;

import com.hygge.shop.common.service.RedisService;
import com.hygge.shop.mall.service.UmsMemberCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @program: shop
 * @description: 会员缓存服务实现
 * @author: hygge
 * @create: 2023/05/06
 */
@Service
public class UmsMemberCachServiceImpl implements UmsMemberCacheService {

  @Autowired
  private RedisService redisService;

  @Value("${redis.database}")
  private String REDIS_DATABASE;
  @Value("${redis.key.authCode}")
  private String REDIS_KEY_AUTHCODE;
  @Value("${redis.expire.authCode}")
  private Long REDIS_EXPIRE_AUTHCODE;

  @Override
  public void setAuthCode(String tel, String authCode) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTHCODE + ":" + tel;
    redisService.set(key, authCode, REDIS_EXPIRE_AUTHCODE);
  }

  @Override
  public String getAuthCode(String tel) {
    String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTHCODE + ":" + tel;
    return (String) redisService.get(key);
  }
}
