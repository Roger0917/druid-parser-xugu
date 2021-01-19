/*
 * Copyright 1999-2017 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.bvt.sql.oracle.create;

import com.alibaba.druid.sql.OracleTest;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class OracleCreateProcedureTest1 extends OracleTest {

    public void test_0() throws Exception {
        StringBuilder builder = new StringBuilder();
        List<SQLCreateProcedureStatement> createProcedureStatements = new ArrayList<>();
        String sql = "CREATE OR REPLACE PROCEDURE p (" +
        		"  dept_no varchar" +
        		") AS " +
        		"BEGIN" +
        		"  DELETE FROM dept_temp" +
        		"  WHERE department_id = dept_no;" +
        		" " +
        		"  IF SQL%FOUND THEN" +
        		"    DBMS_OUTPUT.PUT_LINE (" +
        		"      'Delete succeeded for department number ' || dept_no" +
        		"    );" +
        		"  ELSE" +
        		"    DBMS_OUTPUT.PUT_LINE ('No department number ' || dept_no);" +
        		"  END IF;" +
        		"END;" +
        		"/" +
        		"BEGIN" +
        		"  p(270);" +
        		"  p(400);" +
        		"END;"; //
        String sql2= "CREATE OR REPLACE PROCEDURE p (" +
                "  dept_no varchar(20)" +
                ") AS " +
                "BEGIN" +
                "  DELETE FROM dept_temp" +
                "  WHERE department_id = dept_no;" +
                " " +
                "  IF SQL%FOUND THEN" +
                "    DBMS_OUTPUT.PUT_LINE (" +
                "      'Delete succeeded for department number ' || dept_no" +
                "    );" +
                "  ELSE" +
                "    DBMS_OUTPUT.PUT_LINE ('No department number ' || dept_no);" +
                "  END IF;" +
                "END;" +
                "/" +
                "BEGIN" +
                "  p(270);" +
                "  p(400);" +
                "END;"; //

        String sql3 = "create or replace procedure pro1 (x in int default 5,y out varchar(20) default 'abc',z in numeric(20,10) default 5)as\n" +
                "                begin\n" +
                "                insert into student(id,name)values(x,y);\n" +
                "                end;";
        String sql4 = "create or replace function fun1(x in int default 5,y out varchar(20) default 'abc',z in numeric(20,10) default 5) return int as\n" +
                "begin \n" +
                "insert into student(id,name)values(x,y);\n" +
                "return 5;\n" +
                "end";
        //builder.append(sql);
        //builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);

        XuguStatementParser parser = new XuguStatementParser(sql4);
        List<SQLStatement> statementList = parser.parseStatementList();
        print(statementList);

        //Assert.assertEquals(3, statementList.size());

        XuguSchemaStatVisitor visitor = new XuguSchemaStatVisitor();
        for (SQLStatement statement : statementList) {
            if(statement instanceof SQLCreateProcedureStatement){
                createProcedureStatements.add((SQLCreateProcedureStatement) statement);
            }
            statement.accept(visitor);
        }
        String param = createProcedureStatements.get(0).getParameters().toString();

        System.out.println("Tables : " + visitor.getTables());
        System.out.println("fields : " + visitor.getColumns());
        System.out.println("coditions : " + visitor.getConditions());
        System.out.println("relationships : " + visitor.getRelationships());
        System.out.println("orderBy : " + visitor.getOrderByColumns());

        Assert.assertEquals(1, visitor.getTables().size());

        //Assert.assertTrue(visitor.getTables().containsKey(new TableStat.Name("dept_temp")));

//        Assert.assertEquals(7, visitor.getColumns().size());
        //Assert.assertEquals(1, visitor.getConditions().size());
        Assert.assertEquals(0, visitor.getRelationships().size());

        System.out.printf("参数列表");
        //System.out.println(createProcedureStatements.get(0x).getParameters());
        // Assert.assertTrue(visitor.getColumns().contains(new TableStat.Column("employees", "salary")));
    }
}
