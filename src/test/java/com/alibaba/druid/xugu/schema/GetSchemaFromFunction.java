package com.alibaba.druid.xugu.schema;

import cn.hutool.core.map.CaseInsensitiveMap;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLPartition;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLBlockStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSchemaFromFunction extends TestCase {

    public void test() throws ParserBusinessException {
        String sql = "create or replace function fun1(a int,b interval minute to second) return interval minute(7) to second(3) as\n" +
                "declare x int;\n" +
                "y varchar;\n" +
                "z char(10);\n" +
                "z1 numeric(5,3);\n" +
                "begin\n" +
                "select t.id,t.tname from t1 t inner join s1 s on t.tid=s.tid where s.tid=1;\n" +
                "select tid from t1 union select tid from s1;\n" +
                "insert into t1(tid,tname)values(1,'tname');\n" +
                "update t1 set name='tname',tid=2 where id=1 and name='tname2';\n" +
                // "update schema1.t1 set t1.tname='tname' where schema1.t1.tid=1 or schema1.t1.tname='tname';\n" +
                "delete from t1 where name='tname' and address='taddress' and uu=1;\n" +
                "return 5;"+
                "end;";
        String sql2 = "CREATE OR REPLACE FUNCTION fun1 (\n" +
                "\ta int, \n" +
                "\tb INTERVAL MINUTE TO SECOND\n" +
                ")\n" +
                "RETURN int\n" +
                "AS\n" +
                "declare x int;\n" +
                "\ty varchar;\n" +
                "\tz char(10);\n" +
                "\tz1 numeric(5, 3);\n" +
                "BEGIN\n" +
                "\tsend_msg(5);\n" +
                "\treturn 5;\n" +
                "END;";

        String sql3 = "create or replace function test_function(a inout int) return int as\n" +
                " begin\n" +
                "   a:=5;\n" +
                "   return a;\n" +
                " end;";
        XuguStatementParser parser = new XuguStatementParser(sql+sql2+sql3);
        List<SQLStatement> statementList = parser.parseStatementList();

        HashMap<String,String> map = new HashMap<>();
        map.put("schema1","schema2");
        map.put("sysdba","sysdba1");
        String returnStr = XuguParserApi.replaceFunctionSqlSchema(sql,map,"schema1");
        System.out.println(222);
    }

    public void test4() throws ParserBusinessException {
        String sql = "create or replace function test_procedure(a out int) return int\n" +
                "                as\n" +
                "                b int;\n" +
                "                begin\n" +
                "                 b:=5;\n" +
                "                for i in 1..10 loop\n" +
                "insert into test_proc_tab(id,dt)values(1,sysdate);\n" +
                "                update test_proc_tab set id=5 where dt=sysdate;\n" +
                "delete from test_proc_tab where id=5; \n" +
                "select * from test_proc_tab;               \n" +
                "b:=b+i;\n" +
                "                end loop;\n" +
                "                a:=b;\n" +
                "return 5;"+
                "                end;";
        Map<String, String> map = new CaseInsensitiveMap<>();
        map.put("qwe", "sysdba1");
        map.put("syssso", "syssso1");
        map.put("guest", "guest1");
        String resourceSchema = "qwe";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String returnStr = XuguParserApi.replaceFunctionSqlSchema(sql, map, resourceSchema);
        System.out.println(returnStr);
    }
    
    public void test5() throws ParserBusinessException {
        String sql = "create or replace function test_procedure(a datetime with time zone,b time with time zone) return time with time zone\n" +
                "                as\n" +
                "                b int;\n" +
                "                begin\n" +
                "                 b:=5;\n" +
                "                for i in 1..10 loop\n" +
                "insert into test_proc_tab(id,dt)values(1,sysdate);\n" +
                "                update test_proc_tab set id=5 where dt=sysdate;\n" +
                "delete from test_proc_tab where id=5; \n" +
                "select * from test_proc_tab;               \n" +
                "b:=b+i;\n" +
                "                end loop;\n" +
                "                a:=b;\n" +
                "return a;"+
                "                end;";
        Map<String, String> map = new CaseInsensitiveMap<>();
        map.put("qwe", "sysdba1");
        map.put("syssso", "syssso1");
        map.put("guest", "guest1");
        String resourceSchema = "qwe";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<CreateFunctionBean> functionBeans = XuguParserApi.parseCreateFunction(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String returnStr = XuguParserApi.replaceFunctionSqlSchema(sql, map, resourceSchema);
        System.out.println(returnStr);
    }
    
    public void test6() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE FUNCTION ff5(p1 DATETIME WITH TIME zone) RETURN DATETIME WITH TIME zone AS\n" +
                "BEGIN\n" +
                " RETURN p1;\n" +
                "END";
        List<CreateFunctionBean> createFunctionBeans = XuguParserApi.parseCreateFunction(sql);
        System.out.println(222);
    }
    
    public void test7() throws ParserBusinessException {
        String sql = "create or replace procedure test_procedure(a date,b numeric(4,2)) as\n" +
                "                begin\n" +
                "                send_msg('a='||a||','||'b='||b);\n" +
                "                end;\n" +
                " create or replace procedure test_procedure(a interval month,b interval day to second) as\n" +
                "      begin\n" +
                "          send_msg('a='||to_char(a)||','||'b='||to_char(b));\n" +
                "       end;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<CreateProcedureBean> createProcedureBeanList = XuguParserApi.parseCreateProcedure(sql);
        System.out.printf("222");
    }
}
