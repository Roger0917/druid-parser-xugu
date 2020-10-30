package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreatePackageStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-30 14:07
 **/

public class OracleCreatePackage extends TestCase {

    public void test(){
        List<OracleCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = /*"CREATE OR REPLACE PACKAGE pack_test IS PROCEDURE proc1;\n" +
                "END;\n" +
                "CREATE OR REPLACE PACKAGE body pack_test IS\n" +
                "PROCEDURE proc1 IS\n" +
                "CURSOR mycur IS SELECT table_name FROM dba_tables1;\n" +
                "con INTEGER;\n" +
                "name VARCHAR(20);\n" +
                "BEGIN\n" +
                "SELECT count(*) INTO con FROM dba_tables1;\n" +
                "OPEN mycur;\n" +
                "FOR i IN 1..con LOOP\n" +
                "FETCH mycur INTO name;\n" +
                "DBMS_OUTPUT.PUT_LINE(name);\n" +
                "END LOOP;\n" +
                "close mycur;\n" +
                "END;\n" +
                "END;"
                +"\n"*/
                "create or replace package emp_pkg is\n" +
                        " procedure emp_update_ename(v_empno varchar2,v_ename varchar2);\n" +
                        " function emp_get_sal(v_empno varchar2) return number;\n" +
                        " end;\n";
                       /* "create or replace package body emp_pkg\n" +
                        "is\n" +
                        "    procedure emp_update_ename\n" +
                        "    (\n" +
                        "    v_empno varchar2,\n" +
                        "    v_ename varchar2\n" +
                        "    )\n" +
                        "    is\n" +
                        "    vename varchar2(32);\n" +
                        "    begin \n" +
                        "    update emp set ename=v_ename where empno=v_empno;\n" +
                        "    commit;\n" +
                        "    select ename into vename from emp where empno=v_empno;\n" +
                        "   \n" +
                        "    dbms_output.put_line('雇员名称：'||vename);\n" +
                        "    \n" +
                        "    end;\n" +
                        "    \n" +
                        "    function emp_get_sal\n" +
                        "    (\n" +
                        "    v_empno varchar2\n" +
                        "    )\n" +
                        "    return number is\n" +
                        "    vsal number(7,2);\n" +
                        "    begin\n" +
                        "    select sal into vsal from emp where empno=v_empno;\n" +
                        "    return vsal;\n" +
                        "    end;\n" +
                        "end;";*/

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        Base.oraclePrint(statementList);

        System.out.println("package statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            OracleCreatePackageStatement oracleCreatePackageStatement = (OracleCreatePackageStatement) statement;
            createPackageStatementList.add(oracleCreatePackageStatement);
        }
        System.out.println("提取包中的变量参数");

        for(OracleCreatePackageStatement oracleCreatePackageStatement:createPackageStatementList){
            System.out.println("包名 "+oracleCreatePackageStatement.getName());
            if(!oracleCreatePackageStatement.isBody()){
                System.out.println("包头-----");
                System.out.println(oracleCreatePackageStatement.getStatements());
                /*for(SQLStatement sqlStatement:oracleCreatePackageStatement.getStatements()){
                    //OracleCreatePackageStatement oracleCreatePackageStatement1 = (OracleCreatePackageStatement) sqlStatement;
                    System.out.println(sq);
                }*/
            }
            else{
                System.out.println("包体: "+oracleCreatePackageStatement.getStatements());
            }
        }
    }
}
