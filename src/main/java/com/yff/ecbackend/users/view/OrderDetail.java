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


    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getIscomplete() {
        return iscomplete;
    }

    public void setIscomplete(Integer iscomplete) {
        this.iscomplete = iscomplete;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public Long getBranchid() {
        return branchid;
    }

    public void setBranchid(Long branchid) {
        this.branchid = branchid;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Uaddress getUaddress() {
        return uaddress;
    }

    public void setUaddress(Uaddress uaddress) {
        this.uaddress = uaddress;
    }

    public String getExptimeinf() {
        return exptimeinf;
    }

    public void setExptimeinf(String exptimeinf) {
        this.exptimeinf = exptimeinf;
    }

    public String getExptime() {
        return exptime;
    }

    public void setExptime(String exptime) {
        this.exptime = exptime;
    }

    public String getDisservice() {
        return disservice;
    }

    public void setDisservice(String disservice) {
        this.disservice = disservice;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public String getPaym() {
        return paym;
    }

    public void setPaym(String paym) {
        this.paym = paym;
    }

    public Float getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(Float deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public Float getFirstorder() {
        return firstorder;
    }

    public void setFirstorder(Float firstorder) {
        this.firstorder = firstorder;
    }

    public int getIsmember() {
        return ismember;
    }

    public void setIsmember(int ismember) {
        this.ismember = ismember;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(Float totalfee) {
        this.totalfee = totalfee;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }
}
