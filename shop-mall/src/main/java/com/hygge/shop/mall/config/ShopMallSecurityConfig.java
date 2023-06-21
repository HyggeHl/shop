package com.hygge.shop.mall.config;

import com.hygge.shop.mall.entity.UmsResource;
import com.hygge.shop.mall.service.UmsResourceService;
import com.hygge.shop.mall.service.UmsMemberService;
import com.hygge.shop.security.shopsecurity.component.DynamicSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: shop
 * @description: shop-mall模块安全配置
 * @author: hygge
 * @create: 2023/04/19
 */
@Configuration
public class ShopMallSecurityConfig {

  @Autowired
  private UmsMemberService umsMemberService;
  @Autowired
  private UmsResourceService resourceService;

  @Bean
  public UserDetailsService userDetailsService() {
    //获取登录用户信息
//    return username -> umsMemberService.loadUserByUsername(username);
    return phone -> umsMemberService.loadUserByPhone(phone);
  }

  @Bean
  public DynamicSecurityService dynamicSecurityService() {
    return new DynamicSecurityService() {
      @Override
      public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        List<UmsResource> resourceList = resourceService.listAll();
        for (UmsResource resource : resourceList) {
          map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
        return map;
      }
    };
  }

}
