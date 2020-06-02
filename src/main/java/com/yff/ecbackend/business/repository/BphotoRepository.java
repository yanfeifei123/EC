package com.yff.ecbackend.business.repository;

import com.yff.core.jparepository.repository.BaseRepository;
import com.yff.ecbackend.business.entity.Bphoto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BphotoRepository extends BaseRepository<Bphoto,Long> {

    @Query("select p from Bphoto p where p.fkid=:fkid")
    public abstract Bphoto findByfkidBphoto(@Param("fkid")Long fkid);



}
