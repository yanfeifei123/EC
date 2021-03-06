package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uorder;
import org.springframework.data.jpa.repository.Modifying;
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
     * 查询首单用户
     * @param openid
     * @return
     */
    @Query("select o from Uorder o where o.openid=:openid and o.status=1 ORDER BY o.buildtime DESC")
    public abstract List<Uorder> findUserIsfirstorder(@Param("openid") String openid);

    /**
     * 查询未支付的订单
     * @param openid
     * @return
     */
    @Query("select o from Uorder o where o.openid=:openid and o.status=0 ORDER BY o.buildtime DESC")
    public abstract List<Uorder> findUserUnpaidorder(@Param("openid") String openid);

    /**
     * 通过分店id查询商家订单未完成统计
     * @param branchid
     * @return
     */
    @Query(" SELECT count(o) FROM Uorder o where o.branchid=:branchid and o.iscomplete=0 and o.status=1")
    public abstract int findByIsNotOrderComplete(@Param("branchid") Long branchid);

    /**
     * 通过商户号查询订单
     * @param tradeno
     * @return
     */
    @Query("SELECT o FROM Uorder o where o.tradeno=:tradeno")
    public abstract Uorder findTradenoUorder(@Param("tradeno") String tradeno);


    @Query(value = " SELECT * FROM u_order o where o.branchid=:branchid and (o.status=1 or o.status=2)  ORDER BY o.buildtime desc,odr limit :pageNum,:pageSize",nativeQuery = true)
    public abstract List<Uorder> findByBranchOrder(@Param("branchid") Long branchid,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);


    /**
     * 根据商家分店id查询订单 以时间倒序查询
     * @param branchid
     * @return
     */
    @Query(value = " SELECT * FROM u_order o where o.branchid=:branchid and o.iscomplete=:iscomplete and o.status=1 and o.id not in (SELECT orderid FROM  u_orderr where end=0 ) ORDER BY o.buildtime desc ,odr limit :pageNum,:pageSize" ,nativeQuery = true)
    public abstract List<Uorder> findByBranchOrder(@Param("branchid") Long branchid,@Param("iscomplete") int iscomplete,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);


    @Query(value = " SELECT count(*) FROM u_order o where o.branchid=:branchid and o.iscomplete=:iscomplete  and o.status=1 and o.id not in (SELECT orderid FROM  u_orderr where end=0 ) " ,nativeQuery = true)
    public abstract int countAllByBranchOrder(@Param("branchid") Long branchid,@Param("iscomplete") int iscomplete);


    @Query(value = " SELECT count(*) FROM u_order o where o.branchid=:branchid and (o.status=1 or o.status=2)"  ,nativeQuery = true)
    public abstract int countAllByBranchOrder(@Param("branchid") Long branchid);

    /**
     * 查询待退款
     * @param branchid
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Query(value = " SELECT o.* FROM u_order o, u_orderr r  where  o.id=r.orderid and o.branchid=:branchid    ORDER BY o.buildtime desc limit :pageNum,:pageSize" ,nativeQuery = true)
    public abstract List<Uorder> findByBranchRefundOrder(@Param("branchid") Long branchid,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);

    /**
     * 查询待退款统计
     * @param branchid
     * @return
     */
    @Query(value = "SELECT count(*) FROM u_order o, u_orderr r  where  o.id=r.orderid and o.branchid=:branchid  ",nativeQuery = true)
    public abstract int countAllByBranchRefundOrder(@Param("branchid") Long branchid);


    /**
     * 查询待退款信息
     * @param branchid
     * @return
     */
    @Query(value="SELECT count(*) FROM u_order o, u_orderr r  where  o.id=r.orderid and o.branchid=:branchid  and r.end=0   and o.status=1" ,nativeQuery = true)
    public abstract int countAllByBranchStayRefundOrder(@Param("branchid") Long branchid);


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
     * 统计用户订单
     * @param openid
     * @return
     */
    @Query(value = "select count(*) from  u_order where openid=:openid ",nativeQuery = true )
    public abstract int countAllByUorderOAndOpenid(@Param("openid") String openid);

    @Modifying
    @Query(value = " DELETE FROM u_order where openid=:openid and status=0 ", nativeQuery=true)
    public abstract void clearMyorder(@Param("openid") String openid);


}
