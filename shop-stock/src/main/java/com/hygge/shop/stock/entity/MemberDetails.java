package com.hygge.shop.stock.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * @program: shop
 * @description: UserDetail实现类
 * @author: hygge
 * @create: 2023/04/23
 */
public class MemberDetails implements UserDetails {
  private final UmsMember umsMember;

  public MemberDetails(UmsMember umsMember) {
    this.umsMember = umsMember;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //返回当前用户的权限
    return Arrays.asList(new SimpleGrantedAuthority("TEST"));
  }

  @Override
  public String getPassword() {
    return umsMember.getPassword();
  }

  /**
   *以手机号作为用户名验证
   */
  @Override
  public String getUsername() {
    return umsMember.getPhone();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return umsMember.getStatus()==1;
  }

  public UmsMember getUmsMember() {
    return umsMember;
  }
}
