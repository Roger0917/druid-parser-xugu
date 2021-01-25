package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

@Data
public class Param {

    private Integer index;
    
    private String name;
    
    private String paramType;
    
    private String dataType;
    
    private String defaultValue;
    
    private Integer precision;
    
    private Integer scale;
}
