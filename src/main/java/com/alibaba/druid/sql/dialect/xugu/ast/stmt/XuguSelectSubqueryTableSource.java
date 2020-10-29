package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectPivotBase;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectSubqueryTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableSource;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 16:56
 **/

public class XuguSelectSubqueryTableSource extends SQLSubqueryTableSource implements XuguSelectTableSource {

    public XuguSelectSubqueryTableSource(){
    }

    public XuguSelectSubqueryTableSource(String alias){
        super(alias);
    }

    public XuguSelectSubqueryTableSource(SQLSelect select, String alias){
        super(select, alias);
    }

    public XuguSelectSubqueryTableSource(SQLSelect select){
        super(select);
    }
    @Override
    protected void accept0(SQLASTVisitor visitor) {
        this.accept0((XuguASTVisitor) visitor);
    }

    protected void accept0(OracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.getHints());
            acceptChild(visitor, this.select);
            acceptChild(visitor, this.flashback);
        }
        visitor.endVisit(this);
    }

    @Override
    public String toString () {
        return SQLUtils.toXuguString(this);
    }

    @Override
    public XuguSelectSubqueryTableSource clone() {
        XuguSelectSubqueryTableSource x = new XuguSelectSubqueryTableSource();
        cloneTo(x);
        return x;
    }
}
