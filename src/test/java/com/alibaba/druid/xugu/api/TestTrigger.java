package com.alibaba.druid.xugu.api;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateTriggerBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import com.alibaba.druid.xugu.trigger.CreateTrigger;
import junit.framework.TestCase;

public class TestTrigger extends TestCase {
    
    public void test() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE TRIGGER trig_user BEFORE INSERT ON text_table_trigger REFERENCING new AS nn\n" +
                "FOR each ROW\n" +
                "BEGIN\n" +
                "IF nn.id>1000 THEN\n" +
                "INSERT INTO text_table_trigger1 VALUES(nn.id,'插 入 了 一 个 过 大 的 数！ ');\n" +
                "ELSE IF nn.id<10 THEN\n" +
                "INSERT INTO text_table_trigger1 VALUES (nn.id,'插 入 了 一 个 过 小 的 数！ ');\n" +
                "END IF;\n" +
                "END IF;\n" +
                "END;";
        CreateTriggerBean bean = XuguParserApi.parseCreateTrigger(sql);
        System.out.println(222);
    }
}
