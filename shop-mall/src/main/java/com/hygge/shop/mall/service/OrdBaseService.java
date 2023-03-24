package com.hygge.shop.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hygge.shop.mall.entity.OrdBase;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hygge.shop.shopcommon.api.CommonResult;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * <p>
 * 商城，订单表 服务类
 * </p>
 *
 * @author hygge
 * @since 2023-03-23
 */
public interface OrdBaseService extends IService<OrdBase> {

  CommonResult queryOrdBase(OrdBase params);

  CommonResult getOrdBase(OrdBase params);

  void insertOrdBase(OrdBase params) throws RuntimeException;

  CommonResult updateOrdBase(OrdBase params);

  CommonResult deleteOrdBase(OrdBase params);

  CommonResult deleteOrdBaseBatch(List<OrdBase> list);

  CommonResult getOrdBaseList(OrdBase params);

}
