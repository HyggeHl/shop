package com.hygge.shop.admin.mapper;

import com.hygge.shop.admin.entity.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hygge.shop.admin.entity.UmsAdminExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */
@Mapper
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
  
  @Select("<script>" 
    + "select"
    + " id, phone, username, password, icon, email, nick_name, note, create_time, login_time, status "
    + "from ums_admin"
    + "<where>"
    + "<if test='username != null'>"
    + "  username = #{username}"
    + "</if>"
    + "<if test='phone != null'>"
    + "phone = #{phone}"
    + "</if>"
    + "</where>"
    + "</script>")
  List<UmsAdmin>  selectByUmsAdmin(UmsAdmin umsAdmin);

}
