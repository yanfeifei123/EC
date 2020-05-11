package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bbluetooth;
import com.yff.ecbackend.business.repository.BbluetoothRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BbluetoothService extends BaseService<Bbluetooth,Long> {

    @Autowired
    private BbluetoothRepository bbluetoothRepository;


    public Bbluetooth updatebbluetooth(Bbluetooth bbluetooth){
        if(bbluetooth.isNew()){
            bbluetooth.setBuildtime(new Date());
        }
        return this.update(bbluetooth);
    }

    public Bbluetooth findByBbluetooth(String branchid){
        return this.bbluetoothRepository.findByBbluetooth(Long.valueOf(branchid));
    }



}
