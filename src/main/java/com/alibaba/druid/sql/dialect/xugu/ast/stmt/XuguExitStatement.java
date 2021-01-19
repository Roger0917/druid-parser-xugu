package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLCommentHint;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleExitStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 11:30
 **/

public class XuguExitStatement extends XuguStatementImpl {

    private String label;

    private SQLExpr when;

    public SQLExpr getWhen() {
        return when;
    }

    public void setWhen(SQLExpr when) {
        if (when != null) {
            when.setParent(this);
        }
        this.when = when;
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, when);
        }
        visitor.endVisit(this);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public OracleExitStatement clone() {
        OracleExitStatement x = new OracleExitStatement();

        x.setLabel(this.label);

        x.setAfterSemi(this.afterSemi);

        x.setDbType(this.dbType);

        if (when != null) {
            x.setWhen(when.clone());
        }

        if (this.headHints != null) {
            List<SQLCommentHint> headHintsClone = new ArrayList<SQLCommentHint>(this.headHints.size());
            for (SQLCommentHint hint : headHints) {
                SQLCommentHint h2 = hint.clone();
                h2.setParent(x);
                headHintsClone.add(h2);
            }
            x.setHeadHints(headHintsClone);
        }

        return x;
    }
}
