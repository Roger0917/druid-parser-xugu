package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLObject;

public interface XuguSegmentAttributes extends SQLObject {

    SQLName getTablespace();
    void setTablespace(SQLName name);
}
