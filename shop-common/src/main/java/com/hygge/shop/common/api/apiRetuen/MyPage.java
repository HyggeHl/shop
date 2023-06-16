package com.hygge.shop.common.api.apiRetuen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: shop
 * @description: 分页数据
 * @author: hygge
 * @create: 2023/03/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPage {
  //当前页码
  private long current;

  //总页数
  private long pages;

  //每页数量
  private long size;

  //总数量
  private long total;
}
