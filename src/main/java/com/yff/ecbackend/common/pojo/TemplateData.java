package com.yff.ecbackend.common.pojo;

import lombok.Data;

@Data
public class TemplateData {

    private Object value;

    public TemplateData(Object value) {
        this.value = value;
    }
}
