package com.alibaba.druid.xugu.api;



import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

/**
 * @Author: zkun
 * @Date: 2021/2/2 14:47
 */
public class SqlParserReplaceTest {


    String proSql1 = "create  or replace  procedure  scp()  \n" +
            "as\n" +
            "begin\n" +
            "\tselect * from test_user.sct;\n" +
            "end;";
    String proSql2 = "create  or replace  procedure  test_user.scp2()  \n" +
            "as\n" +
            "begin\n" +
            "\tselect * from test_user.sct;\n" +
            "end;";
    String proSql3 = "create   procedure scp1()\n" +
            "as\n" +
            "begin\n" +
            "select * from sct;\n" +
            "end;";
    String proSql4 = "create  or replace  procedure  test_user.scp3()  \n" +
            "as\n" +
            "begin\n" +
            "\tselect * from sct;\n" +
            "end;";

    String funSql1 = "create  or replace  function  scf()  \n" +
            "return int\n" +
            "as\n" +
            "res int;\n" +
            "begin\n" +
            "\tselect id into res from test_user.sct;\n" +
            "return res;\n" +
            "end;";
    String funSql2 = "create  or replace  function  test_user.scf2()  \n" +
            "return int\n" +
            "as\n" +
            "res int;\n" +
            "begin\n" +
            "\tselect id into res from test_user.sct;\n" +
            "return res;\n" +
            "end;";
    String funSql3 = "create or replace function scf1()\n" +
            "return int\n" +
            "as\n" +
            "res int;\n" +
            "begin\n" +
            "\tselect id into res from test_user.sct;\n" +
            "return res;\n" +
            "end;";
    String funSql4 = "create  or replace  function  test_user.scf3()  \n" +
            "return int\n" +
            "as\n" +
            "res int;\n" +
            "begin\n" +
            "\tselect id into res from test_user.sct;\n" +
            "return res;\n" +
            "end;";

    String pack1 = "create or replace package scpa is\n" +
            "procedure proc1();\n" +
            "procedure proc2();\n" +
            "procedure proc3();\n" +
            "procedure proc4();\n" +
            "end;" +
            "create package body scpa as\n" +
            "\n" +
            "procedure proc1()\n" +
            "as\n" +
            "begin\n" +
            "\tselect * from test_user.sct;\n" +
            "end;\n" +
            "\n" +
            "procedure proc2()  \n" +
            "as\n" +
            "begin\n" +
            "\tselect * from test_user.sct;\n" +
            "end;\n" +
            "\n" +
            "procedure proc3()\n" +
            "as\n" +
            "begin\n" +
            "select * from sct;\n" +
            "end;\n" +
            "\n" +
            "procedure  proc4()  \n" +
            "as\n" +
            "begin\n" +
            "\tselect * from sct;\n" +
            "end;\n" +
            "end;";
    String pack2 = "";
    String pack3 = "";
    String pack4 = "";

    static HashMap<String,String> schemasMap = new HashMap<>();


    @BeforeClass
    public static void before(){
        schemasMap.put("test_user","d1");
    }

    @Test
    public void functionReplaceTest1(){
        String s = replaceSchemas(funSql1,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void functionReplaceTest2(){
        String s = replaceSchemas(funSql2,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void functionReplaceTest3(){
        String s = replaceSchemas(funSql3,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void functionReplaceTest4(){
        String s = replaceSchemas(funSql4,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void procedureReplaceTest1(){
        String s = replaceSchemas(proSql1,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void procedureReplaceTest2(){
        String s = replaceSchemas(proSql2,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void procedureReplaceTest3(){
        String s = replaceSchemas(proSql3,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void procedureReplaceTest4(){
        String s = replaceSchemas(proSql4,schemasMap);
        System.out.println(s);
        Assert.assertNotNull(s);
    }

    @Test
    public void packageReplaceTest1(){
        String s =  replaceSchemas(pack1,schemasMap);
        System.out.println(s);
            Assert.assertNotNull(s);
    }



    public String replaceSchemas(String sql,  HashMap<String,String> schemasMap){
        //正则表达式按空格分隔
        String s1[] = sql.split("\\s+");
        String define = sql;
        for(int i = 0;i<s1.length;i++){
            String typeName = s1[i].toLowerCase();
            if("view".equalsIgnoreCase(typeName)){
                define = XuguParserApi.replaceViewSqlSchema(sql,schemasMap);
                break;
            }else if("package".equalsIgnoreCase(typeName)){
                define =  XuguParserApi.replacePackageSqlSchema(sql,schemasMap);
                break;
            }else if("function".equalsIgnoreCase(typeName)){
                define = XuguParserApi.replaceFunctionSqlSchema(sql,schemasMap);
                break;
            }else if("trigger".equalsIgnoreCase(typeName)){
                define = XuguParserApi.replaceTriggerSchema(sql,schemasMap);
                break;
            }else if("procedure".equalsIgnoreCase(typeName)){
                define = XuguParserApi.replaceProcedureSqlSchema(sql,schemasMap);
                break;
            }
        }
        return define;
    }


}
