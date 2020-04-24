package com.yff.ecbackend;


import com.yff.core.safetysupport.aes.Aes;
import com.yff.core.util.ToolUtil;

import java.util.Random;

public class Main {

    public static void main(String[] args)    {

        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        long millis = System.currentTimeMillis();
        System.out.println(millis);
        sb.append(millis);
        for (int i = 1; i <= 19; i++) {
            int randNum = rand.nextInt(9) + 1;
            String num = randNum + "";
            sb = sb.append(num);
        }

        System.out.println(sb.toString());


    }
}
