package com.alibaba.druid.sql.dialect.xugu.visitor;

import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalDay;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.*;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.xugu.ast.*;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguLobStorageClause;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguReturningClause;
import com.alibaba.druid.sql.dialect.xugu.ast.expr.*;
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
    default boolean endVisit(OracleSelectRestriction.CheckOption x) {
        return true;
    }

    default boolean endVisit(OracleSelectRestriction.ReadOnly x) {
        return true;
    }

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

    default boolean visit(XuguOuterExpr x) {
        return true;
    }

    default void endVisit(XuguOuterExpr x) {

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

    }*/

    default boolean visit(XuguSysdateExpr x) {
        return true;
    }

    default void endVisit(XuguSysdateExpr x) {

    }

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


    default boolean visit(XuguArgumentExpr x) {
        return true;
    }

    default void endVisit(XuguArgumentExpr x) {

    }

    /*
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

    default boolean visit(XuguBinaryFloatExpr x) {
        return true;
    }

    default void endVisit(XuguBinaryFloatExpr x) {

    }

    default boolean visit(XuguBinaryDoubleExpr x) {
        return true;
    }

    default void endVisit(XuguBinaryDoubleExpr x) {

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

    default boolean visit(XuguRangeExpr x) {
        return true;
    }

    default void endVisit(XuguRangeExpr x) {

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

    default boolean visit(XuguContinueStatement x) {
        return true;
    }

    default void endVisit(XuguContinueStatement x) {

    }

    default boolean visit(XuguRaiseStatement x) {
        return true;
    }

    default void endVisit(XuguRaiseStatement x) {

    }

    default boolean visit(XuguDataTypeIntervalYear x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalYear x) {

    }

    default boolean visit(XuguDataTypeIntervalMonth x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalMonth x) {

    }

    default boolean visit(XuguDataTypeIntervalDay x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalDay x) {

    }

    default boolean visit(XuguDataTypeIntervalHour x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalHour x) {

    }

    default boolean visit(XuguDataTypeIntervalMinute x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalMinute x) {

    }

    default boolean visit(XuguDataTypeIntervalSecond x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalSecond x) {

    }

    default boolean visit(XuguDataTypeIntervalYearToMonth x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalYearToMonth x) {

    }

    default boolean visit(XuguDataTypeIntervalDayToHour x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalDayToHour x) {

    }

    default boolean visit(XuguDataTypeIntervalDayToMinute x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalDayToMinute x) {

    }

    default boolean visit(XuguDataTypeIntervalDayToSecond x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalDayToSecond x) {

    }

    default boolean visit(XuguDataTypeIntervalHourToMinute x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalHourToMinute x) {

    }

    default boolean visit(XuguDataTypeIntervalHourToSecond x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalHourToSecond x) {

    }

    default boolean visit(XuguDataTypeIntervalMinuteToSecond x) {
        return true;
    }

    default void endVisit(XuguDataTypeIntervalMinuteToSecond x) {

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

    default boolean visit(XuguExecuteStatement x) {
        return true;
    }

    default void endVisit(XuguExecuteStatement x) {

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

    default boolean visit(XuguSQLPartitionValue x) {
        return true;
    }

    default void endVisit(XuguSQLPartitionValue x) {

    }

    default boolean visit(XuguSQLPartition x) {
        return true;
    }

    default void endVisit(XuguSQLPartition x) {

    }

    default boolean visit(OracleIsOfTypeExpr x) {
        return true;
    }

    default void endVisit(OracleIsOfTypeExpr x) {

    }
}
