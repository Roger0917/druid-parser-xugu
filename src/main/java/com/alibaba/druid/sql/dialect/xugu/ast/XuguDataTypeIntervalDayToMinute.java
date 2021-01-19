package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class XuguDataTypeIntervalDayToMinute extends SQLDataTypeImpl implements XuguSQLObject {

    public XuguDataTypeIntervalDayToMinute(){
        this.setName("INTERVAL DAY TO MINUTE");
    }

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

    public XuguDataTypeIntervalDayToMinute clone() {
        XuguDataTypeIntervalDayToMinute x = new XuguDataTypeIntervalDayToMinute();

        super.cloneTo(x);

        return x;
    }
}
