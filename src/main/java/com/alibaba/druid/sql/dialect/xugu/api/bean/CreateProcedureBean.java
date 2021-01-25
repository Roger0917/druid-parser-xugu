package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-02 14:11
 **/
@Data
public class CreateProcedureBean {

    private String procedureName;

    private Integer paramSize;

    private List<Param> params;
    
    private Map<Integer,String> schemas;
}
