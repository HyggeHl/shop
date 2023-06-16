package com.hygge.shop.mall.controller;

import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.common.service.CaptchaService;
import com.hygge.shop.mall.entity.UmsMember;
import com.hygge.shop.mall.service.UmsMemberCacheService;
import com.hygge.shop.mall.service.UmsMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
@RestController
@RequestMapping("/umsMember")
public class UmsMemberController {

  @Resource
  private CaptchaService captchaService;

  @Value("${jwt.tokenHeader}")
  private String tokenHeader;
  @Value("${jwt.tokenHead}")
  private String tokenHead;
  @Autowired
  private UmsMemberService memberService;
  @Resource
  private UmsMemberCacheService umsMemberCacheService;

  @ApiOperation("获取验证码")
  @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
  public ModelAndView getCaptcha(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type", defaultValue = "math") String type, @RequestParam("tel") String tel) {
    String rtnCode = "";
    Map<String, String> codeMap = new HashMap<>();
    codeMap.put("code", "");
    ModelAndView modelAndView = captchaService.defaultKaptcha(request, response, type, codeMap);
    umsMemberCacheService.setAuthCode(tel, codeMap.get("code"));
    return modelAndView;
  }

  @ApiOperation("会员注册")
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult register(UmsMember umsMember) {
    memberService.register(umsMember);
    return CommonResult.success(null,"注册成功");
  }

  @ApiOperation("会员登录")
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult login(UmsMember umsMember) {
    String token = memberService.login(umsMember);
    if (token == null) {
      return CommonResult.validateFailed("用户名或密码错误");
    }
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return CommonResult.success(tokenMap);
  }

  @ApiOperation("获取会员信息")
  @RequestMapping(value = "/info", method = RequestMethod.POST)
  @ResponseBody
  public CommonResult info(Principal principal) {
    if(principal==null){
      return CommonResult.unauthorized(null);
    }
    UmsMember member = memberService.getCurrentMember();
    return CommonResult.success(member);
  }

}
