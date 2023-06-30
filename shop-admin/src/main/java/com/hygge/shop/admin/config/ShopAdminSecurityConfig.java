package com.hygge.shop.admin.config;

import com.hygge.shop.admin.entity.UmsResource;
import com.hygge.shop.admin.service.UmsAdminService;
import com.hygge.shop.admin.service.UmsResourceService;
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
 * @description: shop-admin安全配置
 * @author: hygge
 * @create: 2023/04/19
 */
@Configuration
public class ShopAdminSecurityConfig {

  @Autowired
  private UmsAdminService adminService;
  @Autowired
  private UmsResourceService resourceService;

  @Bean
  public UserDetailsService userDetailsService() {
    //获取登录用户信息
    return phone -> adminService.loadUserByPhone(phone);
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
