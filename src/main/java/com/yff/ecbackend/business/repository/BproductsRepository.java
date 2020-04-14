package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bproducts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface BproductsRepository extends BaseRepository<Bproducts,Long> {

    /**
     * 通过类别查询商品
     * @param categoryid
     * @return
     */
    @Query("select  b from  Bproducts b where b.categoryid=:categoryid and b.pid is null order by b.odr")
    public abstract List<Bproducts> findBproducts(@Param("categoryid")Long categoryid);
}
