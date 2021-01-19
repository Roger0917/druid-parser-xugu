package com.alibaba.druid.sql.dialect.xugu.ast;

import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-28 10:06
 **/

public class XuguSQLPartition extends SQLPartition {

    protected SQLName name;

    protected SQLExpr subPartitionsCount;

    protected List<SQLSubPartition> subPartitions = new ArrayList<SQLSubPartition>();

    protected SQLPartitionValue values;

    // for mysql
   /* protected SQLExpr           dataDirectory;
    protected SQLExpr           indexDirectory;
    protected SQLExpr           maxRows;
    protected SQLExpr           minRows;
    protected SQLExpr           engine;
    protected SQLExpr           comment;*/

    // for oracle
   /* protected boolean segmentCreationImmediate;
    protected boolean segmentCreationDeferred;*/

    private SQLObject lobStorage;

    @Override
    public SQLName getName() {
        return name;
    }

    @Override
    public void setName(SQLName name) {
        if (name != null) {
            name.setParent(this);
        }
        this.name = name;
    }

    @Override
    public SQLExpr getSubPartitionsCount() {
        return subPartitionsCount;
    }

    @Override
    public void setSubPartitionsCount(SQLExpr subPartitionsCount) {
        if (subPartitionsCount != null) {
            subPartitionsCount.setParent(this);
        }
        this.subPartitionsCount = subPartitionsCount;
    }

    @Override
    public SQLPartitionValue getValues() {
        return values;
    }

    @Override
    public void setValues(SQLPartitionValue values) {
        if (values != null) {
            values.setParent(this);
        }
        this.values = values;
    }

    @Override
    public List<SQLSubPartition> getSubPartitions() {
        return subPartitions;
    }

    @Override
    public void addSubPartition(SQLSubPartition partition) {
        if (partition != null) {
            partition.setParent(this);
        }
        this.subPartitions.add(partition);
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, name);
            acceptChild(visitor, values);
            acceptChild(visitor, tablespace);

            acceptChild(visitor, subPartitionsCount);
           // acceptChild(visitor, storage);
            acceptChild(visitor, subPartitions);
        }
        visitor.endVisit(this);
    }

    @Override
    public SQLObject getLobStorage() {
        return lobStorage;
    }

    @Override
    public void setLobStorage(SQLObject lobStorage) {
        if (lobStorage != null) {
            lobStorage.setParent(this);
        }
        this.lobStorage = lobStorage;
    }

    @Override
    public XuguSQLPartition clone() {
        XuguSQLPartition x = new XuguSQLPartition();

        if (name != null) {
            x.setName(name.clone());
        }

        if (subPartitionsCount != null) {
            x.setSubPartitionsCount(subPartitionsCount.clone());
        }

        for (SQLSubPartition p : subPartitions) {
            SQLSubPartition p2 = p.clone();
            p2.setParent(x);
            x.subPartitions.add(p2);
        }

        if (values != null) {
            x.setValues(values.clone());
        }

        if (lobStorage != null) {
            x.setLobStorage(lobStorage.clone());
        }

        return x;
    }
}
