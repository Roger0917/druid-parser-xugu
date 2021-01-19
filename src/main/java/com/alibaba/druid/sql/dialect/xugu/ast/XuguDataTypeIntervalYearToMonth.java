package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public class XuguDataTypeIntervalYearToMonth extends SQLDataTypeImpl implements XuguSQLObject {

    public XuguDataTypeIntervalYearToMonth(){
        this.setName("INTERVAL YEAR TO MONTH");
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

    public XuguDataTypeIntervalYearToMonth clone() {
        XuguDataTypeIntervalYearToMonth x = new XuguDataTypeIntervalYearToMonth();

        super.cloneTo(x);

        return x;
    }
}
