package com.alibaba.druid.xugu.ddl;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCallStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import com.mysql.cj.xdevapi.SqlStatement;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-09 14:34
 **/

public class Call extends TestCase {

    public void test(){
            List<SQLCallStatement> callStatementList = new ArrayList<>();
            String sql = "call xugu_test_pro(id => 5,name=>'ggg',address => 'vvv');";
            String sql2 = "call xugu_test_pro(:1,:2)";
            String sql3 = "call xugu_test_pro(:id,:name)";
            String sql4 = "call xugu_test_return_fun(id => 5,name=>'ggg',address => 'vvv');";

            StringBuilder builder = new StringBuilder();
            builder.append(sql);
            builder.append(sql2);
            builder.append(sql3);
            builder.append(sql4);
            XuguStatementParser parser = new XuguStatementParser(builder.toString());
            List<SQLStatement> statementList = parser.parseStatementList();
            //SQLStatement statement = statementList.get(0);
            String text = Base.print(statementList);
            System.out.println(statementList.size());
            System.out.println(text);

            for(SQLStatement sqlStatement:statementList){
                    SQLCallStatement sqlCallStatement = (SQLCallStatement) sqlStatement;
                    callStatementList.add(sqlCallStatement);
            }

            for (SQLCallStatement sqlCallStatement:callStatementList){
                    System.out.println(sqlCallStatement.getProcedureName());
                    System.out.println(sqlCallStatement.getParameters());
                    /*System.out.println(sqlCallStatement.getChildren());
                    System.out.println(sqlCallStatement.getOutParameter());*/
            }


           // System.out.println(Base.judgmentDdlOrDmlOrDcl(statementList));
        }
}
