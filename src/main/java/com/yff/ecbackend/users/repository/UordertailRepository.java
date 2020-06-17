package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uordertail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UordertailRepository extends BaseRepository<Uordertail, Long> {

    @Query("select a from Uordertail a where a.orderid=:orderid ORDER BY a.id ")
    public abstract List<Uordertail> findByUordertail(@Param("orderid") Long orderid);

    /**
     * 查询订单明细不包含套餐明细
     * @param orderid
     * @return
     */
    @Query("select a from Uordertail a where a.orderid=:orderid  and pid is null")
    public abstract List<Uordertail> findByUordertailNotTc(@Param("orderid") Long orderid);

    @Modifying
    @Query(" delete from Uordertail a where a.orderid=:orderid")
    public abstract void clearUordertail(@Param("orderid") Long orderid);

    /**
     * 查询套餐
     * @param orderid
     * @param pid
     * @return
     */
    @Query(value = "SELECT a.productid   id , count(a.productid)    num,  a.name ,sum( b.price)  tprice,sum(b.memberprice) memberprice  FROM u_orderta a ,b_product b  where a.productid=b.id  and a.pid=:pid GROUP BY a.productid, a.name" ,nativeQuery = true)
    public abstract List<Map<String,Object>> findByTc(@Param("pid") Long pid);

}
