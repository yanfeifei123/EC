package com.yff.core.config.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class MyTomcatWebServerCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {


    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setAttribute("relaxedPathChars", "\\");
                connector.setAttribute("relaxedQueryChars", "\\");

//                connector.setProperty("relaxedPathChars", "\\");
//                connector.setProperty("relaxedQueryChars", "\\");
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
                protocol.setMaxSavePostSize(0);
                protocol.setCompressibleMimeType("text/html,text/xml,text/plain,text/css,text/javascript,application/javascript");
            }
        });
    }
}
