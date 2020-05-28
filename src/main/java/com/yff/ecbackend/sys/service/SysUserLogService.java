package com.yff.ecbackend.sys.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.sys.entity.SysUserLog;
import com.yff.ecbackend.sys.repository.SysUserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserLogService extends BaseService<SysUserLog,Long> {

    @Autowired
    private SysUserLogRepository sysUserLogRepository;

}
