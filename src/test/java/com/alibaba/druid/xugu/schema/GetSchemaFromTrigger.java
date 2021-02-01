package com.alibaba.druid.xugu.schema;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class GetSchemaFromTrigger extends TestCase {

    public void test(){
        String sql = "CREATE TRIGGER sysdba.INSERT_err BEFORE update of(sysdba.test_1.id,sysdba.test_1.name) ON sysdba.test_1 \n" +
                "referencing new AS nn FOR each ROW \n" +
                "BEGIN \n" +
                "IF nn.id>1000 \n" +
                "THEN INSERT INTO sysdba.test_1 VALUES(1,'插入了一个过大的数！'); \n" +
                "ELSE IF nn.id<10 \n" +
                "THEN INSERT INTO sysdba.test_1 VALUES (2,'插入了一个过小的数！'); \n" +
                "END IF;\n" +
                "end if;\n" +
                "end;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList =  parser.parseStatementList();
        HashMap<String,String> map = new HashMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","uu1");

        String returnStr = XuguParserApi.replaceTriggerSchema(sql,map);
        System.out.println(222);
    }

    public void test2(){
        String sql = "CREATE TRIGGER sysdba.INSERT_err BEFORE update of(sysdba.test_1.id,sysdba.test_1.name) ON sysdba.test_1 \n" +
                "referencing new AS nn FOR each ROW \n" +
                "BEGIN \n" +
                "insert into uu.test1(1,'插入了一个过大的数！'); \n" +
                "update uu.test1 set sysdba.test1.id=2 where sysdba.test1.name='roger'; \n"+
                "delete from uu.test1 where sysdba.test1.name='roger2'; \n" +
                "end;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList =  parser.parseStatementList();
        System.out.println(222);
        HashMap<String,String> map = new HashMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","uu1");

        String returnStr = XuguParserApi.replaceTriggerSchema(sql,map);
        System.out.println(222);
    }
}
