package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Business;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends BaseRepository<Business,Long> {

}
