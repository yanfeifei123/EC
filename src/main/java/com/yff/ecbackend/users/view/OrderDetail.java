package com.yff.ecbackend.users.view;

import com.alibaba.fastjson.annotation.JSONField;
import com.yff.ecbackend.users.entity.Uaddress;
import com.yff.ecbackend.users.entity.Uordertail;
import lombok.Data;

import java.util.*;

/**
 * 订单详情
 */
@Data
public class OrderDetail {
    private Long orderid; //订单id
    private String openid; //微信用户 id
    private Integer iscomplete; //是否完成 0,1
    private String hour; //预计送达时间小时
    private String branchname; //商家分店名称
    private Long branchid; //分店id

    private List<OrderItem> orderItems = new ArrayList<>(); //包含明细统计（不包含套餐）

    private Uaddress uaddress;  //配送地址
    private String exptimeinf="由商家自行配送";
    private String exptime="立即配送"; //期望时间
    private String disservice="商家自行配送";//配送服务
    private String orderno; //订单号码

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date ordertime;//下单时间

    private String paym; //支付方式
    private Float deliveryfee; //配送费
    private Float firstorder;// 首单用户
    private int ismember=0; //是否是会员购买

    private Float discount;
    private Float totalfee ;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date completetime;

    private int self;

}
