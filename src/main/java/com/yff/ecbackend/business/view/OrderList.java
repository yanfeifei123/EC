package com.yff.ecbackend.business.view;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class OrderList {
    private Long orderid; //订单id
    private String productNames; //商品名称

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private Date ordertime; //下单时间

    private String state;//状态（已完成/未完成）

    private String type; //类型（到店自取/外卖配送）

    private float totalfee;//订单总金额

    private String avatarurl;

    private String username;//收货人（下单人姓名）

    private Long uaddressid;
    private int iscomplete;

    private String tradeno;



}
