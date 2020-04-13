package com.yff.ecbackend.users.service;


import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.users.entity.Uordertail;
import com.yff.ecbackend.users.repository.UordertailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UordertailService extends BaseService<Uordertail,Long> {

    @Autowired
    private UordertailRepository uordertailRepository;

}
