package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateViewBean {

    private String name;

    private String tableName;

    private String schemaName;

}
