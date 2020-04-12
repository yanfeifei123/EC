package com.yff.ecbackend.business.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Baddress;
import org.springframework.stereotype.Repository;

@Repository
public interface BaddressRepository extends BaseRepository<Baddress,Long> {


}
