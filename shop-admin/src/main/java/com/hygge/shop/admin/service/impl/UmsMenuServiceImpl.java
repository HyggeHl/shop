package com.hygge.shop.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hygge.shop.admin.entity.UmsMenu;
import com.hygge.shop.admin.mapper.UmsMenuMapper;
import com.hygge.shop.admin.service.UmsMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: shop
 * @description: 菜单服务
 * @author: hygge
 * @create: 2023/06/25
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

  @Resource
  private UmsMenuMapper umsMenuMapper;
  @Resource
  private UmsAdminServiceImpl adminService;

  @Override
  public List<UmsMenu> selectMenuList() {
    String phone = adminService.getCurrentAdmin().getPhone();
    return umsMenuMapper.selectMenuListByRoleIds(phone);
  }
}
