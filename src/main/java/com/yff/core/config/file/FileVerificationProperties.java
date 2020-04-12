package com.yff.core.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "fileupload")
public class FileVerificationProperties {

    private String uploadTypesOf;

    public String getUploadTypesOf() {
        return uploadTypesOf;
    }

    public void setUploadTypesOf(String uploadTypesOf) {
        this.uploadTypesOf = uploadTypesOf;
    }
}
