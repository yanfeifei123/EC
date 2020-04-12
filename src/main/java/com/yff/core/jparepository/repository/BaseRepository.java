package com.yff.core.jparepository.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.*;

@NoRepositoryBean
public  interface   BaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID>{

    public abstract M findOne(ID id);


}
