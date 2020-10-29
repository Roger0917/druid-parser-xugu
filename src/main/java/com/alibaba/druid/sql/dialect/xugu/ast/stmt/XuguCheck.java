package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.statement.SQLCheck;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObject;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 17:10
 **/

public class XuguCheck extends SQLCheck implements XuguConstraint, XuguSQLObject {

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor instanceof XuguASTVisitor) {
            accept0((XuguASTVisitor) visitor);
            return;
        }

        super.accept(visitor);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.getName());
            acceptChild(visitor, this.getExpr());
        }
        visitor.endVisit(this);
    }

    @Override
    public XuguCheck clone() {
        XuguCheck x = new XuguCheck();
        cloneTo(x);
        return x;
    }
}
