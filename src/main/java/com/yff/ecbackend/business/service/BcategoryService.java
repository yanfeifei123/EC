package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bcategory;
import com.yff.ecbackend.business.repository.BcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BcategoryService extends BaseService<Bcategory, Long> {

    @Autowired
    private BcategoryRepository bcategoryRepository;
}
