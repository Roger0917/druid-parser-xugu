package com.alibaba.druid.sql;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleOutputVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguOutputVisitor;
import junit.framework.TestCase;

import java.util.List;

public class XuguTest extends TestCase {

    protected String output(List<SQLStatement> stmtList) {
        StringBuilder out = new StringBuilder();
        XuguOutputVisitor visitor = new XuguOutputVisitor(out);

        for (SQLStatement stmt : stmtList) {
            stmt.accept(visitor);
        }

        return out.toString();
    }

    protected void print(List<SQLStatement> stmtList) {
        String text = output(stmtList);
        String outputProperty = System.getProperty("druid.output");
        if ("false".equals(outputProperty)) {
            return;
        }
        System.out.println(text);
    }
}
