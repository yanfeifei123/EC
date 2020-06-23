package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bbluetooth;
import com.yff.ecbackend.business.entity.Brecharge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BrechargeRepository extends BaseRepository<Brecharge,Long> {

    @Query("select b from  Brecharge b where b.branchid=:branchid ORDER BY b.money")
    public abstract List<Brecharge> findBybrecharge(@Param("branchid") Long branchid);


}
