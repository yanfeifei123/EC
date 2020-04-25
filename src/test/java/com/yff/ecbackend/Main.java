package com.yff.ecbackend;


import com.yff.core.safetysupport.aes.Aes;
import com.yff.core.util.DateUtil;
import com.yff.core.util.ToolUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args)    {
        String s="2020-04-25 11:17:33";
        Date time =   DateUtil.parseTime(s) ;
        System.out.println(s);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MINUTE,30);
        Date date = calendar.getTime();
        String e = DateUtil.format(date,"HH:mm");
        System.out.println(e);
    }
}
