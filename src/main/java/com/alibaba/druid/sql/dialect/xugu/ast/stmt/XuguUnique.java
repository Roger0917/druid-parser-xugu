package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.statement.SQLUnique;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUnique;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObject;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 16:44
 **/

public class XuguUnique extends SQLUnique implements XuguConstraint, XuguSQLObject {

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
            acceptChild(visitor, this.getColumns());
        }
        visitor.endVisit(this);
    }

    public void cloneTo(OracleUnique x) {
        super.cloneTo(x);
    }

    @Override
    public XuguUnique clone() {
        XuguUnique x = new XuguUnique();
        cloneTo(x);
        return x;
    }
}
