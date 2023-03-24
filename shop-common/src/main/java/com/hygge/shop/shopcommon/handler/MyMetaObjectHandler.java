package com.hygge.shop.shopcommon.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @program: shop
 * @description: mybatisplus 字段填充扩展实现
 * @author: hygge
 * @create: 2023/03/23
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void insertFill(MetaObject metaObject) {
    try {
      this.setFieldValByName("createdate", LocalDateTime.now(),metaObject);
    } catch (ReflectionException var3) {
      logger.warn("MyMetaObjectHandler处理过程中无'createDate'相关字段，不做处理");
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    try {
      this.setFieldValByName("createdate", LocalDateTime.now(),metaObject);
    } catch (ReflectionException var3) {
      logger.warn("MyMetaObjectHandler处理过程中无'updateDate'相关字段，不做处理");
    }
  }
}
