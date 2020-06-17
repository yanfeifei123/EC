package com.yff.ecbackend.sys.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.sys.entity.SysUserLog;
import com.yff.ecbackend.sys.repository.SysUserLogRepository;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Service
public class SysUserLogService extends BaseService<SysUserLog,Long> {


    @Autowired
    private SysUserLogRepository sysUserLogRepository;

    public Map<String, Object> samedayClick(){
         return this.sysUserLogRepository.samedayClick();
    }

}
