package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-09 16:44
 **/
@Data
public class CreateTypeBean {

    private String header;

    private String body;

    private Map<String,String> attributeMap;

    private Map<String,String> methodMap;



}
