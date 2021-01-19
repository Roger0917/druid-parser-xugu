package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObjectImpl;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleExceptionStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObjectImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 13:54
 **/

public class XuguExceptionStatement extends XuguStatementImpl implements XuguStatement {

    private List<XuguExceptionStatement.Item> items = new ArrayList<XuguExceptionStatement.Item>();

    public List<XuguExceptionStatement.Item> getItems() {
        return items;
    }

    public void addItem(XuguExceptionStatement.Item item) {
        if (item != null) {
            item.setParent(this);
        }

        this.items.add(item);
    }

    public static class Item extends XuguSQLObjectImpl {

        private SQLExpr when;
        private List<SQLStatement> statements = new ArrayList<SQLStatement>();

        public SQLExpr getWhen() {
            return when;
        }

        public void setWhen(SQLExpr when) {
            this.when = when;
        }

        public List<SQLStatement> getStatements() {
            return statements;
        }

        public void setStatement(SQLStatement statement) {
            if (statement != null) {
                statement.setParent(this);
                this.statements.add(statement);
            }
        }

        @Override
        public void accept0(XuguASTVisitor visitor) {
            if (visitor.visit(this)) {
                acceptChild(visitor, when);
                acceptChild(visitor, statements);
            }
            visitor.endVisit(this);
        }

        @Override
        public XuguExceptionStatement.Item clone() {
            XuguExceptionStatement.Item x = new XuguExceptionStatement.Item();
            if (when != null) {
                x.setWhen(when.clone());
            }
            for (SQLStatement stmt : statements) {
                SQLStatement stmt2 = stmt.clone();
                stmt2.setParent(x);
                x.statements.add(stmt2);
            }
            return x;
        }
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, items);
        }
        visitor.endVisit(this);
    }

    @Override
    public XuguExceptionStatement clone() {
        XuguExceptionStatement x = new XuguExceptionStatement();
        for (XuguExceptionStatement.Item item : items) {
            XuguExceptionStatement.Item item2 = item.clone();
            item2.setParent(x);
            x.items.add(item2);
        }
        return x;
    }
}
