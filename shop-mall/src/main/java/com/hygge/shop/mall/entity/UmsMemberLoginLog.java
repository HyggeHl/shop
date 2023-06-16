package com.hygge.shop.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 会员登录记录
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
@TableName("ums_member_login_log")
@ApiModel(value = "UmsMemberLoginLog对象", description = "会员登录记录")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsMemberLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("member_id")
    private Long memberId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("ip")
    private String ip;

    @TableField("city")
    private String city;

    @ApiModelProperty("登录类型：0->PC；1->android;2->ios;3->小程序")
    @TableField("login_type")
    private Integer loginType;

    @TableField("province")
    private String province;


}
