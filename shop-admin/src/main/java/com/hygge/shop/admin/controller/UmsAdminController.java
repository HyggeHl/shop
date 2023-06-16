package com.hygge.shop.admin.controller;

import com.hygge.shop.admin.entity.UmsAdmin;
import com.hygge.shop.admin.service.UmsAdminService;
import com.hygge.shop.admin.service.UmsRoleService;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */

@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController", description = "UmsAdmin管理")
@Controller
@RequestMapping("/umsAdmin")
public class UmsAdminController {
  @Value("${jwt.tokenHeader}")
  private String tokenHeader;
  @Value("${jwt.tokenHead}")
  private String tokenHead;
  @Autowired
  private UmsAdminService adminService;
  @Autowired
  private UmsRoleService roleService;


  @ApiOperation(value = "登录以后返回token")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult login(@Validated UmsAdmin umsAdmin) {
    String token = adminService.login(umsAdmin.getUsername(), umsAdmin.getPassword());
    if (token == null) {
      return CommonResult.validateFailed("用户名或密码错误");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }
}
