package com.hygge.shop.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.common.api.apiRetuen.MyPage;
import com.hygge.shop.common.utils.PageUtil;
import com.hygge.shop.mall.entity.PmsProduct;
import com.hygge.shop.mall.entity.PmsProduct;
import com.hygge.shop.mall.mapper.PmsProductMapper;
import com.hygge.shop.mall.service.PmsProductCacheService;
import com.hygge.shop.mall.service.PmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author hygge
 * @since 2023-05-24
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
  @Autowired
  private PmsProductMapper pmsProductMapper;
  @Autowired
  private PmsProductCacheService pmsProductCacheService;
  @Autowired
  private RedisTemplate redisTemplate;
  @Resource
  private RedissonClient redissonClient;

  @Value("${redis.database}")
  private String REDIS_DATABASE;
  @Value("${redis.key.stock}")
  private String REDIS_KEY_STOCK;
  @Value("${redis.key.stockLock}")
  private String REDIS_KEY_STOCKLOCK;
  @Value("${redis.expire.stockLock}")
  private Long REDIS_EXPIRE_STOCKLOCK;

  Logger logger = LoggerFactory.getLogger(PmsProductServiceImpl.class);

  @Override
  public CommonResult deductStock() {
    String REDIS_LOCK = "commonStockLock";

    String uniqueIdentification = IdUtil.getSnowflakeNextIdStr();
    try {
      //尝试加锁
      Boolean addLock = pmsProductCacheService.setStockLock(REDIS_LOCK, uniqueIdentification);

      if(!addLock) {
        //加锁失败
        logger.warn("加锁失败");
        return CommonResult.failed("加锁失败");
      }
      logger.info("加锁成功");

      int goodsStock = pmsProductCacheService.getStock("goods001");
      if(goodsStock > 0) {
        int realStock = goodsStock - 1;
        pmsProductCacheService.setStock("goods001", realStock);
        logger.info("扣减成功，剩余库存：{}", realStock);
        return CommonResult.success("扣减成功，剩余库存：" + realStock);
      }else {
        logger.warn("扣减失败，库存不足");
      }
      return CommonResult.failed("扣减失败，库存不足");

    }finally {
      try {
        String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] "
          + "then "
          + "return redis.call('del', KEYS[1]) "
          + "else "
          + " return 0 "
          + "end";

        String realLockKey = REDIS_DATABASE + ":" + REDIS_KEY_STOCKLOCK + ":" + REDIS_LOCK;
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_SCRIPT, Long.class);
        Object execResult = redisTemplate.execute(redisScript, Collections.singletonList(realLockKey), uniqueIdentification);

        if("1".equals(execResult.toString())) {
          logger.info("解锁成功");
        }else {
          logger.warn("解锁失败");
        }
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public CommonResult deductStockRedisson() {
    String REDIS_LOCK = "commonStockLock";

    RLock lock = redissonClient.getLock(REDIS_LOCK);
    lock.lock();

    try {
      int goodsStock = pmsProductCacheService.getStock("goods001");
      if(goodsStock > 0) {
        int realStock = goodsStock - 1;
        pmsProductCacheService.setStock("goods001", realStock);
        logger.info("扣减成功，剩余库存：{}", realStock);
        return CommonResult.success("扣减成功，剩余库存：" + realStock);
      }else {
        logger.warn("扣减失败，库存不足");
      }
    }catch (Exception e) {
      e.printStackTrace();
    }finally {
      lock.unlock();
    }
    return CommonResult.failed("扣减失败，请排查问题");
  }

  @Override
  public CommonResult queryPmsProduct(PmsProduct params) {
    IPage<PmsProduct> page =  pmsProductMapper.queryPmsProduct(new Page<PmsProduct>(params.getSearch().getCurrentPage(), params.getSearch().getPageSize()), params);
    MyPage myPage = new PageUtil().dealPage(page);
    CommonResult commonResult = CommonResult.successPage(page.getRecords(), myPage);
    return commonResult;
  }

  @Override
  public CommonResult getPmsProduct(PmsProduct params) {
    return CommonResult.success(pmsProductMapper.selectById(params.getId()));
  }

  @Override
  public void insertPmsProduct(PmsProduct params) throws RuntimeException {
    int count = pmsProductMapper.insert(params);
  }

  @Override
  public CommonResult updatePmsProduct(PmsProduct params) {
    int count = pmsProductMapper.updateById(params);
    if(count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @Override
  public CommonResult deletePmsProduct(PmsProduct params) {
    int count = pmsProductMapper.deleteById(params);
    if(count > 0) {
      return CommonResult.success(count);
    }
    return CommonResult.failed();
  }

  @Override
  public CommonResult deletePmsProductBatch(List<PmsProduct> list) {
    List<Long> ids = new ArrayList<>();
    for(PmsProduct params : list) {
      ids.add(params.getId());
    }
    return CommonResult.success(pmsProductMapper.deleteBatchIds(ids));
  }

  @Override
  public CommonResult getPmsProductList(PmsProduct params) {
    QueryWrapper<PmsProduct> query = new QueryWrapper<>();
    return CommonResult.success(pmsProductMapper.selectList(query));
  }
}
