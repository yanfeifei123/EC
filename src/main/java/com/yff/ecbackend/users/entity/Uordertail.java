package com.yff.ecbackend.users.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "u_ordertail")
@org.hibernate.annotations.Table(appliesTo = "u_ordertail",comment = "用户订单明细表")
public class Uordertail extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255) comment '关联订单id'")
    private Long orderid;

    @Column(columnDefinition = "int(255) comment '关联商品id'")
    private Long productid;

    @Column(columnDefinition = "int comment '数量'")
    private Integer number;


    @Column(columnDefinition = "decimal(6,2) comment '单价'")
    private float price;

    @Column(columnDefinition = "decimal(6,2) comment '会员价'")
    private float memberprice;

    @Column(columnDefinition = "decimal(6,2) comment '已优惠'")
    private float prefprice;


    @Column(columnDefinition = "varchar(20) DEFAULT '0' comment '是否是套餐(0否，1是)'")
    private String packages;


    @Column(columnDefinition = "int(255) comment '关联上级id（组合套餐）'")
    private Long pid;


    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(float memberprice) {
        this.memberprice = memberprice;
    }

    public float getPrefprice() {
        return prefprice;
    }

    public void setPrefprice(float prefprice) {
        this.prefprice = prefprice;
    }


    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
