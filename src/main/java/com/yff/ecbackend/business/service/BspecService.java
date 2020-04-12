package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bspec;
import com.yff.ecbackend.business.repository.BspecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BspecService extends BaseService<Bspec,Long> {

    @Autowired
    private BspecRepository bspecRepository;
}
