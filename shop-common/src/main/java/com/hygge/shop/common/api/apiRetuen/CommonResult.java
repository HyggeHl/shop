package com.hygge.shop.common.api.apiRetuen;

/**
 * @program: shop
 * @description: 通用结果返回值
 * @author: hygge
 * @create: 2023/03/23
 */
public class CommonResult<T> {
  /**
   * 状态码
   */
  private long code;
  /**
   * 提示信息
   */
  private String message;
  /**
   * 数据封装
   */
  private T data;

  /**
   * 封装page分页数据
   */
  private MyPage myPage;

  protected CommonResult() {
  }

  protected CommonResult(long code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  protected CommonResult(long code, String message, T data, MyPage myPage) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.myPage = myPage;
  }

  /**
   * 成功返回结果
   *
   * @param data 获取的数据
   */
  public static <T> CommonResult<T> success(T data) {
    return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
  }

  /**
   * 成功返回结果
   *
   * @param data 获取的数据
   * @param  message 提示信息
   */
  public static <T> CommonResult<T> success(T data, String message) {
    return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data);
  }

  /**
   * 成功返回结果(分页)
   *
   * @param data 获取的数据
   * @param  myPage 分页信息
   */
  public static <T> CommonResult<T> successPage(T data, MyPage myPage) {
    return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data, myPage);
  }

  /**
   * 成功返回结果(分页)
   *
   * @param data 获取的数据
   * @param  message 提示信息
   * @param  myPage 分页信息
   */
  public static <T> CommonResult<T> successPage(T data, String message, MyPage myPage) {
    return new CommonResult<T>(ResultCode.SUCCESS.getCode(), message, data, myPage);
  }

  /**
   * 成功返回结果
   */
  public static <T> CommonResult<T> noContent(T data, String message) {
    return new CommonResult<T>(ResultCode.NOCONTENT.getCode(), message, data);
  }

  /**
   * 失败返回结果
   * @param errorCode 错误码
   */
  public static <T> CommonResult<T> failed(IErrorCode errorCode) {
    return new CommonResult<T>(errorCode.getCode(), errorCode.getMessage(), null);
  }

  /**
   * 失败返回结果
   * @param errorCode 错误码
   * @param message 错误信息
   */
  public static <T> CommonResult<T> failed(IErrorCode errorCode,String message) {
    return new CommonResult<T>(errorCode.getCode(), message, null);
  }

  /**
   * 失败返回结果
   * @param message 提示信息
   */
  public static <T> CommonResult<T> failed(String message) {
    return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
  }

  /**
   * 失败返回结果
   */
  public static <T> CommonResult<T> failed() {
    return failed(ResultCode.FAILED);
  }

  /**
   * 参数验证失败返回结果
   */
  public static <T> CommonResult<T> validateFailed() {
    return failed(ResultCode.VALIDATE_FAILED);
  }

  /**
   * 参数验证失败返回结果
   * @param message 提示信息
   */
  public static <T> CommonResult<T> validateFailed(String message) {
    return new CommonResult<T>(ResultCode.VALIDATE_FAILED.getCode(), message, null);
  }

  /**
   * 未登录返回结果
   */
  public static <T> CommonResult<T> unauthorized(T data) {
    return new CommonResult<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
  }

  /**
   * 未授权返回结果
   */
  public static <T> CommonResult<T> forbidden(T data) {
    return new CommonResult<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
  }

  public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public MyPage getPage() {
    return myPage;
  }

  public void setPage(MyPage myPage) {
    this.myPage = myPage;
  }
}
