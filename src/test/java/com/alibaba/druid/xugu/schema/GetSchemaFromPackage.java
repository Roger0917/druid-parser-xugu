package com.alibaba.druid.xugu.schema;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class GetSchemaFromPackage extends TestCase {
    
    public void test(){
        String sql = /*"create or replace package xugu_test_pack is\n" +
                "                                procedure pack_proc1(id int,name varchar);\n" +
                "                                function pack_fun1(id int,name varchar) return int;\n" +
                "                                procedure pack_proc2(x int,y int);\n" +
                "                                function pack_fun2(x int,y int) return int;\n" +
                "                                end;"*/
        "create or replace package body sysdba.xugu_test_pack is\n" +
                "                procedure sysdba.pack_proc1(id int,name varchar)is\n" +
                "                begin\n" +
                "                DBMS_OUTPUT.PUT_LINE(to_char(id)||name);\n" +
                "\t       insert into uu.test_1(id,name)values(1,'roger');\n" +
                "                update uu.test_1 set uu.test_1.id=2,uu.test_1.name='name' where uu.test_1.id=1;\n" +
                "\t       delete from uu.test_1 where uu.test_1.id=1;\n" +
                "                end;\n" +
                "                function pack_fun1(id int,name varchar) return int is\n" +
                "                begin\n" +
                "\t       insert into sysdba.test_1(id,name)values(1,'roger');\n" +
                "                update sysdba.test_1 set sysdba.test_1.id=2,sysdba.test_1.name='name' where sysdba.test_1.id=1;\n" +
                "\t       delete from sysdba.test_1 where sysdba.test_1.id=1;\n" +
                "                return id;\n" +
                "                end;\n" +
                "                procedure pack_proc2(x int,y int)is\n" +
                "                begin\n" +
                "                DBMS_OUTPUT.PUT_LINE(x+y);\n" +
                " insert into uu.test_1(id,name)values(1,'roger');\n" +
        "                update uu.test_1 set uu.test_1.id=2,uu.test_1.name='name' where uu.test_1.id=1;\n" +
                "\t       delete from uu.test_1 where uu.test_1.id=1;\n" +
                "                end;\n" +
                "                function pack_fun2(x int,y int) return int is\n" +
                "                begin\n" +
                " insert into uu.test_1(id,name)values(1,'roger');\n" +
        "                update uu.test_1 set uu.test_1.id=2,uu.test_1.name='name' where uu.test_1.id=1;\n" +
                "\t       delete from uu.test_1 where uu.test_1.id=1;\n" +
                "                return x+y;\n" +
                "                end;\n" +
                "                end;";

        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);
        HashMap<String,String> map = new HashMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","uu1");
        String str = XuguParserApi.replacePackageSqlSchema(sql,map);
        System.out.println(222);
    }
}
