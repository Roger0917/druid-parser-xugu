package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class XuguDataTypeIntervalHourToSecond extends SQLDataTypeImpl implements XuguSQLObject {

    public XuguDataTypeIntervalHourToSecond(){
        this.setName("INTERVAL HOUR TO SECOND");
    }

    private boolean               toSecond          = false;

    protected final List<SQLExpr> fractionalSeconds = new ArrayList<SQLExpr>();

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        this.accept0((XuguASTVisitor) visitor);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, getArguments());
        }
        visitor.endVisit(this);
    }

    public boolean isToSecond() {
        return toSecond;
    }

    public void setToSecond(boolean toSecond) {
        this.toSecond = toSecond;
    }

    public List<SQLExpr> getFractionalSeconds() {
        return fractionalSeconds;
    }

    public XuguDataTypeIntervalHourToSecond clone() {
        XuguDataTypeIntervalHourToSecond x = new XuguDataTypeIntervalHourToSecond();

        super.cloneTo(x);
        for (SQLExpr arg : fractionalSeconds) {
            arg.setParent(x);
            x.fractionalSeconds.add(arg);
        }

        return x;
    }
}
