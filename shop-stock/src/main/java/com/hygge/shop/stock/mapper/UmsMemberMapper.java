package com.hygge.shop.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hygge.shop.stock.entity.UmsMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
@Mapper
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

  @Select("<script>"
    + "select "
    + "id,member_level_id,username,password,nickname,phone,status,create_time,icon,gender,birthday,city,job, "
    + "personalized_signature,source_type,integration,growth,luckey_count,history_integration "
    + "from ums_member "
    + "<where>"
    + "<if test='username != null'>"
    + "username = #{username}"
    + "</if>"
    + "<if test='phone != null'>"
    + "phone = #{phone}"
    + "</if>"
    + "</where>"
    + "</script>")
  List<UmsMember> selectByUmsMember(UmsMember umsMember);



}
