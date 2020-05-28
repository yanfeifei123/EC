package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bproduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface BproductsRepository extends BaseRepository<Bproduct,Long> {

    /**
     * 通过类别查询商品
     * @param categoryid
     * @return
     */
    @Query("select  b from  Bproduct b where b.categoryid=:categoryid and b.pid is null order by b.odr")
    public abstract List<Bproduct> findBproducts(@Param("categoryid")Long categoryid);

    @Query("select  b from  Bproduct b where b.categoryid=:categoryid   order by b.odr")
    public abstract List<Bproduct> findinSetmealBproducts(@Param("categoryid")Long categoryid);


    @Query("select b from Bproduct b where b.pid=:pid")
    public abstract List<Bproduct> findByproductPackage(@Param("pid")Long pid);

    @Query("select b from Bproduct b where b.branchid=:branchid order by b.odr")
    public abstract List<Bproduct> findByBproductToBbranch(@Param("branchid") Long branchid);

    /**
     * 统计商品的月销售
     * @param productid
     * @return
     */
    @Query(value = "SELECT count(*) msales from u_orderta where productid=:productid AND DATE_FORMAT(buildtime,'%Y-%m')   = DATE_FORMAT(NOW(),'%Y-%m')" ,nativeQuery = true)
    public abstract int countMsales(@Param("productid")Long productid);


}
