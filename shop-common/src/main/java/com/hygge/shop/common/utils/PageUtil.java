package com.hygge.shop.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hygge.shop.common.api.apiRetuen.MyPage;

/**
 * @program: shop
 * @description: page分页数据封装
 * @author: hygge
 * @create: 2023/03/24
 */
public class PageUtil {
  public MyPage dealPage(IPage<?> page) {
   return MyPage.builder().current(page.getCurrent()).pages(page.getPages()).size(page.getSize()).total(page.getTotal()).build();
  }
}
