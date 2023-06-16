package com.hygge.shop.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hygge.shop.mall.entity.OmsOrder;
import com.hygge.shop.mall.mapper.OmsOrderMapper;
import com.hygge.shop.mall.service.OmsOrderService;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.common.api.apiRetuen.MyPage;
import com.hygge.shop.common.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-03-27
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
  @Autowired
  private OmsOrderMapper OmsOrderMapper;

  @Override
  public CommonResult queryOmsOrder(OmsOrder params) {
    IPage<OmsOrder> page =  OmsOrderMapper.queryOmsOrder(new Page<OmsOrder>(params.getSearch().getCurrentPage(), params.getSearch().getPageSize()), params);
    MyPage myPage = new PageUtil().dealPage(page);
    CommonResult commonResult = CommonResult.successPage(page.getRecords(), myPage);
    return commonResult;
  }

  @Override
  public CommonResult getOmsOrder(OmsOrder params) {
    return CommonResult.success(OmsOrderMapper.selectById(params.getId()));
  }

  @Override
  public void insertOmsOrder(OmsOrder params) throws RuntimeException {
    int count = OmsOrderMapper.insert(params);
//    if(count > 0) {
//      return CommonResult.success(count);
//    }
//    return CommonResult.failed();
  }

  @Override
  public CommonResult updateOmsOrder(OmsOrder params) {
    int count = OmsOrderMapper.updateById(params);
    if(count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @Override
  public CommonResult deleteOmsOrder(OmsOrder params) {
    int count = OmsOrderMapper.deleteById(params);
    if(count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @Override
  public CommonResult deleteOmsOrderBatch(List<OmsOrder> list) {
    return CommonResult.success("success");
  }

  @Override
  public CommonResult getOmsOrderList(OmsOrder params) {
    QueryWrapper<OmsOrder> query = new QueryWrapper<>();
    return CommonResult.success(OmsOrderMapper.selectList(query));
  }
}
