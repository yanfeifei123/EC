package com.yff.ecbackend.business.service;

import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.yff.ecbackend.business.repository.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class BusinessService extends BaseService<Business, Long> {
    @Autowired
    private BusinessRepository businessRepository;




    public Object getgoods(String businessid) {
        try {
            ClassPathResource resource = new ClassPathResource("/static/commoditys.json");
            InputStream in = resource.getInputStream();

            String result = new BufferedReader(new InputStreamReader(in,"UTF-8")).lines().collect(Collectors.joining(System.lineSeparator()));

            return JSON.parseArray(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





}

