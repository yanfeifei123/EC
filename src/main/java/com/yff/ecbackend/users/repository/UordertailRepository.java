package com.yff.ecbackend.users.repository;


import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.users.entity.Uordertail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UordertailRepository extends BaseRepository<Uordertail, Long> {

    @Query("select a from Uordertail a where a.orderid=:orderid ORDER BY a.id ")
    public abstract List<Uordertail> findByUordertail(@Param("orderid") Long orderid);

}
