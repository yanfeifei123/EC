package com.yff.ecbackend.messagequeue.pojo;


import lombok.Data;

/*
 * 订单消息模板 针对商家后台处理
 */
@Data
public class OrderMessageTemplate {

    private Long orderid;//订单id
    private Long branchid;//商家分店id
    private String openid; //下单微信用户id

}
