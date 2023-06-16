package com.hygge.shop.stock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hygge.shop.common.entity.BaseBean;
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
 * 订单中所包含的商品
 * </p>
 *
 * @author hygge
 * @since 2023-06-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("oms_order_item")
@ApiModel(value = "OmsOrderItem对象", description = "订单中所包含的商品")
public class OmsOrderItem extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("订单编号")
    @TableField("order_sn")
    private String orderSn;

    @TableField("product_id")
    private Long productId;

    @TableField("product_pic")
    private String productPic;

    @TableField("product_name")
    private String productName;

    @TableField("product_brand")
    private String productBrand;

    @TableField("product_sn")
    private String productSn;

    @ApiModelProperty("销售价格")
    @TableField("product_price")
    private BigDecimal productPrice;

    @ApiModelProperty("购买数量")
    @TableField("product_quantity")
    private Integer productQuantity;

    @ApiModelProperty("商品sku编号")
    @TableField("product_sku_id")
    private Long productSkuId;

    @ApiModelProperty("商品sku条码")
    @TableField("product_sku_code")
    private String productSkuCode;

    @ApiModelProperty("商品分类id")
    @TableField("product_category_id")
    private Long productCategoryId;

    @ApiModelProperty("商品促销名称")
    @TableField("promotion_name")
    private String promotionName;

    @ApiModelProperty("商品促销分解金额")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @ApiModelProperty("优惠券优惠分解金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("积分优惠分解金额")
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    @ApiModelProperty("该商品经过优惠后的分解金额")
    @TableField("real_amount")
    private BigDecimal realAmount;

    @TableField("gift_integration")
    private Integer giftIntegration;

    @TableField("gift_growth")
    private Integer giftGrowth;

    @ApiModelProperty("商品销售属性:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    @TableField("product_attr")
    private String productAttr;


}
