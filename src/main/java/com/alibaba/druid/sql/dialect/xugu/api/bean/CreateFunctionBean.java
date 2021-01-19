package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-02 15:16
 **/
@Data
public class CreateFunctionBean {

    private String functionName;

    private Integer paramSize;

    private List<List<String>> params;

    private String returnType;
}
