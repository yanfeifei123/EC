package com.yff.ecbackend.messagequeue.config;


import com.alibaba.fastjson.JSON;
import lombok.Data;

/*
 * 订单消息模板 针对商家后台处理
 */
@Data
public class MessageTemplate {

    private Long orderid;//订单id
    private Long branchid;//商家分店id
    private String openid; //下单微信用户id
    private String type;  //消息类型order,updateAddress,refundOrder

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
