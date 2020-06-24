package com.yff.ecbackend.users.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uaddress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface UaddressRepository extends BaseRepository<Uaddress,Long> {

    /**
     * 查询用户地址
     * @param userid
     * @return
     */
    @Query("select a from Uaddress a where a.userid=:userid order by a.buildtime desc")
    public abstract List<Uaddress> findByUaddress(@Param("userid") Long userid);





}
