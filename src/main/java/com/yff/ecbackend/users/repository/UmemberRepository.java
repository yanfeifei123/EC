package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Umember;
import org.springframework.stereotype.Repository;

@Repository
public interface UmemberRepository extends BaseRepository<Umember,Long> {


}
