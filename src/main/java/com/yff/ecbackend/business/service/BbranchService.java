package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bbranch;
import com.yff.ecbackend.business.repository.BbranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbranchService extends BaseService<Bbranch, Long> {

    @Autowired
    private BbranchRepository bbranchRepository;

}
