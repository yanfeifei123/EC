package com.yff.ecbackend.common.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Testcontroller {



    @PostMapping(value = "/test" ,produces =  MediaType.APPLICATION_JSON_VALUE)
    public String test(@RequestBody(required = false) String requestBody){
        System.out.println("requestBody:"+requestBody);
        return "SUCCESS";
    }



}
