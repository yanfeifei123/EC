package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_address")
@org.hibernate.annotations.Table(appliesTo = "b_address",comment = "商家地址")
public class Baddress extends BaseEntity<Long> {

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
}
