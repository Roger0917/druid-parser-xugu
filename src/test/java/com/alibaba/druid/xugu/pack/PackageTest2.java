package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreatePackageBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class PackageTest2 extends TestCase {
    
    public void test() throws ParserBusinessException {
        StringBuilder builder = new StringBuilder();
        String sql1 = "CREATE OR REPLACE PACKAGE table_con IS " +
                "FUNCTION dfsfdee() RETURN INTEGER;" +
                "function fefe(id int) return integer;" +
                "function  fefe (name varchar(20)) return integer;" +
                "procedure dfeffd (id integer);" +
                "procedure dfefefef();"+
                " END;";
        String sql2 = "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name varchar(20)) return integer;" +
                " END;";
        String sql3 =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name varchar(20)) return integer;" +
                "function  feeyk (name varchar(22)) return integer;" +
                " END;";
        String sql4 =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name numeric(20,2)) return integer;" +
                " END;";
        String sql5 =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name numeric(20,2)) return integer;" +
                "function  fredfd (name numeric(21,2)) return integer;"+
                " END;";
        String sql6 =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "procedure dfeffd (name varchar(33));" +
                " END;";
        String sql7 =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "FUNCTION dfsfdee() RETURN INTEGER;" +
                " END;";
        String sql8 =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "procedure dfefefef();"+
                " END;";
        String sql9 = "CREATE PACKAGE PACK02 AS\n" +
                "FUNCTION MAX_VAL(P1 IN INT,P2 IN INT) RETURN INT;\n" +
                "FUNCTION MAX_VAL(P1 IN INT,P2 IN INT,P3 IN INT) RETURN INT;\n" +
                "END;\n" +
                "\n" +
                "CREATE PACKAGE BODY PACK02 AS\n" +
                "FUNCTION MAX_VAL(P1 IN INT,P2 IN INT) RETURN INT AS\n" +
                "BEGIN\n" +
                "IF P1>=P2 THEN\n" +
                "RETURN P1;\n" +
                "ELSE\n" +
                "RETURN P2;\n" +
                "END IF;\n" +
                "END;\n" +
                "FUNCTION MAX_VAL(P1 IN INT,P2 IN INT,P3 IN INT) RETURN INT AS\n" +
                "BEGIN\n" +
                "IF P1>=P2 THEN\n" +
                "IF P1>=P3 THEN\n" +
                "RETURN P1;\n" +
                "ELSE\n" +
                "RETURN P3;\n" +
                "END IF;\n" +
                "ELSE\n" +
                "IF P2>=P3 THEN\n" +
                "RETURN P2;\n" +
                "ELSE\n" +
                "RETURN P3;\n" +
                "END IF;\n" +
                "END IF;\n" +
                "END;\n" +
                "END;";

        builder.append(sql1);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        builder.append(sql5);
        builder.append(sql6);
        builder.append(sql7);
        builder.append(sql8);

        XuguStatementParser parser = new XuguStatementParser(sql9);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();
        CreatePackageBean createPackageBean = XuguParserApi.parseCreatePackage(sql9);
        System.out.println(222);
    }
    
    public void test1() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.PACK01 IS \n" +
                " FUNCTION IP01 (\n" +
                "  P1 IN INT, \n" +
                "  P2 INT\n" +
                " )\n" +
                " RETURN INT\n" +
                " ;\n" +
                "END ";
        CreatePackageBean bean = XuguParserApi.parseCreatePackage(sql);
        System.out.println(222);
    }
    
    public void test2() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE PACKAGE DBMS_UTILITY AS\n" +
                " FUNCTION GET_TIME RETURN BIGINT;\n" +
                " FUNCTION FORMAT_ERROR_STACK RETURN VARCHAR;\n" +
                "END;";
        CreatePackageBean bean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("222");
    }
    
    public void test3() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE PACKAGE DBMS_SQL2 AS\n" +
                "  TYPE VARCHAR2_TABLE IS TABLE OF VARCHAR2(2000);\n" +
                "  TYPE NUMBER_TABLE IS TABLE OF NUMBER;\n" +
                "END;";
        CreatePackageBean bean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("222");
    }
    
    public void test4() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE PROCEDURE FMIS9912.ADD_GLDXTABLE_FILTE_POLICY(\n" +
                "        I_TABLENAME IN VARCHAR2,\n" +
                "        I_SHAREORNOT IN NUMBER )\n" +
                "  --功能说明： 管理对象主表添加策略.\n" +
                "  --编写时间： 2010.0310 合库版本增加.\n" +
                "  --编 写 人： 代大春\n" +
                "  --参数说明： I_TABLENAME  单位代码COMPID\n" +
                "  --           I_SHAREORNOT 管理对象共享标志\n" +
                "IS\n" +
                "  V_COUNT             NUMBER(10);\n" +
                "  V_TABLENAME         VARCHAR2(40);\n" +
                "  V_POLICY_NAME       VARCHAR2(40);\n" +
                "  V_USER              VARCHAR2(30);\n" +
                "  V_SQL               VARCHAR2(2000);\n" +
                "  V_DATA_CENTER_LEVEL INTEGER;\n" +
                "  V_POLICY_FUNC       VARCHAR2(100);\n" +
                "  CURSOR C_GLDX_POLICY(I_OBJNAME VARCHAR2) IS\n" +
                "    SELECT * FROM XUGU_USER_POLICIES WHERE OBJECT_NAME=I_OBJNAME; --CHANGE USER_POLICIES XUGU_USER_POLICIES BY XUGU\n" +
                "BEGIN\n" +
                "  V_DATA_CENTER_LEVEL:=PACK_GLDXFIXFIELDS.GET_DB_LEVEL;\n" +
                "  IF V_DATA_CENTER_LEVEL=1 THEN\n" +
                "    RETURN;\n" +
                "  END IF;\n" +
                "  V_TABLENAME:=TRIM(UPPER(I_TABLENAME));\n" +
                "  V_USER:=SYS_CONTEXT('USERENV','CURRENT_SCHEMA');\n" +
                "  FOR V_GLDX_POLICY IN C_GLDX_POLICY(V_TABLENAME)\n" +
                "    LOOP\n" +
                "      DBMS_RLS.DROP_POLICY(V_USER,V_TABLENAME,V_GLDX_POLICY.POLICY_NAME);\n" +
                "    END LOOP;\n" +
                "  /*IF I_SHAREORNOT=1 THEN\n" +
                "    VPD_PKG.DROP_POLICY(V_TABLENAME);\n" +
                "    RETURN;\n" +
                "  END IF;*/\n" +
                "  V_SQL:='SELECT COUNT(*) FROM XUGU_USER_TAB_COLUMNS WHERE COLUMN_NAME=''COMPID'' AND TABLE_NAME='''||V_TABLENAME||''''; --CHANGE USER_TAB_COLUMNS TO XUGU_USER_TAB_COLUMNS BY XUGU\n" +
                "  V_COUNT:=SQLVALUE_NUM(V_SQL);\n" +
                "  IF V_COUNT>0 THEN\n" +
                "    V_POLICY_NAME:='PLC_'||V_TABLENAME;\n" +
                "    --集中部署管理对象策略调整为FILTER_DEPLOY_DXCOMPID+SHAREORNOT\n" +
                "    --集中部署再调整，所有共享标志都统一增加策略函数VPD_PKG.FILTER_DEPLOY_SHARE_DXCOMPID\n" +
                "    --V_POLICY_FUNC:='VPD_PKG.FILTER_DEPLOY_DXCOMPID'||I_SHAREORNOT;\n" +
                "    V_POLICY_FUNC:='VPD_PKG.FILTER_DEPLOY_SHARE_DXCOMPID';\n" +
                "--    DBMS_RLS.ADD_POLICY(OBJECT_SCHEMA     =>V_USER,\n" +
                "--                        OBJECT_NAME       =>V_TABLENAME,\n" +
                "--                        POLICY_NAME       =>V_POLICY_NAME,\n" +
                "--                        FUNCTION_SCHEMA   =>V_USER,\n" +
                "--                        POLICY_FUNCTION   =>V_POLICY_FUNC,\n" +
                "--                        STATEMENT_TYPES   =>'SELECT,UPDATE,INSERT,DELETE',\n" +
                "--                        UPDATE_CHECK ";
        CreatePackageBean bean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("222");
    }
}
