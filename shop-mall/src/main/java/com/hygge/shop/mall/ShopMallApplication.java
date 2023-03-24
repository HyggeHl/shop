package com.hygge.shop.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hygge.shop")
public class ShopMallApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopMallApplication.class, args);
  }

}
