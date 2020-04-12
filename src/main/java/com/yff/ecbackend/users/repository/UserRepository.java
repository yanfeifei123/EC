package com.yff.ecbackend.users.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User,Long> {


    @Query("select u from  User  u where u.openid=:openid ")
    public abstract User findByOnenid(@Param("openid") String openid);


    @Query(value = "select md5(CONCAT(:password,:openid))" ,nativeQuery = true)
    public abstract String findByPassword(@Param("password") String password,@Param("openid") String openid);

    @Query("select u from User u where u.account=:account" )
    public abstract User findByAccount(@Param("account") String account);

}
