package com.yff.ecbackend.users.view;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yff.ecbackend.common.json.Object6Serialize;
import lombok.Data;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 订单统计商品
 */

@Data
public class OrderItem {

    private Long productid;
    private String name;
    private Integer number;

    private Float price;

    private Float memberprice;

    private Long orderid;
    private Integer ismeal;
    private String imagepath;
    /*
      套餐统计
     */
    private List<OrderItem> orderItems =new ArrayList<>();


    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Float getPrice() {

        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getMemberprice() {
        return  memberprice;
    }

    public void setMemberprice(Float memberprice) {
        this.memberprice = memberprice;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Integer getIsmeal() {
        return ismeal;
    }

    public void setIsmeal(Integer ismeal) {
        this.ismeal = ismeal;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
