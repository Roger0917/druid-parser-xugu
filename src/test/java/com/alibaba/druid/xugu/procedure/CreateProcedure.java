package com.alibaba.druid.xugu.procedure;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleExportParameterVisitor;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-29 09:18
 **/

public class CreateProcedure extends TestCase {

    public void test(){
        List<SQLCreateProcedureStatement> createProcedureStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "create or replace procedure xugu_test_pro(id in int,name in varchar,address out varchar)as\n" +
                "begin\n" +
                "create table xugu_parser_table(id int,name varchar);\n" +
                "alter table xugu_parser_table add constraint con1 primary key(id);\n" +
                "insert into xugu_parser_table(id,name)values(1,'aaa');\n" +
                "update xugu_parser_table set name='bbb' where id=1;\n" +
                "delete from xugu_parser_table where id=1;\n" +
                "end;" +
                "create or replace procedure xugu_test_pro(id in int,name in varchar,address out varchar)as\\n\" +\n" +
                "                \"begin\\n\" +\n" +
                "                \"create table xugu_parser_table(id int,name varchar);\\n\" +\n" +
                "                \"alter table xugu_parser_table add constraint con1 primary key(id);\\n\" +\n" +
                "                \"insert into xugu_parser_table(id,name)values(1,'aaa');\\n\" +\n" +
                "                \"update xugu_parser_table set name='bbb' where id=1;\\n\" +\n" +
                "                \"delete from xugu_parser_table where id=1;\\n\" +\n" +
                "                \"end;";
        //String sql2 = "create or replace procedure xugu_test_pro(id in int,name in varchar,address out varchar)";
        builder.append(sql.substring(0,sql.indexOf(")")+1));
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        Base.print(statementList);

        System.out.println("Procedure statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            SQLCreateProcedureStatement sqlCreateProcedureStatement = (SQLCreateProcedureStatement) statement;
            createProcedureStatementList.add(sqlCreateProcedureStatement);
        }

        System.out.println("提取存储过程中的变量参数");
        for(SQLCreateProcedureStatement sqlCreateProcedureStatement:createProcedureStatementList){
            System.out.println("存储过程名 "+sqlCreateProcedureStatement.getName());
            System.out.println("参数个数: "+sqlCreateProcedureStatement.getParameters().size());
            for(int i=0;i<sqlCreateProcedureStatement.getParameters().size();i++){
                SQLParameter parameter = sqlCreateProcedureStatement.getParameters().get(i);
                SQLParameter.ParameterType type = parameter.getParamType();
                SQLExpr expr = parameter.getDefaultValue();
                if(type!=null&&expr!=null){
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"参数类型: "+parameter.getParamType()+" "+"数据类型: "+parameter.getDataType().getName()+"参数默认值: "+parameter.getDefaultValue()+" 参数下标: "+i);
                }else if(type!=null&&expr==null){
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"参数类型: "+parameter.getParamType()+" "+"数据类型: "+parameter.getDataType().getName()+" 参数下标: "+i);
                }else if(type==null&expr!=null){
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"数据类型: "+parameter.getDataType().getName()+"参数默认值: "+parameter.getDefaultValue()+" 参数下标: "+i);
                }else{
                    System.out.println("参数名: "+parameter.getName().getSimpleName()+" "+"数据类型: "+parameter.getDataType().getName()+" 参数下标: "+i);
                }
            }
        }
    }
}
