package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreatePackageStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleExportParameterVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-30 11:45
 **/

public class CreatePackageBody extends TestCase {

    public void test(){
        List<OracleCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "create or replace package body xugu_test_pack is\n" +
                "procedure pack_proc1(id int,name varchar)is\n" +
                "begin\n" +
                "DBMS_OUTPUT.PUT_LINE(to_char(id)||name);\n" +
                "end;\n" +
                "function pack_fun1(id int,name varchar)return int is\n" +
                "begin\n" +
                "return id;\n" +
                "end;\n" +
                "procedure pack_proc2(x int,y int)is\n" +
                "begin\n" +
                "DBMS_OUTPUT.PUT_LINE(x+y);\n" +
                "end;\n" +
                "function pack_fun2(x int,y int)return int is\n" +
                "begin\n" +
                "return x+y;\n" +
                "end;\n" +
                "end;";

        OracleSchemaStatVisitor visitor = new OracleSchemaStatVisitor();

        final StringBuilder out = new StringBuilder();
        final OracleExportParameterVisitor visitor1 = new OracleExportParameterVisitor(out);
        visitor1.setParameterizedMergeInList(true);

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        Base.print(statementList);

        System.out.println("package statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            statement.accept(visitor1);
            statement.accept(visitor);
            OracleCreatePackageStatement oracleCreatePackageStatement = (OracleCreatePackageStatement) statement;
            createPackageStatementList.add(oracleCreatePackageStatement);
        }
        System.out.println("统计包中使用的表、字段、过滤条件、排序表达式、分组表达式");
        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());
        //System.out.println("Parameters : " + visitor.getParameters());
        System.out.println("coditions : " + visitor.getConditions());
        System.out.println("relationships : " + visitor.getRelationships());
        System.out.println("orderBy : " + visitor.getOrderByColumns());
        System.out.println("提取包中的变量参数");

        for(OracleCreatePackageStatement oracleCreatePackageStatement:createPackageStatementList){
            System.out.println("包名 "+oracleCreatePackageStatement.getName());
            if(!oracleCreatePackageStatement.isBody()){
                System.out.println("包头: "+oracleCreatePackageStatement.getStatements());
            }
            else{
                System.out.println("包体: "+oracleCreatePackageStatement.getStatements());
            }
        }
    }
}
