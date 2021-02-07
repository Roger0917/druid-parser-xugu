package com.alibaba.druid.xugu.api;

import cn.hutool.core.map.CaseInsensitiveMap;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestPackage extends TestCase {
    
    public void test(){
        String sql = "create or replace package scpa is\n" +
                "procedure proc1();\n" +
                "function proc2() return int;\n" +
                "procedure proc3();\n" +
                "procedure proc4();\n" +
                "end;\n" +
                "\n" +
                "create package body scpa as\n" +
                "\n" +
                "procedure proc1()\n" +
                "as\n" +
                "begin\n" +
                " select * from test_user.sct;\n" +
                "end;\n" +
                "\n" +
                "\n" +
                "function proc2() return int  \n" +
                "as\n" +
                "begin\n" +
                " select * from test_user.sct;\n" +
                "end;\n" +
                "\n" +
                "procedure proc3()\n" +
                "as\n" +
                "begin\n" +
                "select * from u3.sct;\n" +
                "end;\n" +
                "\n" +
                "\n" +
                "procedure  proc4()  \n" +
                "as\n" +
                "begin\n" +
                " select * from u2.sct;\n" +
                "end;\n" +
                "\n" +
                "end;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);
        CaseInsensitiveMap<String,String> map = new CaseInsensitiveMap<>();
        map.put("u1","s1");
        map.put("u2","s2");
        map.put("u3","s3");
        map.put("TEST_USER","test_user2");
        boolean b1 = map.containsKey("u1");
        boolean b2 = map.containsKey("U1");

        String returnStr = XuguParserApi.replacePackageSqlSchema(sql,map,"");
        System.out.println(222);
    }
}
