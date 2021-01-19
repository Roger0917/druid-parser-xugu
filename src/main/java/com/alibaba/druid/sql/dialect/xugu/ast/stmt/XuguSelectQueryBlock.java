package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObject;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.ModelClause;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObject;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 16:19
 **/

public class XuguSelectQueryBlock extends SQLSelectQueryBlock implements XuguSQLObject {

    private boolean                    skipLocked  = false;

    @Override
    public XuguSelectQueryBlock clone() {
        XuguSelectQueryBlock x = new XuguSelectQueryBlock();

        super.cloneTo(x);

        if (forUpdateOf != null) {
            for (SQLExpr item : forUpdateOf) {
                SQLExpr item1 = item.clone();
                item1.setParent(x);
                x.getForUpdateOf().add(item1);
            }
        }

        x.skipLocked = skipLocked;

        return x;
    }

    public XuguSelectQueryBlock(){
        dbType = DbType.xugu;
    }

    public boolean isSkipLocked() {
        return skipLocked;
    }

    public void setSkipLocked(boolean skipLocked) {
        this.skipLocked = skipLocked;
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor instanceof XuguASTVisitor) {
            accept0((XuguASTVisitor) visitor);
            return;
        }

        super.accept0(visitor);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.hints);
            acceptChild(visitor, this.selectList);
            acceptChild(visitor, this.into);
            acceptChild(visitor, this.from);
            acceptChild(visitor, this.where);
            acceptChild(visitor, this.startWith);
            acceptChild(visitor, this.connectBy);
            acceptChild(visitor, this.groupBy);
            acceptChild(visitor, this.orderBy);
            acceptChild(visitor, this.waitTime);
            acceptChild(visitor, this.limit);
            //acceptChild(visitor, this.modelClause);
            acceptChild(visitor, this.forUpdateOf);
        }
        visitor.endVisit(this);
    }

    @Override
    public String toString() {
        return SQLUtils.toXuguString(this);
    }

    @Override
    public void limit(int rowCount, int offset) {
        if (offset <= 0) {
            SQLExpr rowCountExpr = new SQLIntegerExpr(rowCount);
            SQLExpr newCondition = SQLUtils.buildCondition(SQLBinaryOperator.BooleanAnd, rowCountExpr, false,
                    where);
            setWhere(newCondition);
        } else {
            throw new UnsupportedOperationException("not support offset");
        }
    }

    public void setFrom(String tableName) {
        SQLExprTableSource from;
        if (tableName == null || tableName.length() == 0) {
            from = null;
        } else {
            from = new XuguSelectTableReference(new SQLIdentifierExpr(tableName));
        }
        this.setFrom(from);
    }
}
