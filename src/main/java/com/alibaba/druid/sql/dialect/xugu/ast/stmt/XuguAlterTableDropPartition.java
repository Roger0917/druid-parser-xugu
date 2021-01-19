package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 16:10
 **/

public class XuguAlterTableDropPartition extends XuguAlterTableItem {

    private SQLName name;

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, name);
        }
        visitor.endVisit(this);
    }

    public SQLName getName() {
        return name;
    }

    public void setName(SQLName name) {
        this.name = name;
    }

}
