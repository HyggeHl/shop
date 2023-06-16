package com.hygge.shop.mall.controller;

import com.hygge.shop.mall.entity.OmsOrder;
import com.hygge.shop.mall.service.OmsOrderService;
import com.hygge.shop.common.api.annotion.ApiLog;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author hygge
 * @since 2023-03-27
 */
@Api(tags = "OmsOrderController")
@Tag(name = "OmsOrderController", description = "订单管理")
@Controller
@RequestMapping("/omsOrder")
public class OmsOrderController {
  @Autowired
  private OmsOrderService OmsOrderService;

  @ApiLog
  @ApiOperation("查询订单列表")
  @RequestMapping(value = "/queryOmsOrder", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult queryOmsOrder(OmsOrder params) {
    return OmsOrderService.queryOmsOrder(params);
  }

  @ApiOperation("根据id查询订单")
  @RequestMapping(value = "/getOmsOrder", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult getOmsOrder(OmsOrder params) {
    return OmsOrderService.getOmsOrder(params);
  }

  @RequestMapping(value = "/insertOmsOrder", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("插入单条订单")
  public CommonResult insertOmsOrder(OmsOrder params) {
    OmsOrderService.insertOmsOrder(params);
    return CommonResult.failed();
  }

  @RequestMapping(value = "/updateOmsOrder", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("更新订单")
  public CommonResult updateOmsOrder(OmsOrder params) {
    return OmsOrderService.updateOmsOrder(params);
  }

  @RequestMapping(value = "/deleteOmsOrder", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("删除单条订单")
  public CommonResult deleteOmsOrder(OmsOrder params) {
    return OmsOrderService.deleteOmsOrder(params);
  }

  @RequestMapping(value = "/deleteOmsOrderBatch", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("删除多条订单")
  public CommonResult deleteOmsOrderBatch(OmsOrder params) {
    return OmsOrderService.deleteOmsOrderBatch(new ArrayList<>());
  }

  @RequestMapping(value = "/getOmsOrderList", method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation("条件查询订单")
  public CommonResult getOmsOrderList(OmsOrder params) {
    return OmsOrderService.getOmsOrderList(params);
  }
}
