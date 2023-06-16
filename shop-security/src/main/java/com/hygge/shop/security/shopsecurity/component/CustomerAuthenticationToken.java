package com.hygge.shop.security.shopsecurity.component;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @program: shop
 * @description: 封装身份信息验证
 * @author: hygge
 * @create: 2023/04/25
 */
//@Component
public class CustomerAuthenticationToken extends AbstractAuthenticationToken {

  private final UserDetails principal;

  private Object credentials;

  private final String phone;

  public CustomerAuthenticationToken(String phone, UserDetails principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    this.phone = phone;

    // 必须设置
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public String getName() {
    return super.getName();
  }

  public String getPhone() {
    return phone;
  }
}
