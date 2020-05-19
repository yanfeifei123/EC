package com.yff.ecbackend;


import com.yff.core.util.DateUtil;
import com.yff.wechat.wxpaysdk.WXPayUtil;
import org.springframework.util.unit.DataUnit;

import java.util.Date;

public class Main {



    public static void main(String[] args)    {
         String now = DateUtil.formatDate(new Date(),"HH:mm:ss");
         System.out.println(now);
    }
}
