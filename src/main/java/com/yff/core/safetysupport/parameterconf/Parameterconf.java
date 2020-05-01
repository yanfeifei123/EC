package com.yff.core.safetysupport.parameterconf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
@Data
@ConfigurationProperties(prefix = "parameterconf")
@Component
public class Parameterconf {

    @Value("${server.port}")
    private int serverPort;

    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
    private String appid;
    private String appsecret;
    private String sessionHost;
    private String bnumber;
    private String notify_url;

    private String paykey;

    private String unifiedorderurl;

    private String subscribeMessageurl;

    private String domainname;
}
