package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_branch")
@org.hibernate.annotations.Table(appliesTo = "b_branch",comment = "商家分店")
public class Bbranch extends BaseEntity<Long> {

    @Column(columnDefinition = "varchar(255) comment '分店名称'")
    private String name;


    @Column(columnDefinition = "varchar(255) comment '区域（云南昆明五华区）'")
    private String area;

    @Column(columnDefinition = "varchar(255) comment 'xx街道门牌号'")
    private String detailed;


    @Column(columnDefinition = "int(255) comment '绑定商家信息外键'")
    private Long buid;

    @Column(columnDefinition = "float comment '商家经度'")
    private float longitude;

    @Column(columnDefinition = "float comment '商家纬度'")
    private float latitude;

    @Column(columnDefinition = "decimal default 0 comment '配送费'")
    private float psfcost;

    @Column(columnDefinition = "decimal default 0 comment '首单用户-x元'")
    private float firstorder;

    @Column(columnDefinition = "decimal default 0 comment '多少钱起送'")
    private float firstmoney;

    @Column(columnDefinition = "int(11) default 0 comment '分店是否营业(0后，1是)'")
    private int bornot;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    public Long getBuid() {
        return buid;
    }

    public void setBuid(Long buid) {
        this.buid = buid;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getPsfcost() {
        return psfcost;
    }

    public void setPsfcost(float psfcost) {
        this.psfcost = psfcost;
    }

    public float getFirstorder() {
        return firstorder;
    }

    public void setFirstorder(float firstorder) {
        this.firstorder = firstorder;
    }

    public float getFirstmoney() {
        return firstmoney;
    }

    public void setFirstmoney(float firstmoney) {
        this.firstmoney = firstmoney;
    }


    public int getBornot() {
        return bornot;
    }

    public void setBornot(int bornot) {
        this.bornot = bornot;
    }
}
