package com.hygge.shop.admin.controller;

import com.hygge.shop.admin.entity.UmsAdmin;
import com.hygge.shop.admin.entity.UmsMenu;
import com.hygge.shop.admin.entity.vo.UmsAdminVo;
import com.hygge.shop.admin.service.UmsAdminCacheService;
import com.hygge.shop.admin.service.UmsAdminService;
import com.hygge.shop.admin.service.UmsMenuService;
import com.hygge.shop.admin.service.UmsRoleService;
import com.hygge.shop.admin.service.impl.UmsAdminServiceImpl;
import com.hygge.shop.admin.service.impl.UmsMenuServiceImpl;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.common.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
  private UmsAdminServiceImpl adminService;
  @Autowired
  private UmsRoleService roleService;
  @Autowired
  private UmsMenuServiceImpl umsMenuService;
  @Resource
  private CaptchaService captchaService;
  @Resource
  private UmsAdminCacheService umsAdminCacheService;


  /**
   * 获取用户信息用于前端缓存
   * @return UserInfo
   */
  @RequestMapping("getUserInfo")
  @ResponseBody
  public UmsAdminVo getUserInfo () {
    UmsAdminVo userInfo = new UmsAdminVo();
    UmsAdmin sysUser = new UmsAdmin();
    userInfo.setUser(sysUser);
    List<UmsMenu> menuList = umsMenuService.selectMenuList();
    userInfo.setMenu(menuList);
    return userInfo;
  }



  @ApiOperation("获取验证码")
  @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
  public ModelAndView getCaptcha(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", defaultValue = "math") String type, @RequestParam("tel") String tel) {
    String rtnCode = "";
    Map<String, String> codeMap = new HashMap<>();
    codeMap.put("code", "");
    ModelAndView modelAndView = captchaService.defaultKaptcha(request, response, type, codeMap);
    umsAdminCacheService.setAuthCode(tel, codeMap.get("code"));
    return modelAndView;
  }

  @ApiOperation("会员注册")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult register(UmsAdmin umsAdmin) {
    adminService.register(umsAdmin);
    return CommonResult.success(null,"注册成功");
  }

  @ApiOperation("会员登录")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult login(UmsAdmin umsAdmin) {
    String token = adminService.login(umsAdmin);
    if (token == null) {
      return CommonResult.validateFailed("用户名或密码错误");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }
}
