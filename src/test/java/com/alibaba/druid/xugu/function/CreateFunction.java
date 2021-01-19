package com.alibaba.druid.xugu.function;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-29 09:19
 **/

public class CreateFunction extends TestCase {

    public void test(){
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "create function xugu_test_fun(id in int,name in varchar,address out varchar) return int";
        String sql2 = "create function xugu_test_fun2() return int";
                /*"sql2begin\n" +
                "DBMS_OUTPUT.PUT_LINE(id);\n" +
                "DBMS_OUTPUT.PUT_LINE(name);\n" +
                "return 5;\n" +
                "end;\n";*/
        //String sql2 = "create or replace procedure xugu_test_pro(id in int,name in varchar,address out varchar)";
        String[] arr = sql2.split("\\s+");
        if(sql2.contains("as")||sql2.contains("is")||sql2.contains("AS")||sql2.contains("IS")){
            for(String str:arr){
                if("as".equalsIgnoreCase(str)||"is".equalsIgnoreCase(str)){
                    builder.append(sql.substring(0,sql2.indexOf(str)));
                }
            }
        }else{
            builder.append(sql2);
        }

        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        Base.print(statementList);

        System.out.println("Function statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            SQLCreateFunctionStatement sqlCreateFunctionStatement = (SQLCreateFunctionStatement) statement;
            createFunctionStatementList.add(sqlCreateFunctionStatement);
        }

        System.out.println("提取存储函数中的变量参数");
        for(SQLCreateFunctionStatement sqlCreateFunctionStatement:createFunctionStatementList){
            System.out.println("函数名 "+sqlCreateFunctionStatement.getName());
            System.out.println("参数个数: "+sqlCreateFunctionStatement.getParameters().size());
            System.out.println("函数返回类型: "+sqlCreateFunctionStatement.getReturnDataType());
            for(int i=0;i<sqlCreateFunctionStatement.getParameters().size();i++){
                SQLParameter parameter = sqlCreateFunctionStatement.getParameters().get(i);
                SQLParameter.ParameterType type = parameter.getParamType();
                SQLExpr expr = parameter.getDefaultValue();
                if(type!=null&&expr!=null){
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"参数类型: "+parameter.getParamType()+" "+"数据类型: "+parameter.getDataType().getName()+"参数默认值: "+parameter.getDefaultValue()+" 参数下标: "+i);
                }else if(type!=null&&expr==null){
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"参数类型: "+parameter.getParamType()+" "+"数据类型: "+parameter.getDataType().getName()+" 参数下标: "+i);
                }else if(type==null&expr!=null){
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"数据类型: "+parameter.getDataType().getName()+"参数默认值: "+parameter.getDefaultValue()+" 参数下标: "+i);
                }else{
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"数据类型: "+parameter.getDataType().getName()+" 参数下标: "+i);
                }
            }
        }
    }
}
