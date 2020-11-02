package com.alibaba.druid.xugu.trigger;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTriggerStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.xugu.Base;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-02 17:37
 **/

public class OracleCreateTrigger extends TestCase {

    public void test(){
        List<SQLCreateTriggerStatement> createTriggerStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = /*"CREATE OR REPLACE TRIGGER trig_test\n" +
                "AFTER UPDATE OF ID\n" +
                "ON test_trig_tab\n" +
                "FOR EACH ROW WHEN (OLD.ID>=10)\n" +
                "DECLARE num INTEGER;\n" +
                "BEGIN INSERT INTO test_trig_tab2 VALUES(:NEW.ID,'trig_update');\n" +
                "END;\n";*/
                "CREATE OR REPLACE TRIGGER trig_test3\n" +
                        "AFTER INSERT OR DELETE OR UPDATE OF id,NANE ON test_trig_tab\n" +
                        "FOR each ROW WHEN (OLD.ID>=10) " +
                        "BEGIN\n" +
                        "IF updating THEN INSERT INTO test_trig_tab2 VALUES(:NEW.id,'update tab');\n" +
                        "END IF;\n" +
                        "IF inserting THEN\n" +
                        "INSERT INTO test_trig_tab2 VALUES(:NEW.id,'hh');\n" +
                        "END IF;\n" +
                        "END;";
        //builder.append(sql);
        //builder.append(sql2);

        OracleStatementParser parser = new OracleStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String text = Base.oraclePrint(statementList);

        //System.out.println("Trigger statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            SQLCreateTriggerStatement sqlCreateTriggerStatement = (SQLCreateTriggerStatement) statement;
            createTriggerStatementList.add(sqlCreateTriggerStatement);
        }

        String sql3 = "BEGIN\n" +
                "\tIF updating THEN\n" +
                "\t\tINSERT INTO test_trig_tab2\n" +
                "\t\tVALUES (:NEW.id, 'update tab');\n" +
                "\tEND IF;\n" +
                "\tIF inserting THEN\n" +
                "\t\tINSERT INTO test_trig_tab2\n" +
                "\t\tVALUES (:NEW.id, 'hh');\n" +
                "\tEND IF;\n" +
                "END;";
        String sql4 = "BEGIN\n" +
                "IF updating THEN INSERT INTO test_trig_tab2 VALUES(:NEW.id,'update tab');\n" +
                "END IF;\n" +
                "IF inserting THEN\n" +
                "INSERT INTO test_trig_tab2 VALUES(:NEW.id,'hh');\n" +
                "END IF;\n" +
                "END;";
        //System.out.println(sql3.replaceAll("\\s*", "").equals(sql4.replaceAll("\\s*", "")));
        for(SQLCreateTriggerStatement sqlCreateTriggerStatement:createTriggerStatementList){
            /*System.out.println("触发器名: "+sqlCreateTriggerStatement.getName());
            System.out.println("作用的表: "+sqlCreateTriggerStatement.getOn());
            System.out.println("触发时机: "+sqlCreateTriggerStatement.getTriggerType());
            System.out.println("更新的列: "+sqlCreateTriggerStatement.getUpdateOfColumns());
            System.out.println("条件: " +sqlCreateTriggerStatement.getWhen());


            System.out.println(sqlCreateTriggerStatement.getBody().toString());
            System.out.println(text.substring(text.indexOf("BEGIN")));
            System.out.println(text.substring(text.indexOf("BEGIN")).equals(sqlCreateTriggerStatement.getBody().toString()));*/
            System.out.println("text-------");
            System.out.println(text);
            System.out.println("body-------");
            System.out.println(sqlCreateTriggerStatement.getBody());
            System.out.println("text-body");
            System.out.println(text.substring(0,text.indexOf(sqlCreateTriggerStatement.getBody().toString())));

        }
    }
}
