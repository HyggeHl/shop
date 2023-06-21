package com.hygge.shop.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hygge.shop.mall.entity.UmsResource;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author hygge
 * @since 2023-04-19
 */
public interface UmsResourceService extends IService<UmsResource> {
  /**
   * 查询全部资源
   */
  List<UmsResource> listAll();
}
