package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
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

        builder.append(sql1);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        builder.append(sql5);
        builder.append(sql6);
        builder.append(sql7);
        builder.append(sql8);

        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();

    }
}
