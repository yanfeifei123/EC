package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bcategorys;
import com.yff.ecbackend.business.repository.BcategorysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BcategorysService extends BaseService<Bcategorys, Long> {
    @Autowired
    private BcategorysRepository bcategorysRepository;

}
