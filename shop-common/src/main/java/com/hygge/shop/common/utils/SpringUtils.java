package com.hygge.shop.common.utils;

import com.google.code.kaptcha.Constants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @program: shop
 * @description: spring上下文
 * @author: hygge
 * @create: 2023/04/23
 */
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
  /** Spring应用上下文环境 */
  private static ConfigurableListableBeanFactory beanFactory;

  private static ApplicationContext applicationContext = null;

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    SpringUtils.beanFactory = beanFactory;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
  {
    if (SpringUtils.applicationContext == null) {
      SpringUtils.applicationContext = applicationContext;
    }
  }

  //获取applicationContext
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  //通过name获取 Bean.
  public static <T> T  getBean(String name) {
    return (T) beanFactory.getBean(name);

  }

  //通过class获取Bean.
  public static <T> T getBean(Class<T> clazz) {
    return getApplicationContext().getBean(clazz);
  }

  //通过name,以及Clazz返回指定的Bean
  public static <T> T getBean(String name, Class<T> clazz) {
    return getApplicationContext().getBean(name, clazz);
  }

  /**
   * @return Spring容器下获取Request
   */
  public static HttpServletRequest GetRequest() {
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    return servletRequestAttributes == null ? null : servletRequestAttributes.getRequest();
  }

  public static HttpSession GetSession() {
    HttpServletRequest request = GetRequest();
    return request == null ? null : request.getSession();
  }

  public static String GetCaptcha() {
    Object captcha = GetSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
    return captcha == null ? null : captcha.toString();
  }
}
