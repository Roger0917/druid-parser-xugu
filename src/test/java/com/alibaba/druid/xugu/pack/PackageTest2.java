package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreatePackageBean;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class PackageTest2 extends TestCase {
    
    public void test(){
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
}
