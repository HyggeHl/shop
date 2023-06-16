package com.hygge.shop.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 后台用户权限表
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("ums_permission")
@ApiModel(value = "UmsPermission对象", description = "后台用户权限表")
public class UmsPermission implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("父级权限id")
    @TableField("pid")
    private Long pid;

    @ApiModelProperty("名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限值")
    @TableField("value")
    private String value;

    @ApiModelProperty("图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("前端资源路径")
    @TableField("uri")
    private String uri;

    @ApiModelProperty("启用状态；0->禁用；1->启用")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
      @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;


}
