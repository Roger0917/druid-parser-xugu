package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

@Data
public class CreateExecuteBean {

    private String schema;

    private String name;

    private List<String> paramList;
}
