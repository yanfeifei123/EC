package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Umemberrd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UmemberrdRepository extends BaseRepository<Umemberrd,Long> {


    @Query("select u from  Umemberrd u where u.tradeno=:tradeno")
    public abstract Umemberrd findByUmemberrd(@Param("tradeno") String tradeno);

    /**
     * 我的充值记录
     * @param userid
     * @return
     */
    @Query("select u from  Umemberrd u where u.userid=:userid order by u.buildtime desc")
    public abstract List<Umemberrd> findByUserUmemberrd(@Param("userid") Long userid);


    /**
     * 商家充值记录
     * @param branchid
     * @return
     */
    @Query("select u from  Umemberrd u where u.branchid=:branchid order by u.buildtime desc")
    public abstract List<Umemberrd> findByBranchidUmemberrd(@Param("branchid") Long branchid);





}
