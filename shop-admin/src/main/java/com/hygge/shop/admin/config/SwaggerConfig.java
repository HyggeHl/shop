package com.hygge.shop.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @program: shop
 * @description: swagger接口文档配置
 * @author: hygge
 * @create: 2023/04/19
 */
//@Configuration
//@EnableOpenApi
public class SwaggerConfig {
//  @Bean
//  public Docket docketShop() {
//    Docket docket = new Docket(DocumentationType.OAS_30)
//      .groupName("shop")
//      .apiInfo(apiInfo()).enable(true)
//      .select()
//      //apis： 添加swagger接口提取范围
//      .apis(RequestHandlerSelectors.basePackage("com.hygge.shop.admin.controller"))
//      .paths(PathSelectors.any())
//      .build();
//
//    return docket;
//  }
//
//  @Bean
//  public Docket docketAdmin() {
//    Docket docket = new Docket(DocumentationType.OAS_30)
//      .groupName("admin")
//      .apiInfo(apiInfo()).enable(true)
//      .select()
//      //apis： 添加swagger接口提取范围
//      .apis(RequestHandlerSelectors.basePackage("com.hygge.shop.admin.controller"))
//      .paths(PathSelectors.any())
//      .build();
//
//    return docket;
//  }
//
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//      .title("shop API")
//      .description("codingmore")
//      .contact(new Contact("hygge", "https://tobebetterjavaer.com", "983436076@qq.com"))
//      .version("1.0")
//      .build();
//  }
}
