package com.hygge.shop.common.api.apiLog;


import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.hygge.shop.common.entity.WebLog;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @program: shop
 * @description: 接口日志切面
 * @author: hygge
 * @create: 2023/03/27
 */
@Aspect
@Component
public class WebLogAspect {
  private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

//  @Pointcut("execution(public * com.hygge.shop.*.controller.*.*(..))")
  @Pointcut("@annotation(com.hygge.shop.common.api.annotion.ApiLog)")
  public void webLog() {
  }

  @Before("webLog()")
  public void doBefore(JoinPoint joinPoint) throws Throwable {
  }

  @AfterReturning(value = "webLog()", returning = "ret")
  public void doAfterReturning(Object ret) throws Throwable {
  }

  @Around("webLog()")
  public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    //获取当前请求对象
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    //记录请求信息
    WebLog webLog = new WebLog();
    Object result = joinPoint.proceed();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    Method method = methodSignature.getMethod();
    if (method.isAnnotationPresent(ApiOperation.class)) {
      ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
      webLog.setDescription(apiOperation.value());
    }
    long endTime = System.currentTimeMillis();
    String urlStr = request.getRequestURL().toString();
    webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
    webLog.setIp(request.getRemoteUser());
    webLog.setMethod(request.getMethod());
    webLog.setParameter(getParameter(method, joinPoint.getArgs()));
    webLog.setResult(result);
    webLog.setSpendTime((int) (endTime - startTime));
    webLog.setStartTime(startTime);
    webLog.setUri(request.getRequestURI());
    webLog.setUrl(request.getRequestURL().toString());
    LOGGER.info("{}", JSONUtil.parseObj(webLog, false));
    return result;
  }

  /**
   * 根据方法和传入的参数获取请求参数
   */
  private Object getParameter(Method method, Object[] args) {
    List<Object> argList = Arrays.asList(args);

    if (argList.size() == 0) {
      return null;
    } else if (argList.size() == 1) {
      return argList.get(0);
    } else {
      return argList;
    }
  }
}
