package com.yff.core.jparepository.entity;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;


@MappedSuperclass
public class BaseEntity<ID extends Serializable> extends AbstractEntity <ID> implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private ID id;


    @Column(columnDefinition = "int default 0 comment '排序字段'")
    private int odr;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '创建时间'")
    private Date buildtime;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    /**
     * 默认排序
     */

    public int getOdr() {
        return odr;
    }

    /**
     * 默认排序
     */
    public void setOdr(int odr) {
        this.odr = odr;
    }

    /**
     * 创建时间
     */
    public Date getBuildtime() {
        return buildtime;
    }

    /**
     * 创建时间
     */
    public void setBuildtime(Date buildtime) {
        this.buildtime = buildtime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
