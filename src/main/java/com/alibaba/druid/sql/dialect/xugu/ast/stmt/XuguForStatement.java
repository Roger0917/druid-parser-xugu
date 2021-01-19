package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLForStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleForStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 11:38
 **/

public class XuguForStatement extends SQLForStatement implements XuguStatement{

    private boolean            all;

    private SQLName endLabel;

    @Override
    protected void accept0(SQLASTVisitor v) {
        if (v instanceof XuguASTVisitor) {
            accept0((OracleASTVisitor) v);
            return;
        }

        super.accept0(v);
    }

    @Override
    public void accept0(XuguASTVisitor v) {
        if (v.visit(this)) {
            acceptChild(v, index);
            acceptChild(v, range);
            acceptChild(v, statements);
        }
        v.endVisit(this);
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public SQLName getEndLabel() {
        return endLabel;
    }

    public void setEndLabel(SQLName endLabel) {
        if (endLabel != null) {
            endLabel.setParent(this);
        }
        this.endLabel = endLabel;
    }

    @Override
    public XuguForStatement clone() {
        XuguForStatement x = new XuguForStatement();
        if (index != null) {
            x.setIndex(index.clone());
        }
        if (range != null) {
            x.setRange(range.clone());
        }
        for (SQLStatement stmt : statements) {
            SQLStatement stmt2 = stmt.clone();
            stmt2.setParent(x);
            x.statements.add(stmt2);
        }
        x.all = all;
        if (endLabel != null) {
            x.setEndLabel(endLabel.clone());
        }
        return x;
    }
}
