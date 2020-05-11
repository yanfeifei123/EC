package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bbluetooth;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BbluetoothRepository extends BaseRepository<Bbluetooth,Long> {

    @Query("select b from  Bbluetooth b where b.branchid=:branchid")
    public abstract Bbluetooth findByBbluetooth(@Param("branchid") Long branchid);

}
