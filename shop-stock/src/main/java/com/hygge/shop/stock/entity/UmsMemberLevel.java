package com.hygge.shop.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会员等级表
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
@TableName("ums_member_level")
@ApiModel(value = "UmsMemberLevel对象", description = "会员等级表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsMemberLevel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("growth_point")
    private Integer growthPoint;

    @ApiModelProperty("是否为默认等级：0->不是；1->是")
    @TableField("default_status")
    private Integer defaultStatus;

    @ApiModelProperty("免运费标准")
    @TableField("free_freight_point")
    private BigDecimal freeFreightPoint;

    @ApiModelProperty("每次评价获取的成长值")
    @TableField("comment_growth_point")
    private Integer commentGrowthPoint;

    @ApiModelProperty("是否有免邮特权")
    @TableField("priviledge_free_freight")
    private Integer priviledgeFreeFreight;

    @ApiModelProperty("是否有签到特权")
    @TableField("priviledge_sign_in")
    private Integer priviledgeSignIn;

    @ApiModelProperty("是否有评论获奖励特权")
    @TableField("priviledge_comment")
    private Integer priviledgeComment;

    @ApiModelProperty("是否有专享活动特权")
    @TableField("priviledge_promotion")
    private Integer priviledgePromotion;

    @ApiModelProperty("是否有会员价格特权")
    @TableField("priviledge_member_price")
    private Integer priviledgeMemberPrice;

    @ApiModelProperty("是否有生日特权")
    @TableField("priviledge_birthday")
    private Integer priviledgeBirthday;

    @TableField("note")
    private String note;


}
