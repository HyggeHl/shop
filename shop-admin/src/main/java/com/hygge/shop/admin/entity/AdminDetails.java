package com.hygge.shop.admin.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * @program: shop
 * @description: UserDetails实现类
 * @author: hygge
 * @create: 2023/06/25
 */
public class AdminDetails implements UserDetails {

  private final UmsAdmin umsAdmin;

  public AdminDetails(UmsAdmin umsAdmin) {
    this.umsAdmin = umsAdmin;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //返回当前用户的权限
    return Arrays.asList(new SimpleGrantedAuthority("TEST"));
  }

  @Override
  public String getPassword() {
    return umsAdmin.getPassword();
  }

  /**
   *以手机号作为用户名验证
   */
  @Override
  public String getUsername() {
    return umsAdmin.getPhone();
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
    return umsAdmin.getStatus()==1;
  }

  public UmsAdmin getUmsMember() {
    return umsAdmin;
  }
}
