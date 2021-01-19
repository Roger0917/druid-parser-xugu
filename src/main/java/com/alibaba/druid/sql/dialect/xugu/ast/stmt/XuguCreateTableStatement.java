package com.alibaba.druid.sql.dialect.xugu.ast.stmt;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.ast.statement.SQLExternalRecordFormat;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSQLObject;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSegmentAttributes;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleSegmentAttributesImpl;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleLobStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTableStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleXmlColumnProperties;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleASTVisitor;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguSegmentAttributes;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguLobStorageClause;
import com.alibaba.druid.sql.dialect.xugu.visitor.XuguASTVisitor;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 09:55
 **/

public class XuguCreateTableStatement extends SQLCreateTableStatement implements XuguDDLStatement, XuguSegmentAttributes {

    private boolean                   inMemoryMetadata;

    private boolean                   cursorSpecificSegment;

    // NOPARALLEL
    private Boolean                   parallel;
    private SQLExpr parallelValue;

    public XuguLobStorageClause getLobStorage() {
        return lobStorage;
    }

    public void setLobStorage(XuguLobStorageClause lobStorage) {
        this.lobStorage = lobStorage;
    }

    private XuguLobStorageClause lobStorage;

    private Integer                   pctfree;
    private Integer                   pctused;
    private Integer                   initrans;
    private Integer                   maxtrans;
    private Integer                   pctincrease;


    private Integer                   compressLevel;
    private boolean                   compressForOltp;

    private Boolean                   cache;

    private Boolean                   enableRowMovement;

    private List<SQLName> clusterColumns = new ArrayList<SQLName>();
    private SQLName                   cluster;


    private SQLName                   of;
    private boolean                   monitoring;
    private List<SQLName>             including = new ArrayList<SQLName>();

    @Override
    public void simplify() {
        tablespace = null;
        /*storage = null;
        lobStorage = null;*/

        pctfree = null;
        pctused = null;
        initrans = null;
        maxtrans = null;
        pctincrease = null;

        logging = null;
        compress = null;
        compressLevel = null;
        compressForOltp = false;

        onCommitPreserveRows = false;
        onCommitDeleteRows = false;

        super.simplify();
    }

    public XuguCreateTableStatement() {
        super (DbType.xugu);
    }

    /*public OracleLobStorageClause getLobStorage() {
        return lobStorage;
    }

    public void setLobStorage(OracleLobStorageClause lobStorage) {
        this.lobStorage = lobStorage;
    }*/

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public boolean isOnCommitDeleteRows() {
        return onCommitDeleteRows;
    }

    public void setOnCommitDeleteRows(boolean onCommitDeleteRows) {
        this.onCommitDeleteRows = onCommitDeleteRows;
    }

    public Integer getCompressLevel() {
        return compressLevel;
    }

    public void setCompressLevel(Integer compressLevel) {
        this.compressLevel = compressLevel;
    }

    public Integer getPctfree() {
        return pctfree;
    }

    public void setPctfree(Integer pctfree) {
        this.pctfree = pctfree;
    }

    public Integer getPctused() {
        return pctused;
    }

    public void setPctused(Integer pctused) {
        this.pctused = pctused;
    }

    public Integer getInitrans() {
        return initrans;
    }

    public void setInitrans(Integer initrans) {
        this.initrans = initrans;
    }

    public Integer getMaxtrans() {
        return maxtrans;
    }

    public void setMaxtrans(Integer maxtrans) {
        this.maxtrans = maxtrans;
    }

    public Integer getPctincrease() {
        return pctincrease;
    }

    public void setPctincrease(Integer pctincrease) {
        this.pctincrease = pctincrease;
    }

    public Boolean getParallel() {
        return parallel;
    }

    public void setParallel(Boolean parallel) {
        this.parallel = parallel;
    }

    public SQLExpr getParallelValue() {
        return parallelValue;
    }

    public void setParallelValue(SQLExpr x) {
        if (x != null) {
            x.setParent(this);
        }
        this.parallelValue = x;
    }

    public boolean isCursorSpecificSegment() {
        return cursorSpecificSegment;
    }

    public void setCursorSpecificSegment(boolean cursorSpecificSegment) {
        this.cursorSpecificSegment = cursorSpecificSegment;
    }

    public boolean isInMemoryMetadata() {
        return inMemoryMetadata;
    }

    public void setInMemoryMetadata(boolean inMemoryMetadata) {
        this.inMemoryMetadata = inMemoryMetadata;
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        accept0((XuguASTVisitor) visitor);
    }

   /* public OracleStorageClause getStorage() {
        return storage;
    }

    public void setStorage(SQLObject storage) {
        if (storage != null) {
            storage.setParent(this);
        }
        this.storage = (OracleStorageClause) storage;
    }*/

    public SQLName getOf() {
        return of;
    }

    public void setOf(SQLName of) {
        if (of != null) {
            of.setParent(this);
        }
        this.of = of;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }

    public boolean isCompressForOltp() {
        return compressForOltp;
    }

    public void setCompressForOltp(boolean compressForOltp) {
        this.compressForOltp = compressForOltp;
    }

    public Boolean getEnableRowMovement() {
        return enableRowMovement;
    }

    public void setEnableRowMovement(Boolean enableRowMovement) {
        this.enableRowMovement = enableRowMovement;
    }

    public List<SQLName> getClusterColumns() {
        return clusterColumns;
    }

    public SQLName getCluster() {
        return cluster;
    }

    public void setCluster(SQLName x) {
        if (x != null) {
            x.setParent(this);
        }
        this.cluster = x;
    }

    public List<SQLName> getIncluding() {
        return including;
    }

    @Override
    public void accept0(XuguASTVisitor visitor) {
        if (visitor.visit(this)) {
            this.acceptChild(visitor, tableSource);
            this.acceptChild(visitor, of);
            this.acceptChild(visitor, tableElementList);
            this.acceptChild(visitor, tablespace);
            this.acceptChild(visitor, select);
            this.acceptChild(visitor, partitioning);
        }
        visitor.endVisit(this);
    }

    public static enum DeferredSegmentCreation {
        IMMEDIATE, DEFERRED
    }


}
