package com.alibaba.druid.xugu.ddl;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCallStatement;
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

    public void test(){
        List<XuguExecuteImmediateStatement> executeImmediateStatementList = new ArrayList<>();
        String sql = "execute xugu_test_pro(id => 5,name=>'ggg',address => 'vvv');";
        //String sql2 = "execute xugu_test_pro(:1,:2);";
        //String sql3 = "execute xugu_test_pro(:id,:name);";

        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        //builder.append(sql2);
        //builder.append(sql3);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        //SQLStatement statement = statementList.get(0);
        String text = Base.print(statementList);
        System.out.println(statementList.size());
        System.out.println(text);

        for(SQLStatement sqlStatement:statementList){
            XuguExecuteImmediateStatement executeImmediateStatement = (XuguExecuteImmediateStatement) sqlStatement;
            executeImmediateStatementList.add(executeImmediateStatement);
        }

        for (XuguExecuteImmediateStatement executeImmediateStatement:executeImmediateStatementList){
            System.out.println(executeImmediateStatement.getDynamicSql());
            System.out.println(executeImmediateStatement.getArguments());
            System.out.println(executeImmediateStatement.getInto());
            System.out.println(executeImmediateStatement.getReturnInto());
                    /*System.out.println(sqlCallStatement.getChildren());
                    System.out.println(sqlCallStatement.getOutParameter());*/
        }


        // System.out.println(Base.judgmentDdlOrDmlOrDcl(statementList));
    }
    }
