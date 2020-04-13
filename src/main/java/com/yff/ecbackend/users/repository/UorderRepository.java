package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uorder;
import org.springframework.stereotype.Repository;

@Repository
public interface UorderRepository extends BaseRepository<Uorder,Long> {


}
