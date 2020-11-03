package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-03 14:22
 **/

@Data
public class CreatePackageBean {

    private List<CreateProcedureBean> createProcedureBeans;

    private List<CreateFunctionBean> createFunctionBeans;
}
