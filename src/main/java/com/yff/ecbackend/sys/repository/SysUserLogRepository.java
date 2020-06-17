package com.yff.ecbackend.sys.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.sys.entity.SysUserLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SysUserLogRepository extends BaseRepository<SysUserLog, Long> {

    @Query(value = "SELECT  COUNT(id)  clickNumber , COUNT(DISTINCT  username)  clickPersonen  FROM sys_userlog  where   DATE_FORMAT(buildtime  ,'%Y-%m-%d') =  DATE_FORMAT(NOW()  ,'%Y-%m-%d') ", nativeQuery = true)
    public abstract Map<String, Object> samedayClick();

}
