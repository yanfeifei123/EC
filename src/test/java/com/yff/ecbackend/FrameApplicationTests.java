package com.yff.ecbackend;


import com.yff.ecbackend.business.entity.Business;
import com.yff.ecbackend.business.service.BusinessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class FrameApplicationTests {

    @Autowired
    private BusinessService businessService;

    @Test
    public void contextLoads() {
        Business business =new Business();
        business.setName("饭团");
        business.setPhone("13888563741");
        business.setWxzfzh("a39234154562");
        business.setBankcard("6217003850004211189");
        business.setNote("备注");
        this.businessService.update(business);



    }


}
