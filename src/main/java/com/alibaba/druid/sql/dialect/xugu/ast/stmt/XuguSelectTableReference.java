package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.PartitionExtensionClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.SampleClause;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-05 17:24
 **/

public class XuguSelectTableReference extends SQLExprTableSource implements XuguSelectTableSource {

    private boolean                    only = false;
    //protected OracleSelectPivotBase pivot;

    protected PartitionExtensionClause partition;
    protected SampleClause sampleClause;

    public XuguSelectTableReference(){

    }

    public XuguSelectTableReference(SQLExpr expr) {
        this.setExpr(expr);
    }

    public PartitionExtensionClause getPartition() {
        return partition;
    }

    public void setPartition(PartitionExtensionClause partition) {
        if (partition != null) {
            partition.setParent(this);
        }
        this.partition = partition;
    }

    public boolean isOnly() {
        return this.only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public SampleClause getSampleClause() {
        return sampleClause;
    }

    public void setSampleClause(SampleClause sampleClause) {
        if (sampleClause != null) {
            sampleClause.setParent(this);
        }
        this.sampleClause = sampleClause;
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor instanceof OracleASTVisitor) {
            this.accept0((OracleASTVisitor) visitor);
        } else {
            super.accept0(visitor);
        }
    }

    protected void accept0(OracleASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, this.expr);
            acceptChild(visitor, this.partition);
            acceptChild(visitor, this.sampleClause);
        }
        visitor.endVisit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        XuguSelectTableReference that = (XuguSelectTableReference) o;

        if (only != that.only) return false;
        if (partition != null ? !partition.equals(that.partition) : that.partition != null) return false;
        if (sampleClause != null ? !sampleClause.equals(that.sampleClause) : that.sampleClause != null) return false;
        return flashback != null ? flashback.equals(that.flashback) : that.flashback == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (only ? 1 : 0);
        result = 31 * result + (partition != null ? partition.hashCode() : 0);
        result = 31 * result + (sampleClause != null ? sampleClause.hashCode() : 0);
        result = 31 * result + (flashback != null ? flashback.hashCode() : 0);
        return result;
    }

    public String toString () {
        return SQLUtils.toXuguString(this);
    }


    public XuguSelectTableReference clone() {
        XuguSelectTableReference x = new XuguSelectTableReference();
        cloneTo(x);

        x.only = only;

        if (partition != null) {
            x.setPartition(partition.clone());
        }

        if (sampleClause != null) {
            x.setSampleClause(sampleClause.clone());
        }

        if (flashback != null) {
            setFlashback(flashback.clone());
        }

        return x;
    }
}
