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
     * 原生sql查询分页返回Uorder实体
     * @param openid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Query(value = "select * from u_order where openid=:openid ORDER BY  buildtime DESC limit :pageNum,:pageSize ",nativeQuery = true)
    public abstract List<Uorder> findUserOrderpage(@Param("openid") String openid,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);


    /**
     * 通过分店id查询商家订单未完成统计
     * @param branchid
     * @return
     */
    @Query(" SELECT count(o) FROM Uorder o where o.branchid=:branchid and o.iscomplete=0")
    public abstract int findByIsNotOrderComplete(@Param("branchid") Long branchid);

    /**
     * 根据商家分店id查询订单 以时间倒序查询
     * @param branchid
     * @return
     */
    @Query(" SELECT o FROM Uorder o where o.branchid=:branchid ORDER BY o.buildtime desc")
    public abstract List<Uorder> findByBranchOrder(@Param("branchid") Long branchid);

}
