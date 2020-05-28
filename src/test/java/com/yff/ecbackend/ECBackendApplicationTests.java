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



@SpringBootTest
public class ECBackendApplicationTests {

    @Autowired
    private UorderOdrService uorderOdrService;

    @Test
    public void contextLoads() {
        System.out.println("ECBackendApplicationTests:");
    }


}
