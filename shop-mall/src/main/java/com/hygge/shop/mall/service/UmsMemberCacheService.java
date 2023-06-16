package com.hygge.shop.mall.service;

/**
 * @program: shop
 * @description: 会员缓存服务
 * @author: hygge
 * @create: 2023/05/06
 */
public interface UmsMemberCacheService {

  /**
   * 缓存验证码
   */
  void setAuthCode(String tel, String authCode);

  /**
   * 获取验证码
   */
  String getAuthCode(String tel);
}
