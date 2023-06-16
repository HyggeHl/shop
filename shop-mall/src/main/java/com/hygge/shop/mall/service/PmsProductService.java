package com.hygge.shop.mall.service;

import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.mall.entity.PmsProduct;
import com.hygge.shop.mall.entity.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author hygge
 * @since 2023-05-24
 */
public interface PmsProductService extends IService<PmsProduct> {

  CommonResult deductStock();

  CommonResult deductStockRedisson();

  CommonResult queryPmsProduct(PmsProduct params);

  CommonResult getPmsProduct(PmsProduct params);

  void insertPmsProduct(PmsProduct params) throws RuntimeException;

  CommonResult updatePmsProduct(PmsProduct params);

  CommonResult deletePmsProduct(PmsProduct params);

  CommonResult deletePmsProductBatch(List<PmsProduct> list);

  CommonResult getPmsProductList(PmsProduct params);
}
