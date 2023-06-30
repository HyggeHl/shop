package com.hygge.shop.admin.service;

import com.hygge.shop.admin.entity.UmsAdmin;
import com.hygge.shop.admin.entity.UmsResource;

import java.util.List;

/**
*@description: 后台用户缓存操作Service
*@author: hygge
*@create: 2023/4/19
*/
public interface UmsAdminCacheService {
  /**
   * 缓存验证码
   */
  void setAuthCode(String tel, String authCode);

  /**
   * 获取验证码
   */
  String getAuthCode(String tel);
}
