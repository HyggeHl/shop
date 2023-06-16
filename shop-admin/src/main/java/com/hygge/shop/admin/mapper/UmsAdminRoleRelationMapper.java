package com.hygge.shop.admin.mapper;

import com.hygge.shop.admin.entity.UmsAdminRoleRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hygge.shop.admin.entity.UmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */
@Mapper
public interface UmsAdminRoleRelationMapper extends BaseMapper<UmsAdminRoleRelation> {

  @Select("<script>"
    + "SELECT"
    + "    ur.id id,"
    + "    ur.create_time createTime, "
    + "    ur.`name` `name`, "
    + "    ur.url url, "
    + "    ur.description description, "
    + "    ur.category_id categoryId "
    + "FROM "
    + "    ums_admin_role_relation ar "
    + "LEFT JOIN ums_role r ON ar.role_id = r.id "
    + "LEFT JOIN ums_role_resource_relation rrr ON r.id = rrr.role_id "
    + "LEFT JOIN ums_resource ur ON ur.id = rrr.resource_id "
    + "WHERE "
    + "    ar.admin_id = #{adminId} "
    + "AND ur.id IS NOT NULL "
    + "GROUP BY "
    + "    ur.id "
    + "</script>")
  List<UmsResource> getResourceList(@Param("adminId") Long adminId);

}
