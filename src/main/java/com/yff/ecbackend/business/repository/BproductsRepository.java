package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bproducts;
import org.springframework.stereotype.Repository;

@Repository
public interface BproductsRepository extends BaseRepository<Bproducts,Long> {

}
