package com.yff.ecbackend.users.entity;

import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "u_orderta")
@org.hibernate.annotations.Table(appliesTo = "u_orderta",comment = "用户订单明细表")
public class Uordertail extends BaseEntity<Long> {

    @Column(columnDefinition = "int(255) comment '关联订单id'")
    private Long orderid;

    @Column(columnDefinition = "int(255) comment '关联商品id'")
    private Long productid;

    @Column(columnDefinition = "int(255) comment '关联上级id'")
    private Long pid;

    @Column(columnDefinition = "int DEFAULT '0' comment '是否套餐（0否，1是）'")
    private int ismeal;

    @Column(columnDefinition = "decimal(6,2) comment '单价'")
    private float price;

    @Column(columnDefinition = "decimal(6,2) comment '会员价'")
    private float memberprice;


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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public int getIsmeal() {
        return ismeal;
    }

    public void setIsmeal(int ismeal) {
        this.ismeal = ismeal;
    }
}
