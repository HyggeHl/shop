package com.hygge.shop.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @program: shop
 * @description: shop-stock启动类
 * @author: hygge
 * @create: 2023/06/12
 */
@SpringBootApplication(scanBasePackages = "com.hygge.shop")
@EnableWebMvc
public class ShopStockApplication {
  public static void main(String[] args) {
    SpringApplication.run(ShopStockApplication.class, args);
  }
}
