package com.alibaba.druid.xugu.procedure;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import junit.framework.TestCase;

public class XuguSysProcedure extends TestCase {

    public void test(){
        String sql = "create or replace procedure test_proc(names in varchar,outStr out varchar)\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"as \\n\" +\n" +
                "        \"begin\\n\" +\n" +
                "        \"select password into outstr  from USERT where name = names;\\n\" +\n" +
                "        \" end;";

        CreateProcedureBean createProcedureBean = XuguParserApi.parseCreateProcedure(sql);
        System.out.println(222);
    }

}
