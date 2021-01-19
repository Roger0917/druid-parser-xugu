package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

public class XuguContinueStatement extends XuguStatementImpl{

    private SQLExpr when;
    private String label;

    public SQLExpr getWhen() {
        return when;
    }

    public void setWhen(SQLExpr when) {
        if (when != null) {
            when.setParent(this);
        }
        this.when = when;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, when);
        }
        visitor.endVisit(this);
    }
}
