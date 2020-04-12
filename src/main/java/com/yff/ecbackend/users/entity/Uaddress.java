package com.yff.ecbackend.users.entity;

import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "u_address")
@org.hibernate.annotations.Table(appliesTo = "u_address",comment = "用户收货地址(多个地址)")
public class Uaddress extends BaseEntity<Long> {

    @Column(columnDefinition = "varchar(255) comment '地区(云南昆明五华区)'")
    private String area;

    @Column(columnDefinition = "varchar(255) comment '几栋几单元门牌号'")
    private String detailed;

    @Column(columnDefinition = "bigint(20) comment '关联用户id'")
    private Long userid;

    @Column(columnDefinition = "varchar(50) comment '收货人手机号码'")
    private String phone;

    @Column(columnDefinition = "varchar(50) comment '收货人姓名'")
    private String name;

    @Column(columnDefinition = "varchar(50) comment '先生/女士'")
    private String gender;


    @Column(columnDefinition = "float comment '用户经度'")
    private float longitude;

    @Column(columnDefinition = "float comment '用户纬度'")
    private float latitude;

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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
}
