package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObject;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleConstraint;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleForeignKey;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUsingIndexClause;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObject;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 17:04
 **/

public class XuguForeignKey extends SQLForeignKeyImpl implements XuguConstraint, XuguSQLObject {

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor instanceof OracleASTVisitor) {
            accept0((OracleASTVisitor) visitor);
            return;
        }

        super.accept(visitor);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.getName());
            acceptChild(visitor, this.getReferencedTableName());
            acceptChild(visitor, this.getReferencingColumns());
            acceptChild(visitor, this.getReferencedColumns());

        }
        visitor.endVisit(this);
    }

    @Override
    public XuguForeignKey clone() {
        XuguForeignKey x = new XuguForeignKey();
        cloneTo(x);
        return x;
    }
}
