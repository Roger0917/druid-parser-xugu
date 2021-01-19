package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class XuguDataTypeIntervalMonth extends SQLDataTypeImpl implements XuguSQLObject {

    public XuguDataTypeIntervalMonth(){
        this.setName("INTERVAL MONTH");
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

    public XuguDataTypeIntervalMonth clone() {
        XuguDataTypeIntervalMonth x = new XuguDataTypeIntervalMonth();

        super.cloneTo(x);

        return x;
    }
}
