package com.hygge.shop.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.hygge.shop.admin.entity.UmsAdmin;
import com.hygge.shop.admin.mapper.UmsAdminRoleRelationMapper;
import com.hygge.shop.admin.service.UmsAdminCacheService;
import com.hygge.shop.admin.service.UmsAdminService;
import com.hygge.shop.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: shop
 * @description: UmsAdminCacheService实现类
 * @author: hygge
 * @create: 2023/04/19
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
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
