package com.alibaba.druid.sql.dialect.xugu.api.bean;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-02 17:08
 **/
@Data
public class CreateTriggerBean {

    private String triggerName;

    /**类型, for each row or statement*/
    private String triggerType;

    /**触发时机,After,Before*/
    private String triggerOccasion;

    /** 如果有更新,更新的列 */
    private List<String> updateColumns;

    /** 受监视的操作类型 */
    private List<String> operators;

    private String when;

    private String body;

    private String define;

    /** 作用的表 */
    private String table;

}
