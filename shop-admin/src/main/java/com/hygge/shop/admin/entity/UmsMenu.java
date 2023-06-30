package com.hygge.shop.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hygge.shop.common.entity.BaseBean;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @program: shop
 * @description: 菜单
 * @author: hygge
 * @create: 2023/06/25
 */
@Data
public class UmsMenu extends BaseBean {

  @TableId
  @Schema(description = "菜单编号")
  private Integer mid;

  @Schema(description = "菜单Key")
  @JsonProperty("menuKey")
  private String menuKey;

  @Schema(description = "菜单名称")
  @JsonProperty("mName")
  private String mName;

  @Schema(description = "父菜单编号")
  @JsonProperty("mPId")
  private Integer mPId;

  @Schema(description = "父菜单名称")
  @JsonProperty("mPName")
  private String mPName;

  @Schema(description = "菜单层级")
  @JsonProperty("mDepth")
  private Integer mDepth;

  @JsonProperty("mDisp")
  private Integer mDisp;

  @JsonProperty("mAuth")
  private Integer mAuth;

  @Schema(description = "菜单提示")
  @JsonProperty("mTip")
  private String mTip;

  @Schema(description = "菜单方法")
  @JsonProperty("mFunction")
  private String mFunction;

  private Boolean isLine;

  @Schema(description = "是否为submenu")
  private Boolean isSub;

  @Schema(description = "常态图标")
  @JsonProperty("mNormalIcon")
  private String mNormalIcon;

  @Schema(description = "菜单编码")
  @JsonProperty("mCode")
  private String mCode;

  @Schema(description = "菜单状态")
  @JsonProperty("mStatus")
  private String mStatus;

  private Boolean isTab;

  @Schema(description = "路由参数")
  @JsonProperty("params")
  private String params;

  @TableField(exist = false)
  private List<UmsMenu> children;
}
