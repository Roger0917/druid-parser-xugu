package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLObjectImpl;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSegmentAttributesImpl;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 16:31
 **/

public abstract class XuguSegmentAttributesImpl extends SQLObjectImpl implements XuguSegmentAttributes  {

    protected SQLName tablespace;

    @Override
    public SQLName getTablespace() {
        return tablespace;
    }

    @Override
    public void setTablespace(SQLName x) {
        if (x != null) {
            x.setParent(this);
        }
        this.tablespace = x;
    }

    public void cloneTo(XuguSegmentAttributesImpl x) {
       /* x.pctfree = pctfree;
        x.pctused = pctused;
        x.initrans = initrans;

        x.maxtrans = maxtrans;
        x.pctincrease = pctincrease;
        x.freeLists = freeLists;
        x.compress = compress;
        x.compressLevel = compressLevel;
        x.compressForOltp = compressForOltp;
        x.pctthreshold = pctthreshold;

        x.logging = logging;*/

        if (tablespace != null) {
            x.setTablespace(tablespace.clone());
        }

        /*if (storage != null) {
            x.setStorage(storage.clone());
        }*/
    }
}
