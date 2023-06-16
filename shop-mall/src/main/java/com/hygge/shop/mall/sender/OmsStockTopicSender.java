package com.hygge.shop.mall.sender;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.mall.entity.OmsOrder;
import com.hygge.shop.mall.entity.OmsOrderItem;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: shop
 * @description: 库存主题消息生产者
 * @author: hygge
 * @create: 2023/06/13
 */
@Component
public class OmsStockTopicSender {

  @Resource
  RabbitTemplate rabbitTemplate;

  public CommonResult sendDeductStockTopicMessage() throws JsonProcessingException {
    OmsOrderItem omsOrderItem = OmsOrderItem.builder().id(IdUtil.getSnowflakeNextId()).productId(1L).productQuantity(2).build();
    List<OmsOrderItem> omsOrderItemList = new ArrayList<>();
    omsOrderItemList.add(omsOrderItem);
    OmsOrder omsOrder = OmsOrder.builder().id(IdUtil.getSnowflakeNextId()).orderItemList(omsOrderItemList).build();

    ObjectMapper objectMapper = new ObjectMapper();
    String omsOrderStr = objectMapper.writeValueAsString(omsOrder);

    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentType("application/json");
    messageProperties.getHeaders().put("__TypeId__","OmsOrder");
    Message message = new Message(omsOrderStr.getBytes(),messageProperties);

    rabbitTemplate.convertAndSend("TopicExchange","topic.omsOrder",message);
    System.out.println("库存主题消息发送成功");
    return CommonResult.success("库存主题消息发送成功");
  }

  public CommonResult sendDeductStockTopicMessageReg() throws JsonProcessingException {
    OmsOrderItem omsOrderItem = OmsOrderItem.builder().id(IdUtil.getSnowflakeNextId()).productId(1L).productQuantity(2).build();
    List<OmsOrderItem> omsOrderItemList = new ArrayList<>();
    omsOrderItemList.add(omsOrderItem);
    OmsOrder omsOrder = OmsOrder.builder().id(IdUtil.getSnowflakeNextId()).orderItemList(omsOrderItemList).build();

    ObjectMapper objectMapper = new ObjectMapper();
    String omsOrderStr = objectMapper.writeValueAsString(omsOrder);

    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentType("application/json");
    messageProperties.getHeaders().put("__TypeId__","OmsOrder");
    Message message = new Message(omsOrderStr.getBytes(),messageProperties);

    rabbitTemplate.convertAndSend("TopicExchange","topic.stock",message);
    System.out.println("库存主题消息发送成功");
    return CommonResult.success("库存主题消息发送成功");
  }
}
