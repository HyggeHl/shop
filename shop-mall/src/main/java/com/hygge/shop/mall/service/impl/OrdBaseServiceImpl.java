package com.hygge.shop.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hygge.shop.mall.entity.OrdBase;
import com.hygge.shop.mall.mapper.OrdBaseMapper;
import com.hygge.shop.mall.service.OrdBaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hygge.shop.shopcommon.api.CommonResult;
import com.hygge.shop.shopcommon.api.MyPage;
import com.hygge.shop.shopcommon.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商城，订单表 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-03-23
 */
@Service
//@Transactional
public class OrdBaseServiceImpl extends ServiceImpl<OrdBaseMapper, OrdBase> implements OrdBaseService {
  @Autowired
  private OrdBaseMapper ordBaseMapper;

  @Override
  public CommonResult queryOrdBase(OrdBase params) {
    IPage<OrdBase> page =  ordBaseMapper.queryOrdBase(new Page<OrdBase>(params.getSearch().getCurrentPage(), params.getSearch().getPageSize()), params);
    MyPage myPage = new PageUtil().dealPage(page);
    CommonResult commonResult = CommonResult.successPage(page.getRecords(), myPage);
    return commonResult;
  }

  @Override
  public CommonResult getOrdBase(OrdBase params) {
    return CommonResult.success(ordBaseMapper.selectById(params.getOrderid()));
  }

  @Override
  public void insertOrdBase(OrdBase params) throws RuntimeException {
      int count = ordBaseMapper.insert(params);
      throw new RuntimeException("抛出异常测试！！！");
//    if(count > 0) {
//      return CommonResult.success(count);
//    }
//    return CommonResult.failed();
  }

  @Override
  public CommonResult updateOrdBase(OrdBase params) {
    int count = ordBaseMapper.updateById(params);
    if(count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @Override
  public CommonResult deleteOrdBase(OrdBase params) {
    int count = ordBaseMapper.deleteById(params);
    if(count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @Override
  public CommonResult deleteOrdBaseBatch(List<OrdBase> list) {
    return CommonResult.success("success");
  }

  @Override
  public CommonResult getOrdBaseList(OrdBase params) {
    QueryWrapper<OrdBase> query = new QueryWrapper<>();
    return CommonResult.success(ordBaseMapper.selectList(query));
  }
}
