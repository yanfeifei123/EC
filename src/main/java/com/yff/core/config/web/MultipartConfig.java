package com.yff.core.config.web;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
//        factory.setMaxFileSize("10485760KB"); // KB,MB
        /// 总上传数据大小
//        factory.setMaxRequestSize("10485760KB");
        return factory.createMultipartConfig();
    }

}
