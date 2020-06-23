package com.yff.ecbackend.business.service;


import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Brecharge;
import com.yff.ecbackend.business.repository.BrechargeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class BrechargeService extends BaseService<Brecharge,Long> {

    @Autowired
    private  BrechargeRepository brechargeRepository;


    public Object findBybrecharge(Long branchid){
        return this.brechargeRepository.findBybrecharge(branchid);
    }

    public Object updatabrecharge(Brecharge brecharge){
        if(brecharge.isNew()){
            brecharge.setBuildtime(new Date());
        }
        brecharge=  this.update(brecharge);
        return this.brechargeRepository.findBybrecharge( brecharge.getBranchid());
    }

}
