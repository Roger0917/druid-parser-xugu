package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLPartitionValue;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 09:02
 **/

public class XuguSQLPartitionValue extends XuguSegmentAttributesImpl{

    protected XuguSQLPartitionValue.Operator operator;
    protected final List<SQLExpr> items = new ArrayList<SQLExpr>();

    public XuguSQLPartitionValue(XuguSQLPartitionValue.Operator operator){
        super();
        this.operator = operator;
    }

    public List<SQLExpr> getItems() {
        return items;
    }

    public void addItem(SQLExpr item) {
        if (item != null) {
            item.setParent(this);
        }
        this.items.add(item);
    }

    public XuguSQLPartitionValue.Operator getOperator() {
        return operator;
    }

    public static enum Operator {
        LessThan, //
        In, //
        List
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, getItems());
        }
        visitor.endVisit(this);
    }

    @Override
    public XuguSQLPartitionValue clone() {
        XuguSQLPartitionValue x = new XuguSQLPartitionValue(operator);

        for (SQLExpr item : items) {
            SQLExpr item2 = item.clone();
            item2.setParent(x);
            x.items.add(item2);
        }

        return x;
    }
}
