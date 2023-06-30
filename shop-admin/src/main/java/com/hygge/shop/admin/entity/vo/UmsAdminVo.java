package com.hygge.shop.admin.entity.vo;

import com.hygge.shop.admin.entity.UmsAdmin;
import com.hygge.shop.admin.entity.UmsMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: shop
 * @description: 用户信息
 * @author: hygge
 * @create: 2023/06/25
 */
@Data
public class UmsAdminVo implements Serializable {

  private static final long serialVersionUID = 1L;

  @Data
  private static class Dept implements Serializable {

    private String deptId;

    private String deptName;

    public Dept() {
      this.deptId = "";
      this.deptName = "";
    }

  }

  @Data
  private static class User implements Serializable {

    private String phone;

    private String userName;

    private String coId;

    private String userAlias;

    private String userMobile;

    private String userAvatar;

    private String roleId;

    public User(UmsAdmin umsAdmin)  {
      this.phone = umsAdmin.getPhone();
      this.userName = umsAdmin.getUsername();
    }
  }

  private User user;

  private Dept dept;

  private String token;

  private List<UmsMenu> menu;

  private String[] auth;

  public void setUser(UmsAdmin umsAdmin) {
    if (umsAdmin == null) {
      this.user = null;
      return;
    }
    if (this.user == null) {
      this.user = new User(umsAdmin);
    } else {
      this.user.setPhone(umsAdmin.getPhone());
      this.user.setUserName(umsAdmin.getUsername());
    }
  }

  public void setDept() {
    this.dept.setDeptId("");
    this.dept.setDeptName("");
  }

}
