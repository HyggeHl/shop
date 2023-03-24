package com.hygge.shop.mall.controller;

import com.hygge.shop.shopcommon.service.CommonServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: shop
 * @description: 测试接口
 * @author: hygge
 * @create: 2023/03/03
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {
  @Autowired
  private CommonServiceTest commonServiceTest;

  /**
   *@Description:
   *@Param: []
   *@return: java.lang.String
   *@Author: hygge
   *@date: 2023/3/3
   */
  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @ResponseBody
  public String helloWorld() {
    return commonServiceTest.getHelloWorld();
  }
}
