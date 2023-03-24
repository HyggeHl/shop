package com.hygge.shop.mall.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hygge.shop.mall.entity.OrdBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hygge.shop.shopcommon.api.CommonResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 商城，订单表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-03-23
 */
@Mapper
public interface OrdBaseMapper extends BaseMapper<OrdBase> {

  @Select("<script>"
    + "select * from t_ord_base"
    + "</script>")
  IPage<OrdBase> queryOrdBase(Page<OrdBase> page, @Param("OrdBase") OrdBase params);

}
