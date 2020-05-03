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
    @Query("select o from Uorder o where o.openid=:openid ORDER BY o.buildtime DESC")
    public abstract List<Uorder> findUserOrder(@Param("openid") String openid);

    /**
     * 通过分店id查询商家订单未完成统计
     * @param branchid
     * @return
     */
    @Query(" SELECT count(o) FROM Uorder o where o.branchid=2 and o.iscomplete=0")
    public abstract int findByIsNotOrderComplete(@Param("branchid") Long branchid);


}
