package com.alibaba.druid.sql.dialect.xugu.api.exception;

/**
 * @Author: Roger
 * @Date: 2021/2/9 17:30
 */
public class ParserBusinessException extends Exception{
    
    public ParserBusinessException(Object obj){
        super(obj.toString());
    }
}
