package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Umember;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UmemberRepository extends BaseRepository<Umember,Long> {

    /**
     * 查询会员金额
     * @param userid
     * @param branchid
     * @return
     */
    @Query("select u from Umember u where u.userid=:userid and u.branchid=:branchid")
    public abstract Umember findByUserUmember(@Param("userid")Long userid,@Param("branchid")Long branchid);

    /**
     * 查询该商家会员
     * @param branchid
     * @return
     */
    @Query("select u from Umember u where u.branchid=:branchid")
    public abstract List<Umember> findByUserbranchidUmember(@Param("branchid")Long branchid);

}
