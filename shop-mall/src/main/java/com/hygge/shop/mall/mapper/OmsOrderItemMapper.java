package com.hygge.shop.mall.mapper;

import com.hygge.shop.mall.entity.OmsOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单中所包含的商品 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-06-12
 */
@Mapper
public interface OmsOrderItemMapper extends BaseMapper<OmsOrderItem> {

}
