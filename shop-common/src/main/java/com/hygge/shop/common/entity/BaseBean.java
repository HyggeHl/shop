package com.hygge.shop.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * @program: shop
 * @description: 基础实体类
 * @author: hygge
 * @create: 2023/03/23
 */
public class BaseBean implements Serializable {
  @TableField(exist = false)
  protected SearchParams search;

  @TableField(exist = false)
  private String router;

  @TableField(exist = false)
  private int action;

  public SearchParams getSearch() {
    if (null == search) {
      search = new SearchParams();
    }
    return search;
  }

  public void setSearch(SearchParams deal) {
    this.search = deal;
  };
}
