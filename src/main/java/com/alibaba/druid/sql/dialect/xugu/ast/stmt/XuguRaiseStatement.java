package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleStatementImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 11:33
 **/

public class XuguRaiseStatement extends XuguStatementImpl {

    private SQLExpr exception;

    public SQLExpr getException() {
        return exception;
    }

    public void setException(SQLExpr exception) {
        this.exception = exception;
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, exception);
        }
        visitor.endVisit(this);
    }
}
