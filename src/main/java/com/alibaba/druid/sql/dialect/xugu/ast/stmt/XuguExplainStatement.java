package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLExplainStatement;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 11:51
 **/

public class XuguExplainStatement extends SQLExplainStatement implements XuguStatement {

    private SQLExpr statementId;
    private SQLExpr     into;

    public XuguExplainStatement() {
        super (DbType.oracle);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, statementId);
            acceptChild(visitor, into);
            acceptChild(visitor, statement);
        }
        visitor.endVisit(this);
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        accept0((XuguASTVisitor) visitor);
    }

    public String toString() {
        return SQLUtils.toOracleString(this);
    }

    public SQLExpr getStatementId() {
        return statementId;
    }

    public void setStatementId(SQLExpr statementId) {
        this.statementId = statementId;
    }

    public SQLExpr getInto() {
        return into;
    }

    public void setInto(SQLExpr into) {
        this.into = into;
    }
}
