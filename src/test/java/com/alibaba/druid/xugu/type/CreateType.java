package com.alibaba.druid.xugu.type;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreateTypeStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-04 10:45
 **/

public class CreateType extends TestCase {

    public void test(){
        List<XuguCreateTypeStatement> createTypeStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "CREATE OR REPLACE TYPE person_typ2 AS OBJECT(\n" +
                "     name VARCHAR,gender VARCHAR,\n" +
                "     birthdate DATE,address VARCHAR,\n" +
                "     MEMBER PROCEDURE change_address(new_addr VARCHAR),\n" +
                "     MEMBER FUNCTION get_info(x int) RETURN VARCHAR\n" +
                ");"+
                "CREATE OR REPLACE TYPE BODY person_typ2 IS\n" +
                "       MEMBER PROCEDURE change_address(new_addr VARCHAR)\n" +
                "       IS\n" +
                "       BEGIN\n" +
                "           address:=new_addr;\n" +
                "      END;\n" +
                "      MEMBER FUNCTION get_info(x int) RETURN VARCHAR\n" +
                "      IS\n" +
                "           v_info VARCHAR;\n" +
                "      BEGIN\n" +
                "           v_info := '姓名：'||name||',出生日期：'||birthdate;\n" +
                "           RETURN v_info;\n" +
                "      END;\n" +
                "END;";

        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(statementList.size());
        //String text = Base.print(statementList);

        //System.out.println("Trigger statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            XuguCreateTypeStatement createTypeStatement = (XuguCreateTypeStatement) statement;
            createTypeStatementList.add(createTypeStatement);
        }
        for(XuguCreateTypeStatement createTypeStatement:createTypeStatementList){
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
