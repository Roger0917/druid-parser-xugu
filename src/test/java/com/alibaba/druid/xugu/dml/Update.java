package com.alibaba.druid.xugu.dml;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-05 13:44
 **/

public class Update extends TestCase {

    public void test(){
            String sql = "update kkk set name='hkkk' where id=1 and bithday like'%2020-11-05%' and money=200;";

            StringBuilder builder = new StringBuilder();
            builder.append(sql);
            XuguStatementParser parser = new XuguStatementParser(builder.toString());
            List<SQLStatement> statementList = parser.parseStatementList();
            SQLStatement statement = statementList.get(0);
            String text = Base.print(statementList);

            System.out.println(statementList.size());
            System.out.println(text);

            System.out.println(Base.judgmentDdlOrDmlOrDcl(statementList));
        }
}

