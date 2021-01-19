package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

public interface XuguSQLObject extends SQLObject {

    void accept0(XuguASTVisitor visitor);
}
