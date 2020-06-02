package com.yff.core.config.tomcat;


import org.apache.catalina.valves.RemoteIpValve;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.catalina.Context;
import org.springframework.stereotype.Component;

@Configuration
public class TomcatConfig {

    @Value("${http.port}")
    private int httpport;
    @Value("${server.port}")
    private int httpsprot;

    @Bean
    public Connector connector(){
        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");

        connector.setScheme("http");
        connector.setPort(httpport);
        connector.setSecure(false);
        connector.setRedirectPort(httpsprot);

        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint=new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection=new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };


        tomcat.addAdditionalTomcatConnectors(connector);

        return tomcat;
    }

    @Component
    public class MyTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {


        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
                @Override
                public void customize(Connector connector) {

                    connector.setProperty("relaxedPathChars", "|{}");
                    connector.setProperty("relaxedQueryChars", "|{}");
                    connector.setScheme("https");

                    Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                    // 设置最大缓冲区
                    protocol.setMaxHttpHeaderSize(30000);
                    protocol.setAcceptCount(5000);
                    protocol.setMaxConnections(5000);
                    protocol.setMaxThreads(1000);
                    protocol.setMinSpareThreads(100);
                    protocol.setConnectionTimeout(30000);
                    protocol.setCompression("on");
                    protocol.setCompressionMinSize(1024);
                    protocol.setDisableUploadTimeout(true);
                    protocol.setUseSendfile(false);
                    protocol.setSSLEnabled(true);
//                protocol.setMaxSavePostSize(0);
                    protocol.setCompressibleMimeType("text/html,text/xml,text/plain,text/css,text/javascript,application/javascript");
//                System.out.println("内置Tomcat https请求优化");
                }
            });
        }
    }


}
