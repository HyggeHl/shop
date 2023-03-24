package com.hygge.shop.shopcommon.service.impl;

import com.hygge.shop.shopcommon.service.CommonServiceTest;
import org.springframework.stereotype.Service;

/**
 * @program: shop
 * @description: 通用测试
 * @author: hygge
 * @create: 2023/03/04
 */
@Service
public class CommenServiceTestImp implements CommonServiceTest {
  @Override
  public String getHelloWorld(){
    return "Hello World";
  };
}
