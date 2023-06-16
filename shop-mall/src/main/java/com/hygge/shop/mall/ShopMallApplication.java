package com.hygge.shop.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.hygge.shop")
@EnableWebMvc
public class ShopMallApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShopMallApplication.class, args);
  }

}
