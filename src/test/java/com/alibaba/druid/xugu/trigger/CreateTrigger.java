package com.alibaba.druid.xugu.trigger;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTriggerStatement;
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
 * @create: 2020-10-29 15:24
 **/

public class CreateTrigger extends TestCase {

    public void test(){
        List<SQLCreateTriggerStatement> createTriggerStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String sql = "CREATE OR REPLACE TRIGGER trig_test3 AFTER INSERT OR DELETE OR UPDATE OF id,NANE ON test_trig_tab\n" +
                "FOR each ROW WHEN (old.ID>=10) BEGIN\n" +
                "IF updating THEN INSERT INTO test_trig_tab2 VALUES(new.id,'update tab');\n" +
                "END IF;\n" +
                "IF inserting THEN INSERT INTO test_trig_tab2 VALUES(new.id,'update tab');\n" +
                "END IF;\n"+
                "IF deleting THEN\n" +
                "INSERT INTO test_trig_tab2 VALUES(new.id,'hh');\n" +
                "END IF;\n" +
                "END;";
        String sql2 = "CREATE OR REPLACE TRIGGER trig_test3 AFTER INSERT OR UPDATE OF id,NANE ON test_trig_tab\n" +
                "FOR statement WHEN (old.ID>=10) BEGIN\n" +
                "IF updating THEN INSERT INTO test_trig_tab2 VALUES(1,'update tab');\n" +
                "END IF;\n" +
                "IF inserting THEN INSERT INTO test_trig_tab2 VALUES(2,'update tab');\n" +
                "END IF;\n" +
                "IF deleting THEN\n" +
                "INSERT INTO test_trig_tab2 VALUES(3,'hh');\n" +
                "END IF;\n" +
                "END;";
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        Base.print(statementList);

        System.out.println("Trigger statement size: "+statementList.size());
        for (SQLStatement statement : statementList) {
            SQLCreateTriggerStatement sqlCreateTriggerStatement = (SQLCreateTriggerStatement) statement;
            createTriggerStatementList.add(sqlCreateTriggerStatement);
        }
        System.out.println("提取触发器中的变量参数");

        for(SQLCreateTriggerStatement sqlCreateTriggerStatement:createTriggerStatementList){
            System.out.println("触发器名: "+sqlCreateTriggerStatement.getName());
            System.out.println("触发器类型: "+sqlCreateTriggerStatement.isForEachRow());
           // System.out.println("作用的表: "+sqlCreateTriggerStatement.getOn());
            System.out.println("触发时机: "+sqlCreateTriggerStatement.getTriggerType()); //After,Before
            System.out.println("insert: "+sqlCreateTriggerStatement.isInsert()+" update: "+sqlCreateTriggerStatement.isUpdate()+" delete: "+sqlCreateTriggerStatement.isDelete());
            /*String triggerCondition = "";
            if(sqlCreateTriggerStatement.isInsert()){
                triggerCondition="insert";
            }else if(sqlCreateTriggerStatement.isUpdate()){
                triggerCondition="update";
            }else if(sqlCreateTriggerStatement.isDelete()) {
                triggerCondition = "delete";
            }
            System.out.println("操作类型: "+triggerCondition);*/
            System.out.println("更新的列: "+sqlCreateTriggerStatement.getUpdateOfColumns());
            System.out.println("条件: " +sqlCreateTriggerStatement.getWhen());
            System.out.println("触发器body: "+sqlCreateTriggerStatement.getBody());
            System.out.println("定义: "+sqlCreateTriggerStatement.getDefiner());
        }
    }
}
