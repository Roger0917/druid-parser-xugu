package com.alibaba.druid.xugu.api;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateViewBean;
import junit.framework.TestCase;

import java.util.List;

public class TestView extends TestCase {
    
    public void test(){
        String sql = "create view sysdba.temp_v1 as select id,name from user_sod.temp_tab1";
        List<CreateViewBean> createViewBeanList = XuguParserApi.parseCreateView(sql);
        System.out.println(222);
    }


}
