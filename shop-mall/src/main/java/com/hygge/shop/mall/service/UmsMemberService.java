package com.hygge.shop.mall.service;

import com.hygge.shop.mall.entity.MemberDetails;
import com.hygge.shop.mall.entity.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
public interface UmsMemberService extends IService<UmsMember> {
  /**
   * 根据用户名获取会员
   */
  UmsMember getByUsername(String username);

  /**
   * 根据手机号获取会员
   */
  UmsMember getByPhone(String phone);

  /**
   * 用户注册
   */
  void register(UmsMember umsMember);

  /**
   * 获取用户信息
   */
  UserDetails loadUserByUsername(String username);

  /**
   * 获取用户信息
   */
  MemberDetails loadUserByPhone(String phone);

  /**
   * 获取当前登录会员
   */
  UmsMember getCurrentMember();

  /**
   * 登录后获取token
   */
  String login(UmsMember umsMember);

}
