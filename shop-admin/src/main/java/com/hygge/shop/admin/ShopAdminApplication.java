package com.hygge.shop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.hygge.shop")
@EnableWebMvc
public class ShopAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopAdminApplication.class, args);
  }

}
