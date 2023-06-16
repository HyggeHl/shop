package com.hygge.shop.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hygge.shop.mall.entity.OmsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-03-27
 */
@Mapper
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
  @Select("<script>"
    + "select * from oms_order"
    + "</script>")
  IPage<OmsOrder> queryOmsOrder(Page<OmsOrder> page, @Param("OmsOrder") OmsOrder params);
}
