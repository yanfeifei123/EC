package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uorderr;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UorderrRepository extends BaseRepository<Uorderr, Long> {

    /**
     * 查询待退款订单
     * @param orderid
     * @return
     */
    @Query("select u from  Uorderr u where u.end=0 and u.orderid=:orderid")
    public abstract Uorderr findUorderrToberefunded(@Param("orderid") Long orderid);


    /**
     * 查询已退款
     * @param orderid
     * @return
     */
    @Query("select u from  Uorderr u where u.end=1 and u.agree=1 and u.orderid=:orderid")
    public abstract Uorderr findUorderrRefunded(@Param("orderid") Long orderid);

    /**
     * 通过订单id查询
     * @param orderid
     * @return
     */
    @Query("select u from  Uorderr u where  u.orderid=:orderid")
    public abstract Uorderr findUorderrOrderid(@Param("orderid") Long orderid);
}
