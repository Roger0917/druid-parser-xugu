package com.alibaba.druid.xugu.view;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTriggerStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateViewStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class CreateView extends TestCase {

    public void test(){
        List<SQLCreateViewStatement> createViewStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        String sql = "create view view_cm_config as select * from cm_config;";
        String sql2 = "create view SYSDBA.view_cm_role as select * from cm_role;";
        String sql3 = "create view u1.v1 as select * from u2.t1 left join u1.t1 on XXXX";
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);

        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        String text = Base.print(statementList);

        System.out.println("Trigger statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            SQLCreateViewStatement createViewStatement = (SQLCreateViewStatement) statement;
            createViewStatementList.add(createViewStatement);
        }
        System.out.println("提取视图中的变量参数");

        for(SQLCreateViewStatement createViewStatement:createViewStatementList){
            System.out.println(createViewStatement.getSchema());
            System.out.println(createViewStatement.getTableSource());
            System.out.println(createViewStatement.getName());
        }

    }
}
