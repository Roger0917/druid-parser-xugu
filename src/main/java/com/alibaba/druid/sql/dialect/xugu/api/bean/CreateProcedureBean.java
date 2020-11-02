package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-02 14:11
 **/
@Data
public class CreateProcedureBean {

    private String procedureName;

    private Integer paramSize;

    private List<List<String>> params;
}
