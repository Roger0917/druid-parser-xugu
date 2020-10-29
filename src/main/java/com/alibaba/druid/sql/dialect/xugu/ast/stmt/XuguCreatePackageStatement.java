package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreatePackageStatement;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 09:09
 **/

public class XuguCreatePackageStatement extends XuguStatementImpl implements SQLCreateStatement {
    private boolean            orReplace;
    private SQLName name;

    private boolean body;

    private final List<SQLStatement> statements = new ArrayList<SQLStatement>();

    public XuguCreatePackageStatement() {
        super.setDbType(DbType.xugu);
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, name);
            acceptChild(visitor, statements);
        }
        visitor.endVisit(this);
    }

    @Override
    public XuguCreatePackageStatement clone() {
        XuguCreatePackageStatement x = new XuguCreatePackageStatement();

        x.orReplace = orReplace;
        if (name != null) {
            x.setName(name.clone());
        }
        x.body = body;

        for (SQLStatement stmt : statements) {
            SQLStatement s2 = stmt.clone();
            s2.setParent(x);
            x.statements.add(s2);
        }

        return x;
    }

    public boolean isOrReplace() {
        return orReplace;
    }

    public void setOrReplace(boolean orReplace) {
        this.orReplace = orReplace;
    }

    public boolean isBody() {
        return body;
    }

    public void setBody(boolean body) {
        this.body = body;
    }

    public SQLName getName() {
        return name;
    }

    public void setName(SQLName name) {
        if (name != null) {
            name.setParent(this);
        }
        this.name = name;
    }

    public List<SQLStatement> getStatements() {
        return statements;
    }
}
