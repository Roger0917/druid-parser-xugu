package com.alibaba.druid.xugu;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguOutputVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-29 09:25
 **/

public class Base {

    public static String output(List<SQLStatement> stmtList) {
        StringBuilder out = new StringBuilder();
        XuguOutputVisitor visitor = new XuguOutputVisitor(out);

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }

        return out.toString();
    }

    public static String oracleOutput(List<SQLStatement> stmtList) {
        StringBuilder out = new StringBuilder();
        OracleOutputVisitor visitor = new OracleOutputVisitor(out);

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }

        return out.toString();
    }

    public static String print(List<SQLStatement> stmtList) {
        String text = output(stmtList);
        String outputProperty = System.getProperty("druid.output");
        if ("false".equals(outputProperty)) {
            return "error print statementList";
        }
        return text;
    }

    public static String oraclePrint(List<SQLStatement> stmtList) {
        String text = oracleOutput(stmtList);
        String outputProperty = System.getProperty("druid.output");
        if ("false".equals(outputProperty)) {
            return "error print statementList";
        }
        return text;
    }

    public static List<String> judgmentDdlOrDmlOrDcl(List<SQLStatement> list){
        List<String> statementTypeList  = new ArrayList<>();
        for(SQLStatement sqlStatement:list){
            StringBuilder typeBuilder=new StringBuilder();
            if (sqlStatement instanceof SQLDDLStatement) {
                typeBuilder.append("DDL");
                if(sqlStatement instanceof SQLAlterStatement){
                    typeBuilder.append(" Alter");
                }
                if (sqlStatement instanceof SQLCreateStatement) {
                    typeBuilder.append(" Create");
                } else if (sqlStatement instanceof SQLDropStatement) {
                    typeBuilder.append(" Drop");
                }
            }
            else if(sqlStatement instanceof SQLAlterStatement){
                typeBuilder.append("DDL Alter");
            }else if(sqlStatement instanceof SQLTruncateStatement){
                typeBuilder.append("DDL Truncate") ;
            } else if(sqlStatement instanceof SQLPrivilegeStatement){
                typeBuilder.append("DCL");
            }else if(sqlStatement instanceof SQLInsertStatement){
                typeBuilder.append("DML Insert");
            }else if(sqlStatement instanceof SQLUpdateStatement){
                typeBuilder.append("DML UPdate");
            }else if(sqlStatement instanceof SQLSelectStatement){
                typeBuilder.append("DML Select");
            }else if(sqlStatement instanceof SQLDeleteStatement){
                typeBuilder.append("DML Delete");
            }
            statementTypeList.add(typeBuilder.toString());
        }
        return statementTypeList;
    }
}
