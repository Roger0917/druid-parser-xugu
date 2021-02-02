package com.alibaba.druid.xugu.ddl;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCallStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateExecuteBean;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguExecuteImmediateStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-09 18:25
 **/

public class Execute extends TestCase {

    public void test() {
        List<XuguExecuteImmediateStatement> executeImmediateStatementList = new ArrayList<>();
        String sql = "execute xugu_test_pro(id => 5,name=>'ggg',address => 'vvv');";
        String sql2 = "execute xugu_test_pro(:1,:2);";
        String sql3 = "execute xugu_test_pro(:id,:name);";
        String sql4 = "execute sysdba.dep_base_proc_1(a,b);\n";

        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        //SQLStatement statement = statementList.get(0);
        String text = Base.print(statementList);

        List<CreateExecuteBean> executeBeanList = XuguParserApi.parseExecute(builder.toString());
        System.out.println(222);
    }
}
