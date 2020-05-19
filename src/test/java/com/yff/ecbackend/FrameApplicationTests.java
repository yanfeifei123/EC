package com.yff.ecbackend;


import com.yff.ecbackend.business.entity.Business;
import com.yff.ecbackend.business.service.BusinessService;
import com.yff.ecbackend.users.service.UorderOdrService;
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
    private UorderOdrService uorderOdrService;

    @Test
    public void contextLoads() {
        Object o= uorderOdrService.findByUorderOdr(Long.valueOf(1));
        System.out.println("o:"+o);
    }


}
