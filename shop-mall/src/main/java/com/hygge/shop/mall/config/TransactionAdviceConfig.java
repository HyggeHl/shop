package com.hygge.shop.mall.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: shop
 * @description: AOP配置全局事务
 * @author: hygge
 * @create: 2023/03/24
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
  //事务的超时时间为10秒
  private static final int TX_METHOD_TIMEOUT = 10;

  //配置切入点表达式 : 指定哪些包中的类使用事务
  private static final String AOP_POINTCUT_EXPRESSION = "execution (* com..*.service.*.*(..))";

  //事务管理器
  @Resource
  private TransactionManager transactionManager;
  /**
   * 声明业务方法的事务属性
   */
  @Bean
  public TransactionInterceptor txAdvice() {

    /**
     * 这里配置只读事务
     */
    RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
    readOnlyTx.setReadOnly(true);//是否只读
    readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);//事务的传播行为

    /**
     * 必须带事务
     * 当前存在事务就使用当前事务，当前不存在事务,就开启一个新的事务
     */
    RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
    //检查型异常也回滚
    requiredTx.setRollbackRules(
      Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
    requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    requiredTx.setTimeout(TX_METHOD_TIMEOUT);

    /**
     * 无事务地执行，挂起任何存在的事务
     */
    RuleBasedTransactionAttribute noTx = new RuleBasedTransactionAttribute();
    noTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);

    /**
     * 设置方法对应的事务
     */
    Map<String, TransactionAttribute> txMap = new HashMap<>();
    //只读事务
    txMap.put("get*", readOnlyTx);
    txMap.put("query*", readOnlyTx);
    txMap.put("find*", readOnlyTx);
    txMap.put("list*", readOnlyTx);
    txMap.put("count*", readOnlyTx);
    txMap.put("exist*", readOnlyTx);
    txMap.put("search*", readOnlyTx);
    txMap.put("fetch*", readOnlyTx);
    //无事务
    txMap.put("noTx*", noTx);
    //写事务
    txMap.put("add*", requiredTx);
    txMap.put("save*", requiredTx);
    txMap.put("insert*", requiredTx);
    txMap.put("update*", requiredTx);
    txMap.put("modify*", requiredTx);
    txMap.put("delete*", requiredTx);

    NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
    source.setNameMap(txMap);

    //返回事务拦截器
    return new TransactionInterceptor(transactionManager, source);
  }

  @Bean
  public Advisor txAdviceAdvisor() {
    //配置事务切入点表达式
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
    //增强事务，关联切入点和事务属性
    return new DefaultPointcutAdvisor(pointcut, txAdvice());
  }
}
