package com.alibaba.druid.xugu.api;

import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import junit.framework.TestCase;

import java.util.Map;

/**
 * @Author: Roger
 * @Date: 2021/2/4 14:09
 */
public class TestFunction extends TestCase {
    
    public void test(){
        String sql = "create or replace function sysdba.dep_proc_func(id int,name out varchar,address inout varchar,email in int) return int as\n" +
                " begin\n" +
                "   execute dep_base_proc_1(a,b);\n" +
                "   call dep_base_proc_1(a,b);\n" +
                " end;";
        Map<String,String> map = new CaseInsensitiveMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","ss");
        double d = StrUtil.similar(sql,XuguParserApi.replaceFunctionSqlSchema(sql,map,"uu"));
        System.out.println(d);
    }
}
