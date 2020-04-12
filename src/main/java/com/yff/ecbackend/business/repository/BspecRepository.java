package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bspec;
import org.springframework.stereotype.Repository;

@Repository
public interface BspecRepository extends BaseRepository<Bspec,Long> {
}
