package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uorder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UorderRepository extends BaseRepository<Uorder,Long> {


    /**
     * 查询用户订单
     * @param openid
     * @return
     */
    @Query("select o from Uorder o where o.openid=:openid ")
    public abstract List<Uorder> findUserOrder(@Param("openid") String openid);


}
