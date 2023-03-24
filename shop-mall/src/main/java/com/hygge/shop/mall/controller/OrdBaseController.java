package com.hygge.shop.mall.controller;

import com.hygge.shop.mall.entity.OrdBase;
import com.hygge.shop.mall.service.OrdBaseService;
import com.hygge.shop.shopcommon.api.CommonResult;
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
 * 商城，订单表 前端控制器
 * </p>
 *
 * @author hygge
 * @since 2023-03-22
 */
@Api(tags = "OrdBaseController")
@Tag(name = "OrdBaseController", description = "订单管理")
@Controller
@RequestMapping("/ordBase")
public class OrdBaseController {
  @Autowired
  private OrdBaseService ordBaseService;

  @ApiOperation("查询订单列表")
  @RequestMapping(value = "/queryOrdBase", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult queryOrdBase(OrdBase params) {
    return ordBaseService.queryOrdBase(params);
  }

  @ApiOperation("根据id查询订单")
  @RequestMapping(value = "/getOrdBase", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult getOrdBase(OrdBase params) {
    return ordBaseService.getOrdBase(params);
  }

  @RequestMapping(value = "/insertOrdBase", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("插入单条订单")
  public CommonResult insertOrdBase(OrdBase params) {
    ordBaseService.insertOrdBase(params);
    return CommonResult.failed();
  }

  @RequestMapping(value = "/updateOrdBase", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("更新订单")
  public CommonResult updateOrdBase(OrdBase params) {
    return ordBaseService.updateOrdBase(params);
  }

  @RequestMapping(value = "/deleteOrdBase", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("删除单条订单")
  public CommonResult deleteOrdBase(OrdBase params) {
    return ordBaseService.deleteOrdBase(params);
  }

  @RequestMapping(value = "/deleteOrdBaseBatch", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("删除多条订单")
  public CommonResult deleteOrdBaseBatch(OrdBase params) {
    return ordBaseService.deleteOrdBaseBatch(new ArrayList<>());
  }

  @RequestMapping(value = "/getOrdBaseList", method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation("条件查询订单")
  public CommonResult getOrdBaseList(OrdBase params) {
    return ordBaseService.getOrdBaseList(params);
  }
}
