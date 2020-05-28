package com.yff.ecbackend.sys.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.sys.entity.SysUserLog;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserLogRepository extends BaseRepository<SysUserLog,Long> {
}
