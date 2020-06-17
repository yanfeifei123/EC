package com.yff.ecbackend.business.service;


import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Utoken;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UtokenService extends BaseService<Utoken, Long> {

    public void handle(String accessToken, String expiresin) {
        List<Utoken> utokens = this.findAll();
        Utoken utoken = null;
        if (utokens.size() != 0) {
            utoken = utokens.get(0);
        } else {
            utoken = new Utoken();
        }
        utoken.setBuildtime(new Date());
        utoken.setToken(accessToken);
        utoken.setExpiresin(Integer.parseInt(expiresin));
        this.update(utoken);
    }

    public Utoken getUtoken() {
        return this.findAll().size() != 0 ? this.findAll().get(0) : null;
    }


}
