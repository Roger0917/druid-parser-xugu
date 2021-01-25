package com.alibaba.druid.xugu.schema;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLBlockStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class GetSchemaTest extends TestCase {

    public void testFromPro (){
        //Map<Integer,String> schemaMap = new HashMap<>();
        List<String> schemaStr = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "create or replace procedure proc1 as\n" +
                "begin\n" +
                "insert into students(sid,sname,t_id)values(1,'roger',1);\n" +
                "create index idx1 on sysdba.students(sname); \n" +
                "update sysdba.students a set a.sname='roger2' where sid=1;\n" +
                "select * from sysdba.students order by sysdba.students.sid;\n" +
                "delete from sysdba.students where t_id=1;\n" +
                "end;";
        builder.append(sql);
        SQLCreateProcedureStatement head = null;
        SQLBlockStatement body = null;
        XuguStatementParser parser = new XuguStatementParser(sql.toString());
        List<SQLStatement> statementList =  parser.parseStatementList();
        ExportSchemaVisitor visitor = new ExportSchemaVisitor();
        statementList.get(0).accept(visitor);
        for (SQLStatement statement:statementList){
            if(statement instanceof SQLCreateProcedureStatement){
                head = (SQLCreateProcedureStatement) statement;
            }else{
                body = (SQLBlockStatement) statement;
            }
        }
        if(head.getName() instanceof SQLIdentifierExpr){

        }else if(head.getName() instanceof SQLPropertyExpr){
            SQLPropertyExpr propertyExpr = (SQLPropertyExpr) head.getName();
            propertyExpr.getOwnerName();
            schemaStr.add(propertyExpr.getOwnerName());
        }
        List<SQLStatement> blockStatementList1 = body.getStatementList();
        for(SQLStatement statement:blockStatementList1){

        }
    }
}
