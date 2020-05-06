package com.yff.core.jparepository.service;

import com.yff.core.jparepository.entity.*;
import com.yff.core.jparepository.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.*;

@Order(2)
public abstract class BaseService<T extends AbstractEntity, ID extends Serializable> {

    @Autowired
    protected BaseRepository<T, ID> baseRepository;

    public T update(T m) {
        T t = this.baseRepository.save(m);
        return t;
    }


    public List<T> update(List<T> data) {
        try {
            List<T> list = this.baseRepository.saveAll(data);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean delete(T m) {
        try {
            this.baseRepository.delete(m);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(ID id) {
        try {
            this.baseRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(List<T> data) {
        try {
            this.baseRepository.deleteAll(data);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public T findOne(ID id) {
        return this.baseRepository.findOne(id);
    }

    public List<T> findAll() {

        return this.baseRepository.findAll(Sort.by(Sort.Direction.ASC, "odr"));
    }

    /**
     * 分页查询
     * @param pageNum  第几页
     * @param pageSize 一页显示多少行
     * @param sortType 排序类型
     * @param field  排序字段
     * @return
     */
    public List<T> findAll(int pageNum,int pageSize,String sortType,String field){
        Sort sort =Sort.by(Sort.Direction.valueOf(sortType.toUpperCase()),  field);
        Pageable pageable = PageRequest.of(pageNum,pageSize,sort);
        Page<T>  page = this.baseRepository.findAll(pageable);
        return page.getContent();
    }


    public List<T> findAll(Sort var1) {
        return baseRepository.findAll(var1);
    }


}
