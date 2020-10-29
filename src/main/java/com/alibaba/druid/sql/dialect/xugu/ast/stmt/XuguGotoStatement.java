package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleStatementImpl;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 16:38
 **/

public class XuguGotoStatement extends XuguStatementImpl {

    private SQLName label;

    public XuguGotoStatement(){
    }

    public XuguGotoStatement(SQLName label){
        this.label = label;
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, label);
        }
        visitor.endVisit(this);
    }

    public SQLName getLabel() {
        return label;
    }

    public void setLabel(SQLName label) {
        this.label = label;
    }
}
