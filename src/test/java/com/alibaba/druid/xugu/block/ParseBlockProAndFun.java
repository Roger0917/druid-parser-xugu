package com.alibaba.druid.xugu.block;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import com.alibaba.druid.xugu.function.CreateFunction;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ParseBlockProAndFun extends TestCase {

    public void test() {
        /*String sql = "begin\n" +
                "pro1(3,'dd');\n" +
                "end;";*/

        String sql2 = "declare\n" +
                "x int;\n" +
                "y varchar;\n" +
                "begin\n" +
                "x:=3;\n" +
                "y:='dd';\n" +
                "pro1(id => x,name => y);\n" +
                "end;";

        String sql3 = "declare\n" +
                "x int;\n" +
                "y varchar;\n" +
                "begin\n" +
                "x:=3;\n" +
                "y:='dd';\n" +
                "pro1(x,y);\n" +
                "end;";
        String sql4 = "begin\n" +
                "proc1(?,?);\n" +
                "end";
        String sql5 = "begin\n" +
                "proc1(:id,:name);\n" +
                "end";

        List<SQLBlockStatement> blockStatementList = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        //builder.append(sql);
        builder.append(sql4);
        builder.append(sql5);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        //SQLStatement statement = statementList.get(0);
        String text = Base.print(statementList);
        System.out.println(statementList.size());
        System.out.println(text);

        for (SQLStatement sqlStatement : statementList) {
            SQLBlockStatement sqlBlockStatement = (SQLBlockStatement) sqlStatement;
            blockStatementList.add(sqlBlockStatement);
        }

       for (SQLBlockStatement sqlBlockStatement : blockStatementList) {
            System.out.println(sqlBlockStatement.getParameters());
            System.out.println(sqlBlockStatement.getLabelName());
           System.out.println(sqlBlockStatement.getEndLabel());
           System.out.println(sqlBlockStatement.getStatementList());
           for(SQLStatement sqlStatement:sqlBlockStatement.getStatementList()){
               if(sqlStatement instanceof SQLExprStatement){
                   SQLExprStatement exprStatement = (SQLExprStatement) sqlStatement;
                   if(exprStatement.getExpr() instanceof SQLMethodInvokeExpr){
                       SQLMethodInvokeExpr sqlMethodInvokeExpr = (SQLMethodInvokeExpr) exprStatement.getExpr();
                       System.out.println("方法名: "+sqlMethodInvokeExpr.getMethodName());
                       for(SQLExpr expr:sqlMethodInvokeExpr.getArguments()){
                           if(expr.toString().contains("=>")){
                               System.out.println("參數名: "+expr.toString().substring(0,expr.toString().indexOf("=>")));
                           }else{
                               System.out.println("參數名: "+expr.toString());
                           }
                       }
                   }

               }
           }
        }


        // System.out.println(Base.judgmentDdlOrDmlOrDcl(statementList));
    }
}

