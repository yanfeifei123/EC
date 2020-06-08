package com.yff.ecbackend.users.service;


import com.yff.core.jparepository.service.BaseService;
import com.yff.core.util.ToolUtil;
import com.yff.ecbackend.users.entity.Uorder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * 订单序号算法
 */

@Service
public class UorderOdrService extends BaseService<Uorder, Long> {

    @PersistenceContext(unitName = "entityManagerFactoryPrimary")
    private EntityManager entityManager;

    /**
     * 订单序号
     *
     * @return
     */
    public int findByUorderOdr(Long branchid) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("SELECT MAX(odr) FROM u_order  where  DATE_FORMAT(buildtime,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d') and branchid=:branchid and (`status`=1 OR `status`=2)");
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("branchid",branchid);
        Object o = query.getSingleResult();
        int odr = 1;
        if(ToolUtil.isNotEmpty(o)){
            odr = Integer.parseInt(o.toString())+1;
        }
        return odr;
    }

}
