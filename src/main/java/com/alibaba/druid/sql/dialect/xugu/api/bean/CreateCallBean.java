package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-09 19:19
 **/
@Data
public class CreateCallBean {

    private String name;

    private List<String> paramList;
}
