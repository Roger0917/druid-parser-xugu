package com.alibaba.druid.sql.dialect.xugu.ast.expr;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObjectImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

import java.util.Arrays;
import java.util.List;

public class XuguRangeExpr extends XuguSQLObjectImpl implements SQLExpr {

    private SQLExpr lowBound;
    private SQLExpr upBound;

    public XuguRangeExpr(){

    }

    public XuguRangeExpr(SQLExpr lowBound, SQLExpr upBound){
        setLowBound(lowBound);
        setUpBound(upBound);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, lowBound);
            acceptChild(visitor, upBound);
        }
        visitor.endVisit(this);
    }

    public List<SQLObject> getChildren() {
        return Arrays.<SQLObject>asList(this.lowBound, this.upBound);
    }

    public SQLExpr getLowBound() {
        return lowBound;
    }

    public void setLowBound(SQLExpr lowBound) {
        if (lowBound != null) {
            lowBound.setParent(this);
        }
        this.lowBound = lowBound;
    }

    public SQLExpr getUpBound() {
        return upBound;
    }

    public void setUpBound(SQLExpr upBound) {
        if (upBound != null) {
            upBound.setParent(this);
        }
        this.upBound = upBound;
    }


    public XuguRangeExpr clone() {
        XuguRangeExpr x = new XuguRangeExpr();

        if (lowBound != null) {
            x.setLowBound(lowBound.clone());
        }

        if (upBound != null) {
            x.setUpBound(upBound.clone());
        }

        return x;
    }
}
