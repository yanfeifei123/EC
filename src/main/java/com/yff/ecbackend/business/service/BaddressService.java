package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Baddress;
import com.yff.ecbackend.business.repository.BaddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaddressService extends BaseService<Baddress, Long> {

    @Autowired
    private BaddressRepository baddressRepository;

}
