package com.yff.ecbackend.business.view;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * 商家订单汇总
 */
@Data
public class OrderSummary {

    private float totalfee; //订单总金额
    private int ordernum;  //订单数量
    private float refundamount; //退款金额
    private int refundformnum; //退款订单数量

}
