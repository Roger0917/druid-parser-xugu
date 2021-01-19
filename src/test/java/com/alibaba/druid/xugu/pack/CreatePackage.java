package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreatePackageStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleExportParameterVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreatePackageBean;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreatePackageStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-30 10:56
 **/

public class CreatePackage extends TestCase {

    public void test(){
        List<XuguCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "create or replace package xugu_test_pack is\n" +
                "                procedure pack_proc1(id int,name varchar);\n" +
                "                function pack_fun1(id int,name varchar) return int;\n" +
                "                procedure pack_proc2(x int,y int);\n" +
                "                function pack_fun2(x int,y int) return int;\n" +
                "                end;";
        String sql2 = "CREATE PACKAGE SYSDBA.DBMS_DBA \n" +
                "        IS \n" +
                "               PROCEDURE KILL_TRANS(NODEID INTEGER,TRANID BIGINT);\n" +
                "               PROCEDURE KILL_SESSION(NODEID INTEGER,SESSID INTEGER);\n" +
                "               PROCEDURE KILL_SESSION_TRANS(NODEID INTEGER,SESSID INTEGER);\n" +
                "               FUNCTION FILE_LIST(_dir VARCHAR) RETURN FInfoTab;\n" +
                "        END SYSDBA.DBMS_DBA;";

        String sql3="CREATE PACKAGE SYSDBA.DBMS_INFO \n" +
                "IS\n" +
                "\tSUBTYPE  FileInfo IS RECORD(IS_DIR BOOLEAN,DB_PATH VARCHAR(256),OS_PATH VARCHAR(256));\n" +
                //"\tSUBTYPE  FInfoTab IS TABLE OF FileInfo;\n" +
                //"\tFUNCTION FILE_LIST(_dir VARCHAR) RETURN FInfoTab;\n" +
                "END SYSDBA.DBMS_INFO";

        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql3);
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.println(packageBean.getCreateFunctionBeans());

        /*XuguStatementParser  parser = new XuguStatementParser(sql2);
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
                System.out.println("包头: "+xuguCreatePackageStatement.getStatements().get(1));
            }
            else{
                System.out.println("包体: "+xuguCreatePackageStatement.getStatements());
            }
        }*/
    }
}
