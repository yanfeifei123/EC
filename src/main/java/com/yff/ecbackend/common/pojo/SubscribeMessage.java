package com.yff.ecbackend.common.pojo;

import lombok.Data;
import java.util.*;

/**
 * 消息推送通用类
 */
@Data
public class SubscribeMessage {

    private String access_token;
    private String touser;
    private String template_id;
    private String page;
    private Map<String, TemplateData> data;


}
