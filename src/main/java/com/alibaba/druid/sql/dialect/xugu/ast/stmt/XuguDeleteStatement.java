package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLHint;
import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleReturningClause;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleDeleteStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguReturningClause;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 09:15
 **/

public class XuguDeleteStatement extends SQLDeleteStatement {

    private final List<SQLHint> hints     = new ArrayList<SQLHint>();
    private XuguReturningClause returning = null;

    public XuguDeleteStatement(){
        super (DbType.xugu);
    }

    public XuguReturningClause getReturning() {
        return returning;
    }

    public void setReturning(XuguReturningClause returning) {
        this.returning = returning;
    }

    public List<SQLHint> getHints() {
        return this.hints;
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        accept0((XuguASTVisitor) visitor);
    }

    protected void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.hints);
            acceptChild(visitor, this.tableSource);
            acceptChild(visitor, this.getWhere());
            //acceptChild(visitor, returning);
        }

        visitor.endVisit(this);
    }

    @Override
    public XuguDeleteStatement clone() {
        XuguDeleteStatement x = new XuguDeleteStatement();
        cloneTo(x);

        for (SQLHint hint : hints) {
            SQLHint hint2 = hint.clone();
            hint2.setParent(x);
            x.hints.add(hint2);
        }
        if (returning != null) {
            x.setReturning(returning.clone());
        }

        return x;
    }
}
