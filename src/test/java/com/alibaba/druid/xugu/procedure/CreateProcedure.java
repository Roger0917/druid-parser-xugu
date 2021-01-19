package com.alibaba.druid.xugu.procedure;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.XuguTest;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleExportParameterVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguSchemaStatVisitor;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-29 09:18
 **/

public class CreateProcedure extends XuguTest {

    public void test_0() throws Exception {
        StringBuilder builder = new StringBuilder();
        List<SQLCreateProcedureStatement> createProcedureStatements = new ArrayList<>();
        String sql = "CREATE PROCEDURE sysdba.p (" +
                "  dept_no varchar" +
                ") AS " +
                "BEGIN" +
                "  DELETE FROM dept_temp" +
                "  WHERE department_id = dept_no;" +
                " " +
                "  IF SQL%FOUND THEN" +
                "    DBMS_OUTPUT.PUT_LINE (" +
                "      'Delete succeeded for department number ' || dept_no" +
                "    );" +
                "  ELSE" +
                "    DBMS_OUTPUT.PUT_LINE ('No department number ' || dept_no);" +
                "  END IF;" +
                "END;" +
                "/" +
                "BEGIN" +
                "  p(270);" +
                "  p(400);" +
                "END;"; //
        String sql2= "CREATE PROCEDURE syssso.p (" +
                "  dept_no varchar(20)" +
                ") AS " +
                "BEGIN" +
                "  DELETE FROM dept_temp" +
                "  WHERE department_id = dept_no;" +
                " " +
                "  IF SQL%FOUND THEN" +
                "    DBMS_OUTPUT.PUT_LINE (" +
                "      'Delete succeeded for department number ' || dept_no" +
                "    );" +
                "  ELSE" +
                "    DBMS_OUTPUT.PUT_LINE ('No department number ' || dept_no);" +
                "  END IF;" +
                "END;" +
                "/" +
                "BEGIN" +
                "  p(270);" +
                "  p(400);" +
                "END;"; //

        String sql3 = "create or replace procedure sysauditor.pro1 (x in int default 5,y out varchar(20) default 'abc',z in numeric(20,10) default 5,x1 in char(10) default 'a')as\n" +
                "                begin\n" +
                "                insert into student(id,name)values(x,y);\n" +
                "                end;";
        String sql4 = "create or replace function guest.fun1(x in int default 5,y out varchar(20) default 'abc',z in numeric(20,10) default 5,x1 out char(20) default 'b') return int as\n" +
                "begin \n" +
                "insert into student(id,name)values(x,y);\n" +
                "send_msg(5);\n"+
                "return 5;\n" +
                "end;";
        String sql5 = "create or replace procedure user_sod.pro2()\n" +
                "as\n" +
                "day1 int;\n" +
                "hour1 int; \n" +
                "minute1 int;\n" +
                "second1 int;\n" +
                "month1 int;\n" +
                "year1 int;\n" +
                "begin\n" +
                "insert into student(id,name)values(1,'roger');\n" +
                "send_msg(5);\n" +
                "DBMS_OUTPUT.PUT_LINE(to_char(5));\n" +
                "select * from sys_tables where db_id=current_db_id;\n" +
                "Select extract_day ('2008-8-8 20:10:5')into day1 from dual ;\n" +
                "Select extract_hour ('2008-8-8 20:10:5') into hour1 from dual;\n" +
                "Select extract_minute ('2008-8-8 20:10:5')into minute1 from dual ;\n" +
                "Select extract_second ('2008-8-8 20:10:5')into second1 from dual ;\n" +
                "Select extract_month ('2008-8-8 20:10:5')into month1 from dual ;\n" +
                "Select extract_year ('2008-8-8 20:10:5')into year1 from dual ;\n" +
                "end;\n";
        String sql6 = "create or replace procedure uu.proc1 is\n" +
                "begin\n" +
                "send_msg(6);\n" +
                "end;";
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        builder.append(sql5);
        builder.append(sql6);

        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        print(statementList);
    }
}
