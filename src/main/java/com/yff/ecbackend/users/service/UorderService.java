package com.yff.ecbackend.users.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.users.entity.Uorder;
import com.yff.ecbackend.users.repository.UorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class UorderService  extends BaseService<Uorder,Long> {

    @Autowired
    private UorderRepository uorderRepository;


    public List<Uorder> findUserOrder(String openid){
         return this.uorderRepository.findUserOrder(openid);
    }

}
