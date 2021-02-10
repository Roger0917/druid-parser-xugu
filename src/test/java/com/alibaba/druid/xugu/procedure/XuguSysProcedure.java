package com.alibaba.druid.xugu.procedure;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import junit.framework.TestCase;

import java.util.List;

public class XuguSysProcedure extends TestCase {

    public void test() throws ParserBusinessException {
        String sql = "create or replace procedure test_proc(names in varchar,outStr out varchar)\\n\" +\n" +
                "        \"\\n\" +\n" +
                "        \"as \\n\" +\n" +
                "        \"begin\\n\" +\n" +
                "        \"select password into outstr  from USERT where name = names;\\n\" +\n" +
                "        \" end;";

        List<CreateProcedureBean> createProcedureBeanList = XuguParserApi.parseCreateProcedure(sql);
        System.out.println(222);
    }

}
