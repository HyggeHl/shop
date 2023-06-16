package com.hygge.shop.security.shopsecurity.config;

import com.hygge.shop.security.shopsecurity.component.*;
import com.hygge.shop.security.shopsecurity.utils.JwtTokenUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

/**
 * @program: shop
 * @description: SpringSecurity通用配置
 * 包括通用Bean、Security通用Bean及动态权限通用Bean
 * @author: hygge
 * @create: 2023/03/29
 */
@Configuration
public class CommonSecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public IgnoreUrlsConfig ignoreUrlsConfig() {
    return new IgnoreUrlsConfig();
  }

  @Bean
  public JwtTokenUtil jwtTokenUtil() {
    return new JwtTokenUtil();
  }

  @Bean
  public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
    return new RestfulAccessDeniedHandler();
  }

  @Bean
  public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
    return new RestAuthenticationEntryPoint();
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
    return new JwtAuthenticationTokenFilter();
  }

//  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
    return new DynamicAccessDecisionManager();
  }

//  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
    return new DynamicSecurityMetadataSource();
  }

//  @ConditionalOnBean(name = "dynamicSecurityService")
  @Bean
  public DynamicSecurityFilter dynamicSecurityFilter(){
    return new DynamicSecurityFilter();
  }

}
