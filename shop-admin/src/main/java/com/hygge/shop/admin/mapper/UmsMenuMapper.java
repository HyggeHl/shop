package com.hygge.shop.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hygge.shop.admin.entity.UmsMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

  @Select("<script>"
    + "select distinct a.mid, a.menu_key, a.m_name, a.m_p_id, a.m_depth, a.m_normal_icon, a.m_code, a.is_sub, a.is_tab, a.sort, a.params "
    + "from ums_menu a, ums_role_menu_relation b,ums_admin_role_relation c,ums_admin d "
    + "where d.phone = #{phone} "
    + "and a.mid = b.mid and b.role_id = c.role_id and c.admin_id= d.id and d.phone and a.m_status = '1' and b.m_auth &lt;&gt; 0 "
    + "order by m_depth, sort, mid "
    + "</script>")
  List<UmsMenu> selectMenuListByRoleIds(String phone);
}
