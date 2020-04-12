package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bcategory;
import org.springframework.stereotype.Repository;

@Repository
public interface BcategoryRepository extends BaseRepository<Bcategory,Long> {



}
