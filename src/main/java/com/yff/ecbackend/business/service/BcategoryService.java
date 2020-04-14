package com.yff.ecbackend.business.service;

import com.yff.core.jparepository.service.BaseService;
import com.yff.ecbackend.business.entity.Bcategory;
import com.yff.ecbackend.business.entity.Bproducts;
import com.yff.ecbackend.business.repository.BcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class BcategoryService extends BaseService<Bcategory, Long> {

    @Autowired
    private BcategoryRepository bcategoryRepository;

    @Autowired
    private BproductsService bproductsService;

    public List<Bcategory> findbusinessAll(Long businessid){
        List<Bcategory> bcategoryList = this.bcategoryRepository.findbusinessAll(businessid);
        for(Bcategory bcategory : bcategoryList){
            List<Bproducts>  bproductsitems = bproductsService.findBproducts(bcategory.getId());
            bcategory.setBproductsitems(bproductsitems);
        }
        return bcategoryList;
    }

}
