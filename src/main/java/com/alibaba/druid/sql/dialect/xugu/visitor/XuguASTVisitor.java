package com.alibaba.druid.sql.dialect.xugu.visitor;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalDay;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.*;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguLobStorageClause;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguReturningClause;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.*;
import com.alibaba.druid.sql.visitor.SQLASTVisitor;

public interface XuguASTVisitor extends SQLASTVisitor {

    default void endVisit(XuguDeleteStatement x) {
        endVisit((SQLDeleteStatement) x);
    }

    /*default void endVisit(OracleIntervalExpr x) {

    }

    default void endVisit(OracleSelectJoin x) {

    }*/

    default void endVisit(XuguSelectSubqueryTableSource x) {

    }

    default void endVisit(XuguUpdateStatement x) {

    }

    default boolean visit(XuguDeleteStatement x) {
        return visit((SQLDeleteStatement) x);
    }

    /*default boolean visit(OracleIntervalExpr x) {
        return true;
    }

    default boolean visit(OracleSelectJoin x) {
        return true;
    }*/

    default boolean visit(OracleSelectSubqueryTableSource x) {
        return true;
    }


    default boolean visit(XuguUpdateStatement x) {
        return visit((SQLUpdateStatement) x);
    }

    /*default boolean visit(OracleWithSubqueryEntry x) {
        return true;
    }

    default void endVisit(OracleWithSubqueryEntry x) {

    }*/

    default boolean visit(SearchClause x) {
        return true;
    }

    default void endVisit(SearchClause x) {

    }

    default boolean visit(OracleCursorExpr x) {
        return true;
    }

    default void endVisit(OracleCursorExpr x) {

    }

    default boolean visit(OracleIsSetExpr x) {
        return true;
    }

    default void endVisit(OracleIsSetExpr x) {

    }

    default boolean visit(XuguReturningClause x) {
        return true;
    }

    default void endVisit(XuguReturningClause x) {

    }

    default boolean visit(XuguInsertStatement x) {
        return visit((SQLInsertStatement) x);
    }

    default void endVisit(XuguInsertStatement x) {
        endVisit((SQLInsertStatement) x);
    }

    /*default boolean visit(OracleSelectQueryBlock x) {
        return visit((SQLSelectQueryBlock) x);
    }

    default void endVisit(OracleSelectQueryBlock x) {
        endVisit((SQLSelectQueryBlock) x);
    }

    default boolean visit(OracleDatetimeExpr x) {
        return true;
    }

    default void endVisit(OracleDatetimeExpr x) {

    }

    default boolean visit(OracleSysdateExpr x) {
        return true;
    }

    default void endVisit(OracleSysdateExpr x) {

    }*/

    default boolean visit(XuguExceptionStatement x) {
        return true;
    }

    default void endVisit(XuguExceptionStatement x) {

    }

    default boolean visit(XuguExceptionStatement.Item x) {
        return true;
    }

    default void endVisit(XuguExceptionStatement.Item x) {

    }

    /*
    default boolean visit(OracleArgumentExpr x) {
        return true;
    }

    default void endVisit(OracleArgumentExpr x) {

    }

    default boolean visit(OracleSetTransactionStatement x) {
        return true;
    }

    default void endVisit(OracleSetTransactionStatement x) {

    }*/

    default boolean visit(XuguExplainStatement x) {
        return true;
    }

    default void endVisit(XuguExplainStatement x) {

    }

    default boolean visit(XuguAlterTableDropPartition x) {
        return true;
    }

    default void endVisit(XuguAlterTableDropPartition x) {

    }

    default boolean visit(XuguAlterTableTruncatePartition x) {
        return true;
    }

    default void endVisit(XuguAlterTableTruncatePartition x) {

    }


    default boolean visit(XuguCreateIndexStatement x) {
        return visit((SQLCreateIndexStatement) x);
    }

    default void endVisit(XuguCreateIndexStatement x) {
        endVisit((SQLCreateIndexStatement) x);
    }

    default boolean visit(XuguForStatement x) {
        return true;
    }

    default void endVisit(XuguForStatement x) {

    }

    default boolean visit(OracleRangeExpr x) {
        return true;
    }

    default void endVisit(OracleRangeExpr x) {

    }

    default boolean visit(XuguPrimaryKey x) {
        return true;
    }

    default void endVisit(XuguPrimaryKey x) {

    }

    default boolean visit(XuguCreateTableStatement x) {
        return visit((XuguCreateTableStatement) x);
    }

    default void endVisit(XuguCreateTableStatement x) {
        endVisit((XuguCreateTableStatement) x);
    }


    default boolean visit(XuguGotoStatement x) {
        return true;
    }

    default void endVisit(XuguGotoStatement x) {

    }

    default boolean visit(XuguAlterTriggerStatement x) {
        return true;
    }

    default void endVisit(XuguAlterTriggerStatement x) {

    }

    default boolean visit(OracleAlterTablespaceAddDataFile x) {
        return true;
    }

    default void endVisit(OracleAlterTablespaceAddDataFile x) {

    }

    default boolean visit(OracleAlterTablespaceStatement x) {
        return true;
    }

    default void endVisit(OracleAlterTablespaceStatement x) {

    }

    default boolean visit(XuguExitStatement x) {
        return true;
    }

    default void endVisit(XuguExitStatement x) {

    }

    default boolean visit(OracleContinueStatement x) {
        return true;
    }

    default void endVisit(OracleContinueStatement x) {

    }

    default boolean visit(XuguRaiseStatement x) {
        return true;
    }

    default void endVisit(XuguRaiseStatement x) {

    }

    default boolean visit(OracleDataTypeIntervalYear x) {
        return true;
    }

    default void endVisit(OracleDataTypeIntervalYear x) {

    }

    default boolean visit(OracleDataTypeIntervalDay x) {
        return true;
    }

    default void endVisit(OracleDataTypeIntervalDay x) {

    }

    default boolean visit(XuguLobStorageClause x) {
        return true;
    }

    default void endVisit(XuguLobStorageClause x) {

    }

    default boolean visit(XuguUnique x) {
        return visit((SQLUnique) x);
    }

    default void endVisit(XuguUnique x) {
        endVisit((SQLUnique) x);
    }

    default boolean visit(XuguForeignKey x) {
        return visit((SQLForeignKeyImpl) x);
    }

    default void endVisit(XuguForeignKey x) {
        endVisit((SQLForeignKeyImpl) x);
    }

    default boolean visit(XuguCheck x) {
        return visit((SQLCheck) x);
    }

    default void endVisit(XuguCheck x) {
        endVisit((SQLCheck) x);
    }

    default boolean visit(XuguCreatePackageStatement x) {
        return true;
    }

    default void endVisit(XuguCreatePackageStatement x) {

    }

    default boolean visit(XuguExecuteImmediateStatement x) {
        return true;
    }

    default void endVisit(XuguExecuteImmediateStatement x) {

    }

    default boolean visit(XuguCreateSynonymStatement x) {
        return true;
    }

    default void endVisit(XuguCreateSynonymStatement x) {

    }

    default boolean visit(XuguCreateTypeStatement x) {
        return true;
    }

    default void endVisit(XuguCreateTypeStatement x) {

    }

    default boolean visit(OracleIsOfTypeExpr x) {
        return true;
    }

    default void endVisit(OracleIsOfTypeExpr x) {

    }
}
