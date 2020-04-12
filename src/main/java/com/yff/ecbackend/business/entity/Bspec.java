package com.yff.ecbackend.business.entity;


import com.yff.core.jparepository.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_spec")
@org.hibernate.annotations.Table(appliesTo = "b_spec",comment = "商品规格")
public class Bspec extends BaseEntity<Long> {


    @Column(columnDefinition = "varchar(255) comment '规格名称'")
    private String name;

    @Column(columnDefinition = "varchar(255) comment '关联商品id'")
    private Long categorysid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategorysid() {
        return categorysid;
    }

    public void setCategorysid(Long categorysid) {
        this.categorysid = categorysid;
    }
}
