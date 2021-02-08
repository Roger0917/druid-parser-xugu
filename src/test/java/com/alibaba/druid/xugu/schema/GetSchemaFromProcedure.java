package com.alibaba.druid.xugu.schema;

import cn.hutool.core.map.CaseInsensitiveMap;
import com.alibaba.druid.pool.bonecp.TestPSCache;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetSchemaFromProcedure extends TestCase {
    
    public void test(){
        //id datetime with time zone,name time with time zone
        String sql = "create or replace procedure procedure1(id datetime with time zone,name time with time zone) as\n" +
                "declare x int;\n" +
                "y varchar;\n" +
                "z char(10);\n" +
                "z1 numeric(5,3);\n" +
                "begin\n" +
                "select schema1.t1.id,schema1.t1.tname from schema1.t1 t inner join schema1.s1 s on schema1.t1.tid=schema1.s1.tid where schema1.s1.tid=1;\n" +
                "select tid from schema1.t1 union select tid from schema1.s1;\n" +
                "insert into schema1.t1(tid,tname)values(1,'tname');\n" +
                "update schema1.t1 set schema1.t1.tname='tname',schema1.t1.tid=2 where schema1.t1.tid=1 and schema1.t1.tname='tname2';\n" +
               // "update schema1.t1 set t1.tname='tname' where schema1.t1.tid=1 or schema1.t1.tname='tname';\n" +
                "delete from schema1.t1 where schema1.t1.tname='tname' and schema1.t1.taddress='taddress' and schema1.t1.uu=1;\n" +
                "end;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);

        HashMap<String,String> map = new HashMap<>();
        map.put("schema1","schema2");
        map.put("sysdba","sysdba1");
        String returnStr = XuguParserApi.replaceProcedureSqlSchema(sql,map,"schema1");
        System.out.println(222);
    }
    
    public void test2(){
        String sql = "create or replace procedure sysdba.pro_immediate(id int,name varchar,a in int, b in int) as \n" +
                "begin\n" +
                "select * from sysdba.test_1 where sysdba.test_1.id=1;\n" +
                "select * from sysdba.test_1 inner join sysdba.test_2 on sysdba.test_1.id=sysdba.test_2.tid where sysdba.test_1.id=1\n" +
                "update sysdba.test_1 set sysdba.test_1.id=1 and sysdba.test_1.name='jkl' where sysdba.test_1.name='roger';\n" +
                "insert into sysdba.test_1(id,name)values(1,'roger');\n" +
                "delete from sysdba.test_1 where id=1;\n" +
                "execute sysdba.dep_base_proc_1(a,b);\n" +
                "call sysdba.dep_base_proc_1(a,b);\n" +
                "execute immediate 'create schema sc';\n" +
                "execute immediate 'create table sc.tb1(id int,name varhcar)';\n" +
                "execute immediate 'create table sc.students(id int,name varhcar)';\n" +
                "execute immediate 'create index sc.idx1 on sc.tb1(id)';\n" +
                "execute immediate 'create view sc.view1 as select * from sc.tb1';\n" +
                "execute immediate 'create synonym sc.stu for sc.students';\n" +
                "execute immediate 'truncate table sc.tb1';\n" +
                "execute immediate 'drop table sc.tb1';\n" +
                "end;";
        HashMap<String,String> map = new HashMap<>();
        map.put("schema1","schema2");
        map.put("sysdba","sysdba1");
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String str = XuguParserApi.replaceProcedureSqlSchema(sql,map,"sysdba");
        System.out.println(222);
    }

    public void test3(){
        String sql = "create or replace procedure pro_immediate(id int,name varchar,a in int, b in int) as \n" +
                "begin\n" +
                "select * from sysdba.test_1 where sysdba.test_1.id=1;\n" +
                "select * from sysdba.test_1 inner join sysdba.test_2 on sysdba.test_1.id=sysdba.test_2.tid where sysdba.test_1.id=1\n" +
                "update sysdba.test_1 set sysdba.test_1.id=1 and sysdba.test_1.name='jkl' where sysdba.test_1.name='roger';\n" +
                "insert into sysdba.test_1(id,name)values(1,'roger');\n" +
                "delete from sysdba.test_1 where id=1;\n" +
                "execute sysdba.dep_base_proc_1(a,b);\n" +
                "call sysdba.dep_base_proc_1(a,b);\n" +
                "execute immediate 'create schema sc';\n" +
                "execute immediate 'create table sc.tb1(id int,name varhcar)';\n" +
                "execute immediate 'create table sc.students(id int,name varhcar)';\n" +
                "execute immediate 'create index sc.idx1 on sc.tb1(id)';\n" +
                "execute immediate 'create view sc.view1 as select * from sc.tb1';\n" +
                "execute immediate 'create synonym sc.stu for sc.students';\n" +
                "execute immediate 'truncate table sc.tb1';\n" +
                "execute immediate 'drop table sc.tb1';\n" +
                "end;";
        HashMap<String,String> map = new HashMap<>();
        map.put("schema1","schema2");
        map.put("sysdba","sysdba1");
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String str = XuguParserApi.replaceProcedureSqlSchema(sql,map,"schema1");
        System.out.println(222);
    }
    
    public void test4(){
        String sql = "create or replace procedure test_procedure(a out int)\n" +
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
                "call sysdba.dep_base_proc_1(a,b);\n" +
                "execute immediate 'create schema sc';\n" +
                "execute sysdba.dep_base_proc_1(a,b);\n" +
                "exec sysdba.dep_base_proc_1(a,b);\n" +
                "                end;";
        Map<String,String> map = new CaseInsensitiveMap<>();
        map.put("qwe","sysdba1");
        map.put("syssso","syssso1");
        map.put("guest","guest1");
        String resourceSchema="qwe";
        XuguStatementParser parser= new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String returnStr = XuguParserApi.replaceProcedureSqlSchema(sql,map,resourceSchema);
        System.out.println(returnStr);
    }
}
