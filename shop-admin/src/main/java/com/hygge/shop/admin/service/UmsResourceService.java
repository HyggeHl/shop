package com.hygge.shop.admin.service;

import com.hygge.shop.admin.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

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
