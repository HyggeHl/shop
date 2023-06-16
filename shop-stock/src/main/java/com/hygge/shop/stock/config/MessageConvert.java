package com.hygge.shop.stock.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hygge.shop.common.entity.MessageConfig;
import com.hygge.shop.stock.entity.OmsOrder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @program: shop
 * @description: json序列化
 * @author: hygge
 * @create: 2023/06/12
 */
@Configuration
public class MessageConvert {

  @Bean
  public MessageConverter messageConverter() {

    //消费端配置映射
    String DEFAULT_TYPE_CLASS="__TypeId__";
    Map<String, Class<?>> mqObjectMap=new HashMap<String, Class<?>>();
    mqObjectMap.put("OmsOrder", OmsOrder.class);
    mqObjectMap.put("MessageConfig", MessageConfig.class);

    ObjectMapper objectMapper = new ObjectMapper();
    //设置转换的实体类可见性[避免属性访问权问题所导致的缺少字段]
    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper);
    //因为实体类的包名不同的问题；重写 classMapper映射，不然接收消息异常报错
    jackson2JsonMessageConverter.setClassMapper(new ClassMapper() {
      @Override
      public void fromClass(Class<?> aClass, MessageProperties messageProperties) {
        //  messageProperties.setHeader("__TypeId__", "com.gjpzh.robotcrm.authentication.entity.Resource");
      }
      @Override
      public Class<?> toClass(MessageProperties messageProperties) {
        Object obj = messageProperties.getHeaders().get(DEFAULT_TYPE_CLASS);
        if(null != mqObjectMap.get(obj.toString())) {
          return mqObjectMap.get(obj.toString());
        }
        DefaultJackson2JavaTypeMapper defaultJackson2JavaTypeMapper = new DefaultJackson2JavaTypeMapper();
        return defaultJackson2JavaTypeMapper.toClass(messageProperties);
      }
    });
    return jackson2JsonMessageConverter;
  }
}
