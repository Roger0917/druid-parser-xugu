package com.alibaba.druid.xugu.schema;

import com.alibaba.druid.pool.bonecp.TestPSCache;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class GetSchemaFromProcedure extends TestCase {
    
    public void test(){
        String sql = "create or replace procedure sysdba.procedure1(a int,b varchar) as\n" +
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
        String returnStr = XuguParserApi.replaceProcedureSqlSchema(sql,map);
        System.out.println(222);
    }
}
