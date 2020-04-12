package com.yff.core.jparepository.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Persistable;

public abstract class AbstractEntity <ID extends Serializable> implements Persistable<ID>{
    /**
     * 是否新对象
     */
    @Override
    public boolean isNew() {
        if (this.getId() == null) {
            return true;
        }
        return false;
    }

    @Override
    public abstract ID getId();

    public abstract void setId(ID paramID);


    public List  getChildren() {
        return new ArrayList();
    }


    public Object  getParent() {
        return new Object ();
    }


}
