package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bcategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface BcategoryRepository extends BaseRepository<Bcategory,Long> {


    /**
     * 查询商家所有商品
     * @param businessid
     * @return
     */
    @Query("select c from Bcategory c where c.businessid=:businessid order by c.odr")
    public abstract List<Bcategory> findbusinessAll(@Param("businessid") Long businessid);


}
