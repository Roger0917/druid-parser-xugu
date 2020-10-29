package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLPrimaryKeyImpl;
import com.alibaba.druid.sql.ast.statement.SQLTableConstraint;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleConstraint;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OraclePrimaryKey;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUsingIndexClause;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 09:47
 **/

public class XuguPrimaryKey extends SQLPrimaryKeyImpl implements XuguConstraint, SQLPrimaryKey, SQLTableElement, SQLTableConstraint {

    //private OracleUsingIndexClause using;
    //private SQLName exceptionsInto;
    private Boolean                enable;
    //private Initially              initially;
    //private Boolean                deferrable;

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        this.accept0((XuguASTVisitor) visitor);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, getName());
            acceptChild(visitor, getColumns());
            //acceptChild(visitor, using);
            //acceptChild(visitor, exceptionsInto);
        }
        visitor.endVisit(this);
    }

    /*public Boolean getDeferrable() {
        return deferrable;
    }

    public void setDeferrable(Boolean deferrable) {
        this.deferrable = deferrable;
    }

    public OracleUsingIndexClause getUsing() {
        return using;
    }

    public void setUsing(OracleUsingIndexClause using) {
        this.using = using;
    }

    public SQLName getExceptionsInto() {
        return exceptionsInto;
    }

    public void setExceptionsInto(SQLName exceptionsInto) {
        this.exceptionsInto = exceptionsInto;
    }*/

    @Override
    public Boolean getEnable() {
        return enable;
    }

    @Override
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /*public Initially getInitially() {
        return initially;
    }

    public void setInitially(Initially initially) {
        this.initially = initially;
    }*/

    public void cloneTo(XuguPrimaryKey x) {
        super.cloneTo(x);
        /*if (using != null) {
            x.setUsing(using.clone());
        }
        if (exceptionsInto != null) {
            x.setExceptionsInto(exceptionsInto.clone());
        }*/
        x.enable = enable;
        //x.initially = initially;
        //x.deferrable = deferrable;
    }

    @Override
    public XuguPrimaryKey clone() {
        XuguPrimaryKey x = new XuguPrimaryKey();
        cloneTo(x);
        return x;
    }
}
