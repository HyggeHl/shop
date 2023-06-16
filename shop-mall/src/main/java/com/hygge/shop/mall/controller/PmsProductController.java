package com.hygge.shop.mall.controller;

import com.hygge.shop.common.api.annotion.ApiLog;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.mall.entity.PmsProduct;
import com.hygge.shop.mall.service.PmsProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author hygge
 * @since 2023-05-24
 */
@Controller
@RequestMapping("/pmsProduct")
public class PmsProductController {
  @Autowired
  private PmsProductService pmsProductService;

  @ApiOperation("扣减库存redis")
  @RequestMapping(value = "/deductStock", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult deductStock() {
    return pmsProductService.deductStock();
  }

  @ApiOperation("扣减库存redisson")
  @RequestMapping(value = "/deductStockRedisson", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult deductStockRedisson() {
    return pmsProductService.deductStockRedisson();
  }
  
  @ApiLog
  @ApiOperation("查询商品列表")
  @RequestMapping(value = "/queryPmsProduct", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult queryPmsProduct(PmsProduct params) {
    return pmsProductService.queryPmsProduct(params);
  }

  @ApiOperation("根据id查询商品")
  @RequestMapping(value = "/getPmsProduct", method = RequestMethod.GET)
  @ResponseBody
  public CommonResult getPmsProduct(PmsProduct params) {
    return pmsProductService.getPmsProduct(params);
  }

  @RequestMapping(value = "/insertPmsProduct", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("插入单条商品")
  public CommonResult insertPmsProduct(PmsProduct params) {
    pmsProductService.insertPmsProduct(params);
    return CommonResult.failed();
  }

  @RequestMapping(value = "/updatePmsProduct", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("更新商品")
  public CommonResult updatePmsProduct(PmsProduct params) {
    return pmsProductService.updatePmsProduct(params);
  }

  @RequestMapping(value = "/deletePmsProduct", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("删除单条商品")
  public CommonResult deletePmsProduct(PmsProduct params) {
    return pmsProductService.deletePmsProduct(params);
  }

  @RequestMapping(value = "/deletePmsProductBatch", method = RequestMethod.POST)
  @ResponseBody
  @ApiOperation("删除多条商品")
  public CommonResult deletePmsProductBatch(PmsProduct params) {
    return pmsProductService.deletePmsProductBatch(new ArrayList<>());
  }

  @RequestMapping(value = "/getPmsProductList", method = RequestMethod.GET)
  @ResponseBody
  @ApiOperation("条件查询商品")
  public CommonResult getPmsProductList(PmsProduct params) {
    return pmsProductService.getPmsProductList(params);
  }
}
