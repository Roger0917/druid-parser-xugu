package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLArgument;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

import java.util.ArrayList;
import java.util.List;

public class XuguExecuteStatement extends XuguStatementImpl{

    private SQLExpr dynamicSql;

    private final List<SQLArgument> arguments = new ArrayList<SQLArgument>();

    private final List<SQLExpr> into = new ArrayList<SQLExpr>();

    private final List<SQLExpr> returnInto = new ArrayList<SQLExpr>();

    public XuguExecuteStatement(){
    }

    public XuguExecuteStatement(String dynamicSql){
        this.setDynamicSql(dynamicSql);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
//            acceptChild(visitor, label);
        }
        visitor.endVisit(this);
    }

    @Override
    public XuguExecuteStatement clone() {
        XuguExecuteStatement x = new XuguExecuteStatement();

        if (dynamicSql != null) {
            x.setDynamicSql(dynamicSql.clone());
        }

        for (SQLArgument arg : arguments) {
            SQLArgument a2 = arg.clone();
            a2.setParent(x);
            x.arguments.add(a2);
        }

        for (SQLExpr e : into) {
            SQLExpr e2 = e.clone();
            e2.setParent(x);
            x.into.add(e2);
        }

        for (SQLExpr e : returnInto) {
            SQLExpr e2 = e.clone();
            e2.setParent(x);
            x.returnInto.add(e2);
        }

        return x;
    }

    public SQLExpr getDynamicSql() {
        return dynamicSql;
    }

    public void setDynamicSql(SQLExpr dynamicSql) {
        if (dynamicSql != null) {
            dynamicSql.setParent(this);
        }
        this.dynamicSql = dynamicSql;
    }

    public void setDynamicSql(String dynamicSql) {
        this.setDynamicSql(new SQLCharExpr(dynamicSql));
    }

    public List<SQLArgument> getArguments() {
        return arguments;
    }

    public List<SQLExpr> getInto() {
        return into;
    }

    public List<SQLExpr> getReturnInto() {
        return returnInto;
    }
}
