package com.alibaba.druid.sql.dialect.xugu.ast.clause;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLReplaceable;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObjectImpl;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleReturningClause;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObjectImpl;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 14:49
 **/

public class XuguReturningClause extends XuguSQLObjectImpl implements SQLReplaceable {

    private List<SQLExpr> items  = new ArrayList<SQLExpr>();
    private List<SQLExpr> values = new ArrayList<SQLExpr>();

    public List<SQLExpr> getItems() {
        return items;
    }

    public void addItem(SQLExpr item) {
        if (item != null) {
            item.setParent(this);
        }
        this.items.add(item);
    }

    public List<SQLExpr> getValues() {
        return values;
    }

    public void addValue(SQLExpr value) {
        if (value != null) {
            value.setParent(this);
        }
        this.values.add(value);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, items);
            acceptChild(visitor, values);
        }
        visitor.endVisit(this);
    }

    @Override
    public XuguReturningClause clone() {
        XuguReturningClause x = new XuguReturningClause();

        for (SQLExpr item : items) {
            SQLExpr item2 = item.clone();
            item2.setParent(x);
            x.items.add(item2);
        }

        for (SQLExpr v : values) {
            SQLExpr v2 = v.clone();
            v2.setParent(x);
            x.values.add(v2);
        }

        return x;
    }

    @Override
    public boolean replace(SQLExpr expr, SQLExpr target) {
        for (int i = items.size() - 1; i >= 0; i--) {
            if (items.get(i) == expr) {
                target.setParent(this);
                items.set(i, target);
                return true;
            }
        }

        for (int i = values.size() - 1; i >= 0; i--) {
            if (values.get(i) == expr) {
                target.setParent(this);
                values.set(i, target);
                return true;
            }
        }

        return false;
    }
}
