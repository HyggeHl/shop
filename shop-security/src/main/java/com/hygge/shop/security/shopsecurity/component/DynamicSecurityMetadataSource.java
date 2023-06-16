package com.hygge.shop.security.shopsecurity.component;

import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @program: shop
 * @description: 动态权限数据源，用于获取动态权限规则
 * @author: hygge
 * @create: 2023/03/29
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
  private static Map<String, ConfigAttribute> configAttributeMap = null;
  @Autowired
  private DynamicSecurityService dynamicSecurityService;

  @PostConstruct
  public void loadDataSource() {
    configAttributeMap = dynamicSecurityService.loadDataSource();
  }

  public void clearDataSource() {
    configAttributeMap.clear();
    configAttributeMap = null;
  }

  @Override
  public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
    if (configAttributeMap == null) this.loadDataSource();
    List<ConfigAttribute> configAttributes = new ArrayList<>();
    //获取当前访问的路径
    String url = ((FilterInvocation) o).getRequestUrl();
    String path = URLUtil.getPath(url);
    PathMatcher pathMatcher = new AntPathMatcher();
    Iterator<String> iterator = configAttributeMap.keySet().iterator();
    //获取访问该路径所需资源
    while (iterator.hasNext()) {
      String pattern = iterator.next();
      if (pathMatcher.match(pattern, path)) {
        configAttributes.add(configAttributeMap.get(pattern));
      }
    }
    //没有匹配上的资源，禁止访问，设置不存在的访问权限
    // 未设置操作请求权限，如果这里返回空，将会直接放行，运行登录用户访问，这是有风险的
//    if(configAttributes.size() == 0)
//      configAttributes = SecurityConfig.createList("refuseUser");
//    return configAttributes;


    //没有匹配上的资源，直接为空，放行
    return configAttributes;
  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }
}
