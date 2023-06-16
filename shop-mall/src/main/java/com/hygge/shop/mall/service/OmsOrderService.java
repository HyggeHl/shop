package com.hygge.shop.mall.service;

import com.hygge.shop.mall.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hygge.shop.common.api.apiRetuen.CommonResult;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author hygge
 * @since 2023-03-27
 */
public interface OmsOrderService extends IService<OmsOrder> {

  CommonResult queryOmsOrder(OmsOrder params);

  CommonResult getOmsOrder(OmsOrder params);

  void insertOmsOrder(OmsOrder params) throws RuntimeException;

  CommonResult updateOmsOrder(OmsOrder params);

  CommonResult deleteOmsOrder(OmsOrder params);

  CommonResult deleteOmsOrderBatch(List<OmsOrder> list);

  CommonResult getOmsOrderList(OmsOrder params);

}