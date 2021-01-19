package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class XuguDataTypeIntervalHourToMinute extends SQLDataTypeImpl implements XuguSQLObject {

    public XuguDataTypeIntervalHourToMinute(){
        this.setName("INTERVAL HOUR TO MINUTE");
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

    public XuguDataTypeIntervalHourToMinute clone() {
        XuguDataTypeIntervalHourToMinute x = new XuguDataTypeIntervalHourToMinute();

        super.cloneTo(x);

        return x;
    }
}
