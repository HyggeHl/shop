package com.hygge.shop.admin.mapper;

import com.hygge.shop.admin.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-04-19
 */
@Mapper
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
  @Select("<script>"
    + "select distinct id, create_time, name, url, description, category_id "
    + " from ums_resource "
    + "</script>")
  List<UmsResource> listAll();

}
