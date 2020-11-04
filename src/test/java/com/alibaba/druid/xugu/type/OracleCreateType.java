package com.alibaba.druid.xugu.type;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTriggerStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTypeStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-04 10:47
 **/

public class OracleCreateType extends TestCase {

    public void test(){
        List<OracleCreateTypeStatement> createTypeStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "CREATE OR REPLACE TYPE person_typ2 AS OBJECT(\n" +
                "     name VARCHAR(10),gender VARCHAR(2),\n" +
                "     birthdate DATE,address VARCHAR(100),\n" +
                "     MEMBER PROCEDURE change_address(new_addr VARCHAR(20)),\n" +
                "     MEMBER FUNCTION get_info RETURN VARCHAR(20)\n" +
                ");"+
                "CREATE OR REPLACE TYPE BODY person_typ2 IS\n" +
                "       MEMBER PROCEDURE change_address(new_addr VARCHAR(20))\n" +
                "       IS\n" +
                "       BEGIN\n" +
                "           address:=new_addr;\n" +
                "      END;\n" +
                "      MEMBER FUNCTION get_info RETURN VARCHAR(20)\n" +
                "      IS\n" +
                "           v_info VARCHAR(100);\n" +
                "      BEGIN\n" +
                "           v_info := '姓名：'||name||',出生日期：'||birthdate;\n" +
                "           RETURN v_info;\n" +
                "      END;\n" +
                "END;";
        //builder.append(sql);
        //builder.append(sql2);

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(statementList.size());
        //String text = Base.oraclePrint(statementList);

        for (SQLStatement statement : statementList) {
            OracleCreateTypeStatement createTypeStatement = (OracleCreateTypeStatement) statement;
            createTypeStatementList.add(createTypeStatement);
        }
        for(OracleCreateTypeStatement createTypeStatement:createTypeStatementList){
            System.out.println("name: "+createTypeStatement.getName());
            System.out.println("oid: "+createTypeStatement.getOid());
            System.out.println("authid: "+createTypeStatement.getAuthId());
            System.out.println("final: "+createTypeStatement.getFinal());
            System.out.println("Instantiable: "+createTypeStatement.getInstantiable());
            System.out.println("tableof: "+createTypeStatement.getTableOf());
            System.out.println("under: "+createTypeStatement.getUnder());
            System.out.println("VarrayDataType: "+createTypeStatement.getVarrayDataType());
            System.out.println("VarraySizeLimit: "+createTypeStatement.getVarraySizeLimit());
            System.out.println("WrappedSource: "+createTypeStatement.getWrappedSource());
            System.out.println("Parameters: "+createTypeStatement.getParameters());

        }
    }
}
