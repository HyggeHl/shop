package com.hygge.shop.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hygge.shop.mall.entity.UmsResource;
import com.hygge.shop.mall.mapper.UmsResourceMapper;
import com.hygge.shop.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-04-19
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {
  @Autowired
  private UmsResourceMapper umsResourceMapper;

  @Override
  public List<UmsResource> listAll() {
    return umsResourceMapper.listAll();
  }
}
