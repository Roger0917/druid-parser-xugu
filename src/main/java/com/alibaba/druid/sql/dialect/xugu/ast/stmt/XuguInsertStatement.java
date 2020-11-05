package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLCommentHint;
import com.alibaba.druid.sql.ast.SQLHint;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguReturningClause;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 09:14
 **/

public class XuguInsertStatement extends SQLInsertStatement implements XuguStatement {
    private XuguReturningClause returning;
    //private SQLErrorLoggingClause errorLogging;
    private List<SQLHint> hints = new ArrayList<SQLHint>();

    public XuguInsertStatement() {
        dbType = DbType.xugu;
    }

    public void cloneTo(XuguInsertStatement x) {
        super.cloneTo(x);
        if (returning != null) {
            x.setReturning(returning.clone());
        }
       /* if (errorLogging != null) {
            x.setErrorLogging(errorLogging.clone());
        }*/
        for (SQLHint hint : hints) {
            SQLHint h2 = hint.clone();
            h2.setParent(x);
            x.hints.add(h2);
        }
    }

    public List<SQLHint> getHints() {
        return hints;
    }

    public void setHints(List<SQLHint> hints) {
        this.hints = hints;
    }

    public XuguReturningClause getReturning() {
        return returning;
    }

    public void setReturning(XuguReturningClause returning) {
        this.returning = returning;
    }

   /* public SQLErrorLoggingClause getErrorLogging() {
        return errorLogging;
    }

    public void setErrorLogging(SQLErrorLoggingClause errorLogging) {
        this.errorLogging = errorLogging;
    }*/

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        this.accept0((XuguASTVisitor) visitor);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, getTableSource());
            this.acceptChild(visitor, getColumns());
            this.acceptChild(visitor, getValues());
            this.acceptChild(visitor, getQuery());
            //this.acceptChild(visitor, returning);
            //this.acceptChild(visitor, errorLogging);
        }

        visitor.endVisit(this);
    }

    @Override
    public XuguInsertStatement clone() {
        XuguInsertStatement x = new XuguInsertStatement();
        cloneTo(x);
        return x;
    }

    @Override
    public List<SQLCommentHint> getHeadHintsDirect() {
        return null;
    }

    public ValuesClause getValues() {
        if (valuesList.size() == 0) {
            return null;
        }
        return valuesList.get(0);
    }
}
