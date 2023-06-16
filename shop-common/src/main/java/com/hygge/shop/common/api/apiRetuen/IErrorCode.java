package com.hygge.shop.common.api.apiRetuen;

/**
 * api返回码接口
 */
public interface IErrorCode {
  /**
   * 返回码
   */
  long getCode();

  /**
   * 返回信息
   */
  String getMessage();
}
