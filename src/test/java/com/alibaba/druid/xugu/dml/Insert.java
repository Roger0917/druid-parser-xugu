package com.alibaba.druid.xugu.dml;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguOutputVisitor;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-05 13:44
 **/

public class Insert extends TestCase {
    //不支持insert all into
    public void test(){
        String sql = "INSERT INTO Websites (name, country)\n" +
                "SELECT app_name, country FROM apps\n" +
                "WHERE id=1;";
        String sql2 = "INSERT INTO ttt(id) SELECT * FROM vvv;";
        String sql3 = "insert into ttt(id,name)values(4,'roger4');";
        String sql4 = "insert into ttt(id,name)values(1,'roger') (2,'roger2')   (3,'roger3');";

        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        SQLStatement statement = statementList.get(0);
        String text = Base.print(statementList);

        System.out.println(statementList.size());
        System.out.println(text);

        System.out.println(Base.judgmentDdlOrDmlOrDcl(statementList));
    }
}
