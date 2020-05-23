package com.yff.ecbackend.common.view;

import lombok.Data;

@Data
public class CommonReturnType {

    private Integer code = 1;
    private String msg = "ok";
    private Object data;

}
