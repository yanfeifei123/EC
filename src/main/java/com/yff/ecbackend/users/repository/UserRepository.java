package com.yff.ecbackend.users.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.*;
@Repository
public interface UserRepository extends BaseRepository<User,Long> {


    @Query("select u from  User  u where u.openid=:openid ")
    public abstract User findByOnenid(@Param("openid") String openid);


    @Query(value = "select md5(CONCAT(:password,:openid))" ,nativeQuery = true)
    public abstract String findByPassword(@Param("password") String password,@Param("openid") String openid);

    @Query("select u from User u where u.account=:account" )
    public abstract User findByAccount(@Param("account") String account);

    /**
     * 通过分店id查询管理者
     * @param branchid
     * @return
     */
    @Query("select u from User u where u.branchid=:branchid" )
    public abstract List<User> findByBranchid(@Param("branchid") Long branchid);

    /**
     * 通过分店id查询管理者 名称搜索
     * @param branchid
     * @return
     */
    @Query("select u from User u where u.branchid=:branchid and u.openid <> 'oM_GB4rjG6k2dKlqXxrBb_0fdAWI' and u.nickname like %:name% " )
    public abstract List<User>  findBybranchUser(@Param("branchid") Long branchid,@Param("name") String name);

    @Query( value = "select * from user u where u.branchid is NULL and u.openid <> 'oM_GB4rjG6k2dKlqXxrBb_0fdAWI' and u.nickname like %:name% limit :pageNum,:pageSize",nativeQuery = true)
    public abstract List<User> findByUsers(@Param("name") String name ,@Param("pageNum") int pageNum,@Param("pageSize") int pageSize);


    @Query( value = "select count(*) from user u where u.branchid is NULL and u.openid <> 'oM_GB4rjG6k2dKlqXxrBb_0fdAWI' and u.nickname like %:name% ",nativeQuery = true)
    public abstract int usertotalPage(@Param("name") String name);




}
