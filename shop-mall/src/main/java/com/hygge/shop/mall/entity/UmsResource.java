package com.hygge.shop.mall.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台资源表
 * </p>
 *
 * @author hygge
 * @since 2023-04-19
 */
@Getter
@Setter
@TableName("ums_resource")
@ApiModel(value = "UmsResource对象", description = "后台资源表")
public class UmsResource implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("创建时间")
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("资源名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("资源URL")
    @TableField("url")
    private String url;

    @ApiModelProperty("描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("资源分类ID")
    @TableField("category_id")
    private Long categoryId;


}
