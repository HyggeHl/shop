package com.hygge.shop.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hygge.shop.common.exception.Asserts;
import com.hygge.shop.common.utils.SpringUtils;
import com.hygge.shop.mall.entity.MemberDetails;
import com.hygge.shop.mall.entity.UmsMember;
import com.hygge.shop.mall.entity.UmsMemberLevel;
import com.hygge.shop.mall.mapper.UmsMemberLevelMapper;
import com.hygge.shop.mall.mapper.UmsMemberMapper;
import com.hygge.shop.mall.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hygge.shop.security.shopsecurity.utils.JwtTokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-04-23
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UmsMemberServiceImpl.class);
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UmsMemberMapper memberMapper;
  @Autowired
  private UmsMemberLevelMapper memberLevelMapper;
//  @Autowired
//  private UmsMemberCacheService memberCacheService;
//  @Value("${redis.key.authCode}")
//  private String REDIS_KEY_PREFIX_AUTH_CODE;
//  @Value("${redis.expire.authCode}")
//  private Long AUTH_CODE_EXPIRE_SECONDS;

  @Override
  public void register(UmsMember umsMember) {
    //验证验证码
    if (StringUtils.isEmpty(umsMember.getAuthCode()) || !umsMember.getAuthCode().equals(SpringUtils.GetCaptcha())) {
      Asserts.fail("验证码错误");
    }

//    if(!verifyAuthCode(authCode,telephone)){
//      Asserts.fail("验证码错误");
//    }

    //查询是否已有该用户
    List<UmsMember> umsMembers = memberMapper.selectByUmsMember(umsMember);
    if (!CollectionUtils.isEmpty(umsMembers)) {
      Asserts.fail("该用户已经存在");
    }
    //没有该用户进行添加操作
    umsMember.setPassword(passwordEncoder.encode(umsMember.getPassword()));
    umsMember.setStatus(1);
    //获取默认会员等级并设置
    UmsMemberLevel umsMemberLevel = UmsMemberLevel.builder()
      .defaultStatus(1)
      .build();
    List<UmsMemberLevel> memberLevelList = memberLevelMapper.selectDefaultLevel(umsMemberLevel);
    if (!CollectionUtils.isEmpty(memberLevelList)) {
      umsMember.setMemberLevelId(memberLevelList.get(0).getId());
    }
    memberMapper.insert(umsMember);
    umsMember.setPassword(null);
  }

  @Override
  public String login(UmsMember umsMember) {
    String token = null;
    //密码需要客户端加密后传递
    try {
      //验证验证码
      if (StringUtils.isEmpty(umsMember.getAuthCode()) || !umsMember.getAuthCode().equals(SpringUtils.GetCaptcha())) {
        Asserts.fail("验证码错误");
      }

//      UserDetails userDetails = loadUserByUsername(umsMember.getUsername());
//      UserDetails userDetails = loadUserByPhone(umsMember.getPhone());
      MemberDetails memberDetails = loadUserByPhone(umsMember.getPhone());
      if(!passwordEncoder.matches(umsMember.getPassword(),memberDetails.getPassword())){
        throw new BadCredentialsException("密码不正确");
      }
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
      token = jwtTokenUtil.generateTokenCommon(memberDetails.getUmsMember().getPhone());
    } catch (AuthenticationException e) {
      LOGGER.warn("登录异常:{}", e.getMessage());
    }
    return token;
  }

  @Override
  public UmsMember getByUsername(String username) {
//    UmsMember member = memberCacheService.getMember(username);
//    if(member!=null) return member;
//    UmsMemberExample example = new UmsMemberExample();
//    example.createCriteria().andUsernameEqualTo(username);
//    List<UmsMember> memberList = memberMapper.selectByExample(example);
//    if (!CollectionUtils.isEmpty(memberList)) {
//      member = memberList.get(0);
//      memberCacheService.setMember(member);
//      return member;
//    }
//    return null;

    //直接从数据库查询
    UmsMember umsMember = UmsMember.builder()
      .username(username)
      .build();
    List<UmsMember> umsMemberList = memberMapper.selectByUmsMember(umsMember);
    if (!CollectionUtils.isEmpty(umsMemberList)) {
      umsMember = umsMemberList.get(0);
      return umsMember;
    }
    return null;
  }

  @Override
  public UmsMember getByPhone(String phone) {

    //直接从数据库查询
    UmsMember umsMember = UmsMember.builder()
      .phone(phone)
      .build();
    List<UmsMember> umsMemberList = memberMapper.selectByUmsMember(umsMember);
    if (!CollectionUtils.isEmpty(umsMemberList)) {
      umsMember = umsMemberList.get(0);
      return umsMember;
    }
    return null;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    UmsMember member = getByUsername(username);
    if(member!=null){
      return new MemberDetails(member);
    }
    throw new UsernameNotFoundException("用户名或密码错误");
  }

  @Override
  public MemberDetails loadUserByPhone(String phone) {
    UmsMember member = getByPhone(phone);
    if(member!=null){
      return new MemberDetails(member);
    }
    throw new UsernameNotFoundException("手机号或密码错误");
  }

  @Override
  public UmsMember getCurrentMember() {
    SecurityContext ctx = SecurityContextHolder.getContext();
    Authentication auth = ctx.getAuthentication();
    MemberDetails memberDetails = (MemberDetails) auth.getPrincipal();
    return memberDetails.getUmsMember();
  }

  //对输入的验证码进行校验
//  private boolean verifyAuthCode(String authCode, String telephone){
//    if(StrUtil.isEmpty(authCode)){
//      return false;
//    }
//    String realAuthCode = memberCacheService.getAuthCode(telephone);
//    return authCode.equals(realAuthCode);
//  }
}
