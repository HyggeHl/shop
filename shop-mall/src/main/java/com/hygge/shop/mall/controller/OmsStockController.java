package com.hygge.shop.mall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.mall.sender.OmsStockDirectSender;
import com.hygge.shop.mall.sender.OmsStockFanoutSender;
import com.hygge.shop.mall.sender.OmsStockTopicSender;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: shop
 * @description: 库存消息
 * @author: hygge
 * @create: 2023/06/12
 */
@Api(tags = "OmsStockController")
@Tag(name = "OmsStockController", description = "库存消息")
@Controller
@RequestMapping("/omsStock")
public class OmsStockController {

  @Autowired
  private OmsStockDirectSender omsStockDirectSender;
  @Autowired
  private OmsStockTopicSender omsStockTopicSender;
  @Autowired
  private OmsStockFanoutSender omsStockFanoutSender;

  @RequestMapping("/sendDeductStockMessage")
  @ResponseBody
  public CommonResult sendDeductStockMessage() throws JsonProcessingException {
    return omsStockDirectSender.sendDeductStockMessage();
  }

  @RequestMapping("/sendDeductStockMessageOrder")
  @ResponseBody
  public CommonResult sendDeductStockMessageOrder() throws JsonProcessingException {
    // 模拟每个队列中扔 10 个数据，看看效果
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 4; j++) {
        omsStockDirectSender.syncSend(j, " 编号：" + j + " 第：" + i + " 条消息");
      }
    }
    return CommonResult.success(null);
  }

  @RequestMapping("/sendDeductStockMessageJson")
  @ResponseBody
  public CommonResult sendDeductStockMessageJson() throws JsonProcessingException {
    return omsStockDirectSender.sendDeductStockMessageJson();
  }

  @RequestMapping("/sendDeductStockTopicMessage")
  @ResponseBody
  public CommonResult sendDeductStockTopicMessage() throws JsonProcessingException {
    return omsStockTopicSender.sendDeductStockTopicMessage();
  }

  @RequestMapping("/sendDeductStockTopicRegMessage")
  @ResponseBody
  public CommonResult sendDeductStockTopicRegMessage() throws JsonProcessingException {
    return omsStockTopicSender.sendDeductStockTopicMessageReg();
  }

  @RequestMapping("/sendDeductStockFanoutRegMessage")
  @ResponseBody
  public CommonResult sendDeductStockFanoutRegMessage() throws JsonProcessingException {
    return omsStockFanoutSender.sendDeductStockFanoutMessage();
  }

}
