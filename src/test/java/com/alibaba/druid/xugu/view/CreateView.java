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
        String sql3 = "create view sysdba.v1 as select * from sysdba.teacher a left join user_sod.student b on a.id=b.tid;";
        String sql4 = "create view sysdba.v1 as select * from sysdba.teacher a inner join user_sod.student b on a.id=b.tid;";
        String sql5 = "create view sysdba.vv as select s.sname,c.cid,c.cname,t.tname\n" +
                "    from sysdba.students s,user_sod2.course c,user_sod.teacher t\n" +
                "    where s.courseid = c.cid and c.tid = t.tid;";
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        builder.append(sql5);

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
