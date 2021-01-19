package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

@Data
public class BlockProAndFunBean {

    /** 块语句调用的过程或函数名 **/
    private String name;

    private List<String> parameters;
}
