package com.hygge.shop.security.shopsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: shop
 * @description: SpringSecurity白名单资源路径配置
 * @author: hygge
 * @create: 2023/03/29
 */
@Data
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {
  private List<String> urls = new ArrayList<>();
}
