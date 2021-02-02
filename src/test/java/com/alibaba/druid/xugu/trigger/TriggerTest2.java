package com.alibaba.druid.xugu.trigger;

import com.alibaba.druid.pool.bonecp.TestPSCache;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.List;

public class TriggerTest2 extends TestCase {
    
    public void test(){
        String sql = "create trigger trig_user after update of (id,name,address) on text_table_trigger\n" +
                "for each row\n" +
                "begin\n" +
                "send_msg('abc');\n" +
                "end;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);

        System.out.println(XuguParserApi.parseCreateTrigger(sql));
    }
    
    public void test2(){
        String sql = "CREATE OR REPLACE TRIGGER trig_user AFTER INSERT OR UPDATE OF (id,name) ON text_table_trigger\n" +
                "FOR each ROW\n" +
                "BEGIN\n" +
                "IF updating THEN\n" +
                "INSERT INTO text_table_trigger1 VALUES(new.id,'update tab');\n" +
                "END IF;\n" +
                "IF inserting THEN\n" +
                "INSERT INTO text_table_trigger1 VALUES(new.id,'insert tab');\n" +
                "END IF;\n" +
                "END;";

        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);

        System.out.println(XuguParserApi.parseCreateTrigger(sql));
    }
    
    public void test3(){
        String sql = "CREATE OR REPLACE TRIGGER trig_user BEFORE INSERT ON text_table_trigger REFERENCING new AS nn\n" +
                "FOR each ROW\n" +
                "BEGIN\n" +
                "IF nn.id>1000 THEN\n" +
                "INSERT INTO sysdba.text_table_trigger1 VALUES(nn.id,'插 入 了 一 个 过 大 的 数！ ');\n" +
                "ELSE IF nn.id<10 THEN\n" +
                "INSERT INTO user_sod.text_table_trigger1 VALUES (nn.id,'插 入 了 一 个 过 小 的 数！ ');\n" +
                "END IF;\n" +
                "END IF;\n" +
                "END;\n";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);

        System.out.println(XuguParserApi.parseCreateTrigger(sql));
    }
}
