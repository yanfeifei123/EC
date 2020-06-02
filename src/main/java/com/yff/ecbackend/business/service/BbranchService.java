package com.yff.ecbackend.business.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.repository.BbranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BbranchService extends BaseService<Bbranch, Long> {

    @Autowired
    private BbranchRepository bbranchRepository;


    public Object updateBbranch(String bbranchjson){
        Bbranch bbranch = JSON.parseObject(bbranchjson,Bbranch.class);
        if(bbranch.isNew()){
            bbranch = new Bbranch();
        }
        bbranch.setBuildtime(new Date());
        return  this.update(bbranch);
    }



}
