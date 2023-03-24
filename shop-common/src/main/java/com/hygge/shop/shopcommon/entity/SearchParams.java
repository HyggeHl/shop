package com.hygge.shop.shopcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: shop
 * @description: 查询参数
 * @author: hygge
 * @create: 2023/03/23
 */
@EqualsAndHashCode()
@Data()
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams {
  private String year;// 年
  private String month;// 月
  private String day;// 日
  private String userId;// 用户
  private String userName;// 用户编码
  private String orgId;// 机构
  private String orgName;// 机构名称
  private String orgLevel;// 机构级别
  private String level;// 机构级别
  private Date time;// 时间
  private Date starttime;// 开始时间
  private Date endtime;// 结束时间
  private String from;// 开始
  private String to;// 结束
  private String status;// 状态
  private String type;// 类型
  private String keyword;// 关键字
  private String filter;// 过滤字段
  private String flag;// 标记
  private String scope;// 查询范围
  private String group;// 分组方式
  private String order;// 排序方式
  private int currentPage;// 当前页数
  private int pageSize;// 每页条数
  private int start;
  private int end;
  private String begindate;// 开始时间
  private String enddate;// 结束时间
  private int limit;
  private int total;
  private Boolean isHasexport = false;
}
