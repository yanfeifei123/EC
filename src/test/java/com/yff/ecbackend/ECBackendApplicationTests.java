package com.yff.ecbackend;


import com.yff.ecbackend.business.entity.Bproduct;
import com.yff.ecbackend.business.entity.Business;
import com.yff.ecbackend.business.service.BproductService;
import com.yff.ecbackend.business.service.BusinessService;
import com.yff.ecbackend.users.service.UorderOdrService;
import org.hibernate.annotations.LazyToOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  //添加tomcat依赖
@RunWith(SpringRunner.class)  //添加注解类依赖
public class ECBackendApplicationTests {

    @Autowired
    private BproductService bproductService;

    @Test
    public void contextLoads() {
//        System.out.println(bproductService);
        Long br= Long.valueOf(1);
        List<Bproduct> bproducts= bproductService.findByBproductFuzzyquery(br,"煎饼");
        System.out.println("bproducts:"+bproducts.size());
    }


}
