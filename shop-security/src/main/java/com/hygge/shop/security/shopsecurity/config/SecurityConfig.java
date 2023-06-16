package com.hygge.shop.security.shopsecurity.config;

import com.hygge.shop.security.shopsecurity.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @program: shop
 * @description: SpringSecurity配置类
 * @author: hygge
 * @create: 2023/03/29
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired
  private IgnoreUrlsConfig ignoreUrlsConfig;
  @Autowired
  private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
  @Autowired
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  @Autowired
  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
  @Autowired(required = false)
  private DynamicSecurityService dynamicSecurityService;
  @Autowired(required = false)
  private DynamicSecurityFilter dynamicSecurityFilter;
  @Autowired
  private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;


  @Bean(name = "authenticationManager")
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
    //不需要保护的资源路径允许访问
    for (String url : ignoreUrlsConfig.getUrls()) {
      registry.antMatchers(url).permitAll();
    }
    //允许跨域请求的OPTIONS请求
    registry.antMatchers(HttpMethod.OPTIONS)
      .permitAll();
    // 任何请求需要身份认证
    registry.and()
      .authorizeRequests()
      .anyRequest()
      .authenticated()
      // 关闭跨站请求防护及不使用session
      .and()
      .csrf()
      .disable()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      // 自定义权限拒绝处理类
      .and()
      .exceptionHandling()
      .accessDeniedHandler(restfulAccessDeniedHandler)
      .authenticationEntryPoint(restAuthenticationEntryPoint)
      // 自定义权限拦截器JWT过滤器
      .and()
      .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    //有动态权限配置时添加动态权限校验过滤器
    if(dynamicSecurityService!=null){
      registry.and().addFilterBefore(dynamicSecurityFilter, FilterSecurityInterceptor.class);
    }
    return httpSecurity.build();
  }
}