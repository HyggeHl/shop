package com.hygge.shop.admin.service.impl;

import com.hygge.shop.admin.entity.UmsAdminPermissionRelation;
import com.hygge.shop.admin.mapper.UmsAdminPermissionRelationMapper;
import com.hygge.shop.admin.service.UmsAdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-03-29
 */
@Service
public class UmsAdminPermissionRelationServiceImpl extends ServiceImpl<UmsAdminPermissionRelationMapper, UmsAdminPermissionRelation> implements UmsAdminPermissionRelationService {

}
