package com.hygge.shop.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hygge.shop.stock.entity.UmsMemberLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员等级表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
@Mapper
public interface UmsMemberLevelMapper extends BaseMapper<UmsMemberLevel> {

  @Select("<script>"
    + "select "
    + "id,name,growth_point,default_status,free_freight_point,comment_growth_point,priviledge_free_freight,priviledge_sign_in,"
    + "priviledge_comment,priviledge_promotion,priviledge_member_price,priviledge_birthday,note "
    + "from ums_member_level "
    + "where "
    + "default_status = 1"
    + "</script>")
  List<UmsMemberLevel> selectDefaultLevel(UmsMemberLevel umsMemberLevel);

}
