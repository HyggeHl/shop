package com.hygge.shop.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hygge.shop.admin.entity.UmsMenu;

import java.util.List;

public interface UmsMenuService extends IService<UmsMenu> {
  List<UmsMenu> selectMenuList();
}
