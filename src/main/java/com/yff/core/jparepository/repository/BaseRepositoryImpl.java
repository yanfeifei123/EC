package com.yff.core.jparepository.repository;


import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import javax.persistence.EntityManager;
import java.io.Serializable;


public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository <T, ID> implements BaseRepository <T, ID> {

    private  EntityManager entityManager;
    private  Class<T> entityClass;

    /**
     * 老版本写法
     * @param domainClass
     * @param em
     */
//    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
//        super(entityInformation, entityManager);
//        this.entityManager = entityManager;
//    }

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
        entityClass =domainClass;
    }

    @Override
    public T findOne(ID id) {

        return this.entityManager.find(entityClass,id);
    }








}
