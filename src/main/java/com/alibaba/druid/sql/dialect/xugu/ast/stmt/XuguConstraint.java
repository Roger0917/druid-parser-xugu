package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.statement.SQLConstraint;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObject;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleConstraint;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUsingIndexClause;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSQLObject;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 09:48
 **/

public interface XuguConstraint extends XuguSQLObject, SQLConstraint, SQLTableElement {

    /*SQLName getExceptionsInto();

    void setExceptionsInto(SQLName exceptionsInto);*/


    Boolean getEnable();

    void setEnable(Boolean enable);

    /*OracleConstraint.Initially getInitially();

    void setInitially(OracleConstraint.Initially value);

    public static enum Initially {
        DEFERRED, IMMEDIATE
    }*/

    XuguConstraint clone();
}
