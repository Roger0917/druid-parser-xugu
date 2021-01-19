package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreatePackageStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleExportParameterVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreatePackageStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
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
        List<XuguCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "create or replace package body xugu_test_pack is\n" +
                "procedure pack_proc1(id int,name varchar)is\n" +
                "begin\n" +
                "DBMS_OUTPUT.PUT_LINE(to_char(id)||name);\n" +
                "end;\n" +
                "function pack_fun1(id int,name varchar) return int is\n" +
                "begin\n" +
                "return id;\n" +
                "end;\n" +
                "procedure pack_proc2(x int,y int)is\n" +
                "begin\n" +
                "DBMS_OUTPUT.PUT_LINE(x+y);\n" +
                "end;\n" +
                "function pack_fun2(x int,y int) return int is\n" +
                "begin\n" +
                "return x+y;\n" +
                "end;\n" +
                "end;";

        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        Base.print(statementList);

        System.out.println("package statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            XuguCreatePackageStatement xuguCreatePackageStatement = (XuguCreatePackageStatement) statement;
            createPackageStatementList.add(xuguCreatePackageStatement);
        }
        System.out.println("提取包中的变量参数");
        for(XuguCreatePackageStatement xuguCreatePackageStatement:createPackageStatementList){
            System.out.println("包名 "+xuguCreatePackageStatement.getName());
            if(!xuguCreatePackageStatement.isBody()){
                System.out.println("包头: "+xuguCreatePackageStatement.getStatements().get(0));
            }
            else{
                System.out.println("包体: "+xuguCreatePackageStatement.getStatements());
            }
        }
    }
}
