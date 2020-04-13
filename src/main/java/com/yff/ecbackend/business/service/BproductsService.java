package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bproducts;
import com.yff.ecbackend.business.repository.BproductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BproductsService extends BaseService<Bproducts, Long> {
    @Autowired
    private BproductsRepository bcategorysRepository;

}
