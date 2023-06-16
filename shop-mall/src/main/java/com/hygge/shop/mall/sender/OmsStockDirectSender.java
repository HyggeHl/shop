package com.hygge.shop.mall.sender;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hygge.shop.common.api.apiRetuen.CommonResult;
import com.hygge.shop.common.entity.MessageConfig;
import com.hygge.shop.mall.entity.OmsOrder;
import com.hygge.shop.mall.entity.OmsOrderItem;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: shop
 * @description: 库存消息发送
 * @author: hygge
 * @create: 2023/06/13
 */
@Component
public class OmsStockDirectSender {

  @Resource
  RabbitTemplate rabbitTemplate;

  public CommonResult sendDeductStockMessage() throws JsonProcessingException {
    OmsOrderItem omsOrderItem = OmsOrderItem.builder().id(IdUtil.getSnowflakeNextId()).productId(1L).productQuantity(2).build();
    List<OmsOrderItem> omsOrderItemList = new ArrayList<>();
    omsOrderItemList.add(omsOrderItem);
    OmsOrder omsOrder = OmsOrder.builder().id(IdUtil.getSnowflakeNextId()).orderItemList(omsOrderItemList).build();

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(omsOrder);
    System.out.println(json);

    MessageProperties messageProperties = new MessageProperties();
    //消息持久化
    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

    messageProperties.setContentType("application/json");
    messageProperties.getHeaders().put("__TypeId__","OmsOrder");
    Message message = new Message(json.getBytes(),messageProperties);

    //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
    rabbitTemplate.convertAndSend("DirectExchange", "DirectRouting", message);
    return CommonResult.success("库存消息发送成功");
  }

  public CommonResult sendDeductStockMessageJson() throws JsonProcessingException {
    String messageId = IdUtil.getSnowflakeNextIdStr();
    OmsOrderItem omsOrderItem = OmsOrderItem.builder().id(IdUtil.getSnowflakeNextId()).productId(1L).productQuantity(2).build();
    List<OmsOrderItem> omsOrderItemList = new ArrayList<>();
    omsOrderItemList.add(omsOrderItem);
    OmsOrder omsOrder = OmsOrder.builder().id(IdUtil.getSnowflakeNextId()).orderItemList(omsOrderItemList).build();
    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Map<String,Object> map=new HashMap<>();
    map.put("messageId",messageId);
    map.put("messageData",omsOrder);
    map.put("createTime",createTime);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(omsOrder);
    System.out.println(json);

    //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
    rabbitTemplate.convertAndSend("DirectExchange", "DirectRouting", json);
    return CommonResult.success("库存消息发送成功");
  }

  /**
   * 这里的发送是 拟投递到多个队列中
   *
   * @param id  业务id
   * @param msg 业务信息
   */
  public void syncSend(int id, String msg) throws JsonProcessingException {
    MessageConfig messageContent = new MessageConfig(id, msg);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(messageContent);

    MessageProperties messageProperties = new MessageProperties();
    //消息持久化
    messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

    messageProperties.setContentType("application/json");
    messageProperties.getHeaders().put("__TypeId__","MessageConfig");
    Message message = new Message(json.getBytes(),messageProperties);

    rabbitTemplate.convertAndSend(MessageConfig.EXCHANGE, this.getRoutingKey(id), message);
  }

  /**
   * 根据 id 取余来决定丢到那个队列中去
   *
   * @param id id
   * @return routingKey
   */
  private String getRoutingKey(int id) {
    return "DirectRouting_" + String.valueOf(id % MessageConfig.QUEUE_COUNT);
  }
}
