package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Utoken;
import org.springframework.stereotype.Repository;

@Repository
public interface UtokenRepository  extends BaseRepository<Utoken,Long> {


}
