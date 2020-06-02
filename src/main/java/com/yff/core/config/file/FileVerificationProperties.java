package com.yff.core.config.file;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;


@Configuration
@ConfigurationProperties(prefix = "fileupload")
public class FileVerificationProperties {

    private String uploadTypesOf;

    private String uploadPath;

    private String fileRelativepath;

    public String getUploadTypesOf() {
        return uploadTypesOf;
    }

    public void setUploadTypesOf(String uploadTypesOf) {
        this.uploadTypesOf = uploadTypesOf;
    }

    public String getUploadPath()   {

        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getFileRelativepath() {
        return fileRelativepath;
    }

    public void setFileRelativepath(String fileRelativepath) {
        this.fileRelativepath = fileRelativepath;
    }
}
