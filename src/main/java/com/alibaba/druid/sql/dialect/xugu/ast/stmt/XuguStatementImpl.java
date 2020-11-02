package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatementImpl;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 09:29
 **/

public abstract class XuguStatementImpl extends SQLStatementImpl implements XuguStatement {

    public XuguStatementImpl() {
        super(DbType.xugu);
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        accept0((XuguASTVisitor) visitor);
    }

    @Override
    public abstract void accept0(XuguASTVisitor visitor);

    public String toString() {
        return SQLUtils.toXuguString(this);
    }

}
