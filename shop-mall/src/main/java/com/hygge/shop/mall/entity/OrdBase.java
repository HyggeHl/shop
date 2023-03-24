package com.hygge.shop.mall.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hygge.shop.shopcommon.entity.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 商城，订单表
 * </p>
 *
 * @author hygge
 * @since 2023-03-23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_ord_base")
@ApiModel(value = "OrdBase对象", description = "商城，订单表")
public class OrdBase extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单编号")
    @TableId(value = "orderid", type = IdType.ASSIGN_ID)
    private String orderid;

    @ApiModelProperty("客户编号")
    @TableField("custid")
    private String custid;

    @ApiModelProperty("地址编号")
    @TableField("addressid")
    private String addressid;

    @ApiModelProperty("配送日期")
    @TableField("senddate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime senddate;

    @ApiModelProperty("配送开始")
    @TableField("sendbegin")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendbegin;

    @ApiModelProperty("配送结束")
    @TableField("sendend")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sendend;

    @ApiModelProperty("总价")
    @TableField("totalprice")
    private BigDecimal totalprice;

    @ApiModelProperty("实际价格")
    @TableField("realprice")
    private BigDecimal realprice;

    @ApiModelProperty("支付方式")
    @TableField("paytype")
    private String paytype;

    @ApiModelProperty("订单来源（看pmt_cust_source）")
    @TableField("ordersource")
    private String ordersource;

    @ApiModelProperty("满减")
    @TableField("scoreamt")
    private BigDecimal scoreamt;

    @ApiModelProperty("折扣")
    @TableField("discountamt")
    private BigDecimal discountamt;

    @TableField("strategyid")
    private String strategyid;

    @ApiModelProperty("创建时间")
    @TableField(value = "createdate", fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdate;

    @ApiModelProperty("支付状态")
    @TableField("paystatus")
    private String paystatus;

    @ApiModelProperty("支付时间")
    @TableField("paydate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paydate;

    @ApiModelProperty("支付流水号")
    @TableField("paysysid")
    private String paysysid;

    @ApiModelProperty("支付appid")
    @TableField("appid")
    private String appid;

    @ApiModelProperty("关联订单")
    @TableField("relatedorder")
    private String relatedorder;

    @ApiModelProperty("送达日期")
    @TableField("dispatchdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dispatchdate;

    @ApiModelProperty("确认收货日期")
    @TableField("okdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime okdate;

    @ApiModelProperty("订单状态")
    @TableField("orderstatus")
    private String orderstatus;

    @ApiModelProperty("撤销标志")
    @TableField("orderflag")
    private String orderflag;

    @ApiModelProperty("撤销编号")
    @TableField("returnid")
    private String returnid;

    @ApiModelProperty("同步状态")
    @TableField("acwflag")
    private String acwflag;

    @ApiModelProperty("是否开票")
    @TableField("isinvoice")
    private Boolean isinvoice;

    @ApiModelProperty("发票编号")
    @TableField("invoicetranid")
    private String invoicetranid;

    @ApiModelProperty("打印日期")
    @TableField("printdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime printdate;

    @ApiModelProperty("是否评论")
    @TableField("iscomment")
    private Boolean iscomment;

    @ApiModelProperty("评论日期")
    @TableField("commentdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime commentdate;

    @TableField("busflag")
    private String busflag;

    @ApiModelProperty("ACW订单号")
    @TableField("acworderid")
    private String acworderid;

    @ApiModelProperty("水站编号")
    @TableField("watershopid")
    private String watershopid;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
