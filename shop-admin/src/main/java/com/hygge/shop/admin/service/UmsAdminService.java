package com.hygge.shop.admin.service;

import com.hygge.shop.admin.entity.AdminDetails;
import com.hygge.shop.admin.entity.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hygge.shop.admin.entity.UmsResource;
import com.hygge.shop.admin.entity.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */
public interface UmsAdminService extends IService<UmsAdmin> {

  /**
   * 根据用户名获取会员
   */
  UmsAdmin getByUsername(String username);

  /**
   * 根据手机号获取会员
   */
  UmsAdmin getByPhone(String phone);

  /**
   * 用户注册
   */
  void register(UmsAdmin umsMember);

  /**
   * 获取用户信息
   */
  UserDetails loadUserByUsername(String username);

  /**
   * 获取用户信息
   */
  AdminDetails loadUserByPhone(String phone);

  /**
   * 获取当前登录会员
   */
  UmsAdmin getCurrentAdmin();

  /**
   * 登录后获取token
   */
  String login(UmsAdmin umsMember);
}
