package com.hygge.shop.mall.service.impl;

import com.hygge.shop.mall.entity.OmsOrderItem;
import com.hygge.shop.mall.mapper.OmsOrderItemMapper;
import com.hygge.shop.mall.service.OmsOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单中所包含的商品 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-06-12
 */
@Service
public class OmsOrderItemServiceImpl extends ServiceImpl<OmsOrderItemMapper, OmsOrderItem> implements OmsOrderItemService {

}
