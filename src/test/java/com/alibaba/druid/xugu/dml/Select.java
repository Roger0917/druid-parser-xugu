package com.alibaba.druid.xugu.dml;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import com.mysql.cj.xdevapi.SqlStatement;
import junit.framework.TestCase;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-05 13:45
 **/

public class Select extends TestCase {

    public void test(){
        String sql = "select id,name,bithday from kkk where money=200;";
        String sql2 = "select k.id,k.name,k.bithday,k.money,y.id,y.name from kkk k inner join yyy as y on k.id=y.id;";
        String sql3 = "select k.id,k.name,k.bithday,k.money,y.id,y.name from kkk k join yyy as y on k.id=y.id;";
        String sql4 = "select k.id,k.name,k.bithday,k.money,y.id,y.name from kkk k left join yyy as y on k.id=y.id;";
        String sql5 = "select k.id,k.name,k.bithday,k.money,y.id,y.name from kkk k right join yyy as y on k.id=y.id;";
        String sql6 = "select k.id,k.name,k.bithday,k.money,y.id,y.name from kkk k full join yyy as y on k.id=y.id;";
        String sql7 = "select name,bithday from kkk where money=200 and id in(select id from yyy limit 0,1) limit 0,5;";

        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        builder.append(sql5);
        builder.append(sql6);
        builder.append(sql7);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statement = statementList.get(0);
        String text = Base.print(statementList);

        for (SQLStatement sqlStatement:statementList){
            SQLSelectStatement selectStatement = (SQLSelectStatement) sqlStatement;
            selectStatement.getSelect();
        }
        System.out.println(statementList.size());
        System.out.println(text);

        System.out.println(Base.judgmentDdlOrDmlOrDcl(statementList));
    }
}
