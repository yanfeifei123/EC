package com.yff.ecbackend.users.service;


import com.alibaba.fastjson.JSON;
import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.users.entity.Umember;
import com.yff.ecbackend.users.repository.UmemberRepository;
import com.yff.ecbackend.users.view.OrderItem;
import com.yff.ecbackend.users.view.Usermember;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class UmemberService extends BaseService<Umember, Long> {


    @PersistenceContext(unitName = "entityManagerFactoryPrimary")
    private EntityManager entityManager;

    @Autowired
    private UmemberRepository umemberRepository;


    @Autowired
    private UmemberrdService umemberrdService;

    @Autowired
    private UserService userService;


    public Umember findByUserUmember(Long userid, Long branchid) {
        return this.umemberRepository.findByUserUmember(userid, branchid);
    }

    /**
     * 查询商家会员
     * @param branchid
     * @return
     */
    public List<Usermember> findByUserbranchidUmember(Long branchid) {
        StringBuilder dataSql = new StringBuilder();
        dataSql.append("SELECT a.id,a.nickname,a.avatarurl,b.money,b.branchid,(");
        dataSql.append(" SELECT buildtime FROM u_memberrd where userid=b.userid ORDER BY buildtime DESC LIMIT 0,1 ");
        dataSql.append(") date  FROM `user` a ,u_member b where a.id=b.userid and b.branchid=:branchid ORDER BY b.money");
        Query query = this.entityManager.createNativeQuery(dataSql.toString());
        query.setParameter("branchid", branchid);
        List<Map<String, Object>> list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        List<Usermember> usermemberList = JSON.parseArray(JSON.toJSONString(list), Usermember.class);
        return usermemberList;
    }

}
