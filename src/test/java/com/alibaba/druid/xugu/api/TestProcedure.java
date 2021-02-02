package com.alibaba.druid.xugu.api;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import junit.framework.TestCase;

import java.util.List;

public class TestProcedure extends TestCase {
    
    public void test(){
        String sql = "create or replace procedure test_proc3(ss in interval minute(6) to second(3),id int,name varchar(20),money numeric(10,5),address char(10))\n" +
                "as\n" +
                " begin\n" +
                " insert into sysdba.t1(id,name) values(1,'roger');"+
                " select * from sysdba.sys_tables ;\n" +
                " update sysdba.t1 set id=1 where name='roger1';"+
                " delete from sysdba.t3 where sysdba.t3.id=1;"+
                " end;";
        String sql2 ="create or replace procedure test_proc3(ss in interval second)\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";
        List<CreateProcedureBean> createProcedureBeanList = XuguParserApi.parseCreateProcedure(sql2);
        System.out.println(222);
    }
}
