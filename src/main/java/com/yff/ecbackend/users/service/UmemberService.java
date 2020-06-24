package com.yff.ecbackend.users.service;


import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.users.entity.Umember;
import com.yff.ecbackend.users.repository.UmemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class UmemberService   extends BaseService<Umember,Long> {

    @Autowired
    private UmemberRepository umemberRepository;


    public Umember findByUserUmember(Long userid, Long branchid){
        return this.umemberRepository.findByUserUmember(userid,branchid);
    }

}
