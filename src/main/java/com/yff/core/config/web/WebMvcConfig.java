package com.yff.core.config.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yff.core.safetysupport.jwt.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 开发PC端在开启
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/view/**").addResourceLocations("classpath:/view/");
        super.addResourceHandlers(registry);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截路径可自行配置多个 可用 ，分隔开

//        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/static/**")
//                .excludePathPatterns("/view/**");




    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }


    /**
     * 配置使项目使用fastJson

     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        FastJsonConfig config = new FastJsonConfig();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        config.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);//格式化时间
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);

//        config.setSerializerFeatures(SerializerFeature.WriteNullNumberAsZero);
//        config.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
//        config.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }


}
