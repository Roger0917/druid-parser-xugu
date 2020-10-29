package com.alibaba.druid.sql.dialect.xugu.ast.clause;

import com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObject;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.CycleClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleWithSubqueryEntry;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.SearchClause;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 17:20
 **/

public class XuguWithSubqueryEntry extends SQLWithSubqueryClause.Entry implements OracleSQLObject {

    private SearchClause searchClause;

    public SearchClause getSearchClause() {
        return searchClause;
    }

    public void setSearchClause(SearchClause searchClause) {
        if (searchClause != null) {
            searchClause.setParent(this);
        }
        this.searchClause = searchClause;
    }

    @Override
    public void accept0(OracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, columns);
            acceptChild(visitor, subQuery);
            acceptChild(visitor, searchClause);
            //acceptChild(visitor, cycleClause);
        }
        visitor.endVisit(this);
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        this.accept0((OracleASTVisitor) visitor);
    }

    public void cloneTo(XuguWithSubqueryEntry x) {
        super.cloneTo(x);

        if (searchClause != null) {
            setSearchClause(searchClause.clone());
        }
    }

    @Override
    public XuguWithSubqueryEntry clone() {
        XuguWithSubqueryEntry x = new XuguWithSubqueryEntry();
        cloneTo(x);
        return x;
    }
}
