package com.hygge.shop.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hygge.shop.mall.entity.OmsOrder;
import com.hygge.shop.mall.entity.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-05-24
 */
@Mapper
public interface PmsProductMapper extends BaseMapper<PmsProduct> {
  @Select("<script>"
    + "select * from pms_product"
    + "</script>")
  IPage<PmsProduct> queryPmsProduct(Page<PmsProduct> page, @Param("pmsProduct") PmsProduct pmsProduct);
}
