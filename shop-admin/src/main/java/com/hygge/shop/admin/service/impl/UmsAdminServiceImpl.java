package com.hygge.shop.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.hygge.shop.admin.entity.*;
import com.hygge.shop.admin.mapper.UmsAdminMapper;
import com.hygge.shop.admin.mapper.UmsAdminRoleRelationMapper;
import com.hygge.shop.admin.service.UmsAdminCacheService;
import com.hygge.shop.admin.service.UmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hygge.shop.common.exception.Asserts;
import com.hygge.shop.common.utils.SpringUtils;
import com.hygge.shop.security.shopsecurity.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements UmsAdminService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UmsAdminServiceImpl.class);
  @Resource
  private JwtTokenUtil jwtTokenUtil;
  @Resource
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UmsAdminMapper adminMapper;
  @Resource
  private UmsAdminCacheService umsAdminCacheService;

  @Override
  public void register(UmsAdmin umsAdmin) {
    //验证验证码
//    if (StringUtils.isEmpty(umsAdmin.getAuthCode()) || !umsAdmin.getAuthCode().equals(SpringUtils.GetCaptcha())) {
//      Asserts.fail("验证码错误");
//    }

//    if(!verifyAuthCode(authCode,telephone)){
//      Asserts.fail("验证码错误");
//    }

    //查询是否已有该用户
    List<UmsAdmin> umsMembers = adminMapper.selectByUmsAdmin(umsAdmin);
    if (!CollectionUtils.isEmpty(umsMembers)) {
      Asserts.fail("该用户已经存在");
    }
    //没有该用户进行添加操作
    umsAdmin.setPassword(passwordEncoder.encode(umsAdmin.getPassword()));
    umsAdmin.setStatus(1);
    adminMapper.insert(umsAdmin);
    umsAdmin.setPassword(null);
  }

  @Override
  public String login(UmsAdmin umsAdmin) {
    String token = null;
    //密码需要客户端加密后传递
    try {
      //验证验证码
      if (StringUtils.isEmpty(umsAdmin.getAuthCode()) || !umsAdmin.getAuthCode().equals(umsAdminCacheService.getAuthCode(umsAdmin.getPhone()))) {
        Asserts.fail("验证码错误");
      }

//      UserDetails userDetails = loadUserByUsername(umsAdmin.getUsername());
//      UserDetails userDetails = loadUserByPhone(umsAdmin.getPhone());
      AdminDetails adminDetails = loadUserByPhone(umsAdmin.getPhone());
      if(!passwordEncoder.matches(umsAdmin.getPassword(),adminDetails.getPassword())){
        throw new BadCredentialsException("密码不正确");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(adminDetails, null, adminDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateTokenCommon(adminDetails.getUmsMember().getPhone());
    } catch (AuthenticationException e) {
      LOGGER.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  @Override
  public UmsAdmin getByUsername(String username) {
//    UmsAdmin member = memberCacheService.getMember(username);
//    if(member!=null) return member;
//    UmsMemberExample example = new UmsMemberExample();
//    example.createCriteria().andUsernameEqualTo(username);
//    List<UmsAdmin> memberList = adminMapper.selectByExample(example);
//    if (!CollectionUtils.isEmpty(memberList)) {
//      member = memberList.get(0);
//      memberCacheService.setMember(member);
//      return member;
//    }
//    return null;

    //直接从数据库查询
    UmsAdmin umsAdmin = UmsAdmin.builder()
      .username(username)
      .build();
    List<UmsAdmin> umsMemberList = adminMapper.selectByUmsAdmin(umsAdmin);
    if (!CollectionUtils.isEmpty(umsMemberList)) {
      umsAdmin = umsMemberList.get(0);
      return umsAdmin;
    }
    return null;
  }

  @Override
  public UmsAdmin getByPhone(String phone) {

    //直接从数据库查询
    UmsAdmin umsAdmin = UmsAdmin.builder()
      .phone(phone)
      .build();
    List<UmsAdmin> umsMemberList = adminMapper.selectByUmsAdmin(umsAdmin);
    if (!CollectionUtils.isEmpty(umsMemberList)) {
      umsAdmin = umsMemberList.get(0);
      return umsAdmin;
    }
    return null;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    UmsAdmin member = getByUsername(username);
    if(member!=null){
      return new AdminDetails(member);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }

  @Override
  public AdminDetails loadUserByPhone(String phone) {
    UmsAdmin member = getByPhone(phone);
    if(member!=null){
      return new AdminDetails(member);
    }
    throw new UsernameNotFoundException("手机号或密码错误");
  }

  @Override
  public UmsAdmin getCurrentAdmin() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();
    AdminDetails adminDetails = (AdminDetails) auth.getPrincipal();
    return adminDetails.getUmsMember();
  }
}
