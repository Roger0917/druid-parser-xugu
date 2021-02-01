package com.alibaba.druid.sql.dialect.xugu.visitor;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.*;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.xugu.ast.*;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguLobStorageClause;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguReturningClause;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguWithSubqueryEntry;
import com.alibaba.druid.sql.dialect.xugu.ast.expr.XuguBinaryDoubleExpr;
import com.alibaba.druid.sql.dialect.xugu.ast.expr.XuguBinaryFloatExpr;
import com.alibaba.druid.sql.dialect.xugu.ast.expr.XuguRangeExpr;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.*;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguFunctionDataType;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguProdecureDataType;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;

import java.util.List;

/**
 * @description: stament打印成sql核心处理类
 * @author: Roger
 * @create: 2020-10-28 14:28
 **/

public class XuguOutputVisitor extends SQLASTOutputVisitor implements XuguASTVisitor {

    private final boolean printPostSemi;
    {
        this.dbType = DbType.xugu;
    }

    public XuguOutputVisitor(Appendable appender){
        this(appender, true);
    }

    public XuguOutputVisitor(Appendable appender, boolean printPostSemi){
        super(appender);
        this.printPostSemi = printPostSemi;
    }

    public boolean isPrintPostSemi() {
        return printPostSemi;
    }

    private void printHints(List<SQLHint> hints) {
        if (hints.size() > 0) {
            print0("/*+ ");
            printAndAccept(hints, ", ");
            print0(" */");
        }
    }

    @Override
    public boolean visit(XuguDeleteStatement x) {
        print0(ucase ? "DELETE " : "delete ");

        SQLTableSource tableSource = x.getTableSource();
        if (x.getHints().size() > 0) {
            printAndAccept(x.getHints(), ", ");
            print(' ');
        }

        print0(ucase ? "FROM " : "from ");
        if (x.isOnly()) {
            print0(ucase ? "ONLY (" : "only (");
            x.getTableName().accept(this);
            print(')');

            printAlias(x.getAlias());
        } else {
            x.getTableSource().accept(this);
        }

        if (x.getWhere() != null) {
            println();
            this.indentCount++;
            print0(ucase ? "WHERE " : "where ");
            x.getWhere().accept(this);
            this.indentCount--;
        }

        if (x.getReturning() != null) {
            println();
            x.getReturning().accept(this);
        }

        return false;
    }

    public boolean visit(OracleIntervalExpr x) {
        SQLExpr value = x.getValue();
        if (value instanceof SQLLiteralExpr || value instanceof SQLVariantRefExpr) {
            print0(ucase ? "INTERVAL " : "interval ");
            value.accept(this);
            print(' ');
        } else {
            print('(');
            value.accept(this);
            print0(") ");
        }

        print0(x.getType().name());

        if (x.getPrecision() != null) {
            print('(');
            printExpr(x.getPrecision(), parameterized);
            if (x.getFactionalSecondsPrecision() != null) {
                print0(", ");
                print(x.getFactionalSecondsPrecision().intValue());
            }
            print(')');
        }

        if (x.getToType() != null) {
            print0(ucase ? " TO " : " to ");
            print0(x.getToType().name());
            if (x.getToFactionalSecondsPrecision() != null) {
                print('(');
                printExpr(x.getToFactionalSecondsPrecision(), parameterized);
                print(')');
            }
        }

        return false;
    }

    /*public boolean visit(OracleOuterExpr x) {
        x.getExpr().accept(this);
        print0("(+)");
        return false;
    }*/

    @Override
    public boolean visit(SQLSelect x) {
        SQLWithSubqueryClause with = x.getWithSubQuery();
        if (with != null) {
            with.accept(this);
            println();
        }

        SQLSelectQuery query = x.getQuery();
        query.accept(this);

        if (x.getRestriction() != null) {
            println();
            print("WITH ");
            x.getRestriction().accept(this);
        }

        SQLOrderBy orderBy = x.getOrderBy();
        if (orderBy != null) {
            boolean hasFirst = false;
            if (query instanceof SQLSelectQueryBlock) {
                SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock) query;
                hasFirst = queryBlock.getFirst() != null;
            }

            if (!hasFirst) {
                println();
                orderBy.accept(this);
            }
        }

        return false;
    }


    @Override
    public boolean visit(SQLSelectQueryBlock select) {
        if (select instanceof XuguSelectQueryBlock) {
            return visit((XuguSelectQueryBlock) select);
        }

        return super.visit(select);
    }

    public boolean visit(XuguSelectQueryBlock x) {
        if (isPrettyFormat() && x.hasBeforeComment()) {
            printlnComments(x.getBeforeCommentsDirect());
        }

        print0(ucase ? "SELECT " : "select ");

        if (x.getHintsSize() > 0) {
            printAndAccept(x.getHints(), ", ");
            print(' ');
        }

        if (SQLSetQuantifier.ALL == x.getDistionOption()) {
            print0(ucase ? "ALL " : "all ");
        } else if (SQLSetQuantifier.DISTINCT == x.getDistionOption()) {
            print0(ucase ? "DISTINCT " : "distinct ");
        } else if (SQLSetQuantifier.UNIQUE == x.getDistionOption()) {
            print0(ucase ? "UNIQUE " : "unique ");
        }

        printSelectList(x.getSelectList());

        if (x.getInto() != null) {
            println();
            print0(ucase ? "INTO " : "into ");
            x.getInto().accept(this);
        }

        println();
        print0(ucase ? "FROM " : "from ");
        if (x.getFrom() == null) {
            print0(ucase ? "DUAL" : "dual");
        } else {
            x.getFrom().accept(this);
        }

        if (x.getWhere() != null) {
            println();
            print0(ucase ? "WHERE " : "where ");
            x.getWhere().accept(this);
        }

        printHierarchical(x);

        if (x.getGroupBy() != null) {
            println();
            x.getGroupBy().accept(this);
        }
        SQLOrderBy orderBy = x.getOrderBy();
        if (orderBy != null) {
            println();
            orderBy.accept(this);
        }

        printFetchFirst(x);

        if (x.isForUpdate()) {
            println();
            print0(ucase ? "FOR UPDATE" : "for update");
            if (x.getForUpdateOfSize() > 0) {
                print('(');
                printAndAccept(x.getForUpdateOf(), ", ");
                print(')');
            }

            if (x.isNoWait()) {
                print0(ucase ? " NOWAIT" : " nowait");
            } else if (x.isSkipLocked()) {
                print0(ucase ? " SKIP LOCKED" : " skip locked");
            } else if (x.getWaitTime() != null) {
                print0(ucase ? " WAIT " : " wait ");
                x.getWaitTime().accept(this);
            }
        }

        return false;
    }

    public boolean visit(XuguSelectSubqueryTableSource x) {
        print('(');
        this.indentCount++;
        println();
        x.getSelect().accept(this);
        this.indentCount--;
        println();
        print(')');
        if ((x.getAlias() != null) && (x.getAlias().length() != 0)) {
            print(' ');
            print0(x.getAlias());
        }

        return false;
    }

    /*public boolean visit(OracleSelectTableReference x) {
        if (x.isOnly()) {
            print0(ucase ? "ONLY (" : "only (");
            printTableSourceExpr(x.getExpr());

            if (x.getPartition() != null) {
                print(' ');
                x.getPartition().accept(this);
            }

            print(')');
        } else {
            printTableSourceExpr(x.getExpr());

            if (x.getPartition() != null) {
                print(' ');
                x.getPartition().accept(this);
            }
        }

        if (x.getHints().size() > 0) {
            this.printHints(x.getHints());
        }

        if (x.getSampleClause() != null) {
            print(' ');
            x.getSampleClause().accept(this);
        }

        if (x.getPivot() != null) {
            println();
            x.getPivot().accept(this);
        }

        printFlashback(x.getFlashback());

        printAlias(x.getAlias());

        return false;
    }*/

    @Override
    public boolean visit(XuguUpdateStatement x) {
        print0(ucase ? "UPDATE " : "update ");

        if (x.getHints().size() > 0) {
            printAndAccept(x.getHints(), ", ");
            print(' ');
        }

        if (x.isOnly()) {
            print0(ucase ? "ONLY (" : "only (");
            x.getTableSource().accept(this);
            print(')');
        } else {
            x.getTableSource().accept(this);
        }

        printAlias(x.getAlias());

        println();

        print0(ucase ? "SET " : "set ");
        for (int i = 0, size = x.getItems().size(); i < size; ++i) {
            if (i != 0) {
                print0(", ");
            }
            x.getItems().get(i).accept(this);
        }

        if (x.getWhere() != null) {
            println();
            print0(ucase ? "WHERE " : "where ");
            this.indentCount++;
            x.getWhere().accept(this);
            this.indentCount--;
        }

        if (x.getReturning().size() > 0) {
            println();
            print0(ucase ? "RETURNING " : "returning ");
            printAndAccept(x.getReturning(), ", ");
            print0(ucase ? " INTO " : " into ");
            printAndAccept(x.getReturningInto(), ", ");
        }

        return false;
    }


    public boolean visit(XuguWithSubqueryEntry x) {
        print0(x.getAlias());

        if (x.getColumns().size() > 0) {
            print0(" (");
            printAndAccept(x.getColumns(), ", ");
            print(')');
        }

        print0(ucase ? " AS " : " as ");
        print('(');
        this.indentCount++;
        println();
        x.getSubQuery().accept(this);
        this.indentCount--;
        println();
        print(')');

        if (x.getSearchClause() != null) {
            println();
            x.getSearchClause().accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(SearchClause x) {
        print0(ucase ? "SEARCH " : "search ");
        print0(x.getType().name());
        print0(ucase ? " FIRST BY " : " first by ");
        printAndAccept(x.getItems(), ", ");
        print0(ucase ? " SET " : " set ");
        x.getOrderingColumn().accept(this);

        return false;
    }


   @Override
    public boolean visit(XuguBinaryFloatExpr x) {
        print0(x.getValue().toString());
        print('F');
        return false;
    }

    @Override
    public boolean visit(XuguBinaryDoubleExpr x) {
        print0(x.getValue().toString());
        print('D');
        return false;
    }

    @Override
    public boolean visit(XuguReturningClause x) {
        print0(ucase ? "RETURNING " : "returning ");
        printAndAccept(x.getItems(), ", ");
        print0(ucase ? " INTO " : " into ");
        printAndAccept(x.getValues(), ", ");

        return false;
    }

    @Override
    public boolean visit(XuguInsertStatement x) {
        //visit((SQLInsertStatement) x);

        print0(ucase ? "INSERT " : "insert ");

        if (x.getHints().size() > 0) {
            printAndAccept(x.getHints(), ", ");
            print(' ');
        }

        print0(ucase ? "INTO " : "into ");

        x.getTableSource().accept(this);

        printInsertColumns(x.getColumns());

        if (x.getValues() != null&&(x.getValuesList().size()==1)) {
            println();
            print0(ucase ? "VALUES " : "values ");
            x.getValues().accept(this);
        }else if(x.getValues() != null&&(x.getValuesList().size()>1)){
            //虚谷Insert 多values解析
            println();
            print0(ucase ? "VALUES " : "values ");
            x.getValuesList().stream().forEach(m->m.accept(this));
        }
        else {
            if (x.getQuery() != null) {
                println();
                x.getQuery().accept(this);
            }
        }

        if (x.getReturning() != null) {
            println();
            x.getReturning().accept(this);
        }

        return false;
    }

    /*@Override
    public boolean visit(OracleDatetimeExpr x) {
        x.getExpr().accept(this);
        SQLExpr timeZone = x.getTimeZone();

        if (timeZone instanceof SQLIdentifierExpr) {
            if (((SQLIdentifierExpr) timeZone).getName().equalsIgnoreCase("LOCAL")) {
                print0(ucase ? " AT LOCAL" : "alter session set ");
                return false;
            }
        }

        print0(ucase ? " AT TIME ZONE " : " at time zone ");
        timeZone.accept(this);

        return false;
    }

    @Override
    public boolean visit(OracleSysdateExpr x) {
        print0(ucase ? "SYSDATE" : "sysdate");
        if (x.getOption() != null) {
            print('@');
            print0(x.getOption());
        }
        return false;
    }*/

    @Override
    public boolean visit(XuguExceptionStatement.Item x) {
        print0(ucase ? "WHEN " : "when ");
        x.getWhen().accept(this);
        print0(ucase ? " THEN" : " then");

        this.indentCount++;
        if (x.getStatements().size() > 1) {
            println();
        } else {
            if (x.getStatements().size() == 1
                    && x.getStatements().get(0) instanceof SQLIfStatement) {
                println();
            } else {
                print(' ');
            }
        }

        for (int i = 0, size = x.getStatements().size(); i < size; ++i) {
            if (i != 0 && size > 1) {
                println();
            }
            SQLStatement stmt = x.getStatements().get(i);
            stmt.accept(this);
        }

        this.indentCount--;
        return false;
    }

    @Override
    public boolean visit(XuguExceptionStatement x) {
        print0(ucase ? "EXCEPTION" : "exception");
        this.indentCount++;
        List<XuguExceptionStatement.Item> items = x.getItems();
        for (int i = 0, size = items.size(); i < size; ++i) {
            println();
            XuguExceptionStatement.Item item = items.get(i);
            item.accept(this);
        }
        this.indentCount--;
        return false;
    }

    /*@Override
    public boolean visit(OracleArgumentExpr x) {
        print0(x.getArgumentName());
        print0(" => ");
        x.getValue().accept(this);
        return false;
    }

    @Override
    public boolean visit(OracleSetTransactionStatement x) {
        if (x.isReadOnly()) {
            print0(ucase ? "SET TRANSACTION READ ONLY" : "set transaction read only");
        } else {
            print0(ucase ? "SET TRANSACTION" : "set transaction");
        }

        SQLExpr name = x.getName();
        if (name != null) {
            print0(ucase ? " NAME " : " name ");
            name.accept(this);
        }
        return false;
    }*/

    @Override
    public boolean visit(XuguExplainStatement x) {
        print0(ucase ? "EXPLAIN PLAN" : "explain plan");
        this.indentCount++;
        println();
        if (x.getStatementId() != null) {
            print0(ucase ? "SET STATEMENT_ID = " : "set statement_id = ");
            x.getStatementId().accept(this);
            println();
        }

        if (x.getInto() != null) {
            print0(ucase ? "INTO " : "into ");
            x.getInto().accept(this);
            println();
        }

        print0(ucase ? "FOR" : "for");
        println();
        x.getStatement().accept(this);

        this.indentCount--;
        return false;
    }

    @Override
    public boolean visit(SQLAlterProcedureStatement x) {
        print0(ucase ? "ALTER PROCEDURE " : "alter procedure ");
        x.getName().accept(this);
        if (x.isCompile()) {
            print0(ucase ? " COMPILE" : " compile");
        }
        if (x.isReuseSettings()) {
            print0(ucase ? " REUSE SETTINGS" : " reuse settings");
        }
        return false;
    }

    @Override
    public boolean visit(XuguAlterTableDropPartition x) {
        print0(ucase ? "DROP PARTITION " : "drop partition ");
        x.getName().accept(this);
        return false;
    }

    @Override
    public boolean visit(SQLAlterTableStatement x) {
        if (x.getItems().size() == 1) {
            SQLAlterTableItem item = x.getItems().get(0);
            if (item instanceof SQLAlterTableRename) {
                SQLExpr to = ((SQLAlterTableRename) item).getTo().getExpr();

                print0(ucase ? "RENAME " : "rename ");
                x.getName().accept(this);
                print0(ucase ? " TO " : "to ");
                to.accept(this);
                return false;
            }
        }

        print0(ucase ? "ALTER TABLE " : "alter table ");
        printTableSourceExpr(x.getName());
        this.indentCount++;
        for (SQLAlterTableItem item : x.getItems()) {
            println();
            item.accept(this);
        }
        if (x.isUpdateGlobalIndexes()) {
            println();
            print0(ucase ? "UPDATE GLOABL INDEXES" : "update gloabl indexes");
        }
        this.indentCount--;
        return false;
    }

    @Override
    public boolean visit(XuguAlterTableTruncatePartition x) {
        print0(ucase ? "TRUNCATE PARTITION " : "truncate partition ");
        x.getName().accept(this);
        return false;
    }

    @Override
    public boolean visit(XuguCreateIndexStatement x) {
        print0(ucase ? "CREATE " : "create ");
        if (x.getType() != null) {
            print0(x.getType());
            print(' ');
        }

        print0(ucase ? "INDEX " : "index ");

        x.getName().accept(this);
        print0(ucase ? " ON " : " on ");

        if (x.isCluster()) {
            print0(ucase ? "CLUSTER " : "cluster ");
        }

        x.getTable().accept(this);

        List<SQLSelectOrderByItem> items = x.getItems();
        if (items.size() > 0) {
            print('(');
            printAndAccept(items, ", ");
            print(')');
        }

        if (x.isIndexOnlyTopLevel()) {
            println();
            print0(ucase ? "INDEX ONLY TOPLEVEL" : "index only toplevel");
        }

        if (x.isComputeStatistics()) {
            println();
            print0(ucase ? "COMPUTE STATISTICS" : "compute statistics");
        }

        if (x.isReverse()) {
            println();
            print0(ucase ? "REVERSE" : "reverse");
        }

        //this.printOracleSegmentAttributes(x);

        if (x.isOnline()) {
            print0(ucase ? " ONLINE" : " online");
        }

        if (x.isNoParallel()) {
            print0(ucase ? " NOPARALLEL" : " noparallel");
        } else if (x.getParallel() != null) {
            print0(ucase ? " PARALLEL " : " parallel ");
            x.getParallel().accept(this);
        }

        Boolean sort = x.getSort();
        if (sort != null) {
            if (sort.booleanValue()) {
                print0(ucase ? " SORT" : " sort");
            } else {
                print0(ucase ? " NOSORT" : " nosort");
            }
        }

        if (x.getLocalPartitions().size() > 0) {
            println();
            print0(ucase ? "LOCAL (" : "local (");
            this.indentCount++;
            println();
            printlnAndAccept(x.getLocalPartitions(), ",");
            this.indentCount--;
            println();
            print(')');
        } else if (x.isLocal()) {
            print0(ucase ? " LOCAL" : " local");
        }

        List<SQLName> localStoreIn = x.getLocalStoreIn();
        if (localStoreIn.size() > 0) {
            print0(ucase ? " STORE IN (" : " store in (");
            printAndAccept(localStoreIn, ", ");
            print(')');
        }

        List<SQLPartitionBy> globalPartitions = x.getGlobalPartitions();
        if (globalPartitions.size() > 0) {
            for (SQLPartitionBy globalPartition : globalPartitions) {
                println();
                print0(ucase ? "GLOBAL " : "global ");
                print0(ucase ? "PARTITION BY " : "partition by ");
                globalPartition.accept(this);
            }

        } else {
            if (x.isGlobal()) {
                print0(ucase ? " GLOBAL" : " global");
            }
        }

        return false;
    }

    @Override
    public boolean visit(XuguForStatement x) {
        boolean all = x.isAll();
        if (all) {
            print0(ucase ? "FORALL " : "forall ");
        } else {
            print0(ucase ? "FOR " : "for ");
        }
        x.getIndex().accept(this);
        print0(ucase ? " IN " : " in ");
        if(x.getRange() instanceof XuguRangeExpr){
            XuguRangeExpr rangeExpr = (XuguRangeExpr) x.getRange();
            if(rangeExpr.getLowBound() instanceof SQLIntegerExpr&&rangeExpr.getUpBound() instanceof SQLIntegerExpr){
                if(((SQLIntegerExpr) rangeExpr.getLowBound()).getNumber()instanceof Integer&&((SQLIntegerExpr) rangeExpr.getUpBound()).getNumber()instanceof Integer){
                    Integer lowBound = (Integer) ((SQLIntegerExpr) rangeExpr.getLowBound()).getNumber();
                    Integer upBound = (Integer) ((SQLIntegerExpr) rangeExpr.getUpBound()).getNumber();
                    if(lowBound>upBound){
                        print0(" REVERSE ");
                    }
                }
            }
        }

        SQLExpr range = x.getRange();
        range.accept(this);

        if (!all) {
            println();
            print0(ucase ? "LOOP" : "loop");
        }
        this.indentCount++;
        println();

        for (int i = 0, size = x.getStatements().size(); i < size; ++i) {
            SQLStatement stmt = x.getStatements().get(i);
            stmt.accept(this);
            if (!all) {
                if (i != size - 1) {
                    println();
                }
            }
        }

        this.indentCount--;
        if (!all) {
            println();
            print0(ucase ? "END LOOP" : "end loop");
            SQLName endLabel = x.getEndLabel();
            if (endLabel != null) {
                print(' ');
                endLabel.accept(this);
            }
        }
        return false;
    }

    @Override
    public boolean visit(SQLIfStatement.Else x) {
        print0(ucase ? "ELSE" : "else");
        this.indentCount++;
        println();

        for (int i = 0, size = x.getStatements().size(); i < size; ++i) {
            if (i != 0) {
                println();
            }
            SQLStatement item = x.getStatements().get(i);
            item.accept(this);
        }

        this.indentCount--;
        return false;
    }

    @Override
    public boolean visit(SQLIfStatement.ElseIf x) {
        print0(ucase ? "ELSE IF " : "else if ");
        x.getCondition().accept(this);
        print0(ucase ? " THEN" : " then");
        this.indentCount++;

        for (int i = 0, size = x.getStatements().size(); i < size; ++i) {
            println();
            SQLStatement item = x.getStatements().get(i);
            item.accept(this);
        }

        this.indentCount--;
        return false;
    }

    @Override
    public boolean visit(SQLIfStatement x) {
        print0(ucase ? "IF " : "if ");
        int lines = this.lines;
        this.indentCount++;
        x.getCondition().accept(this);
        this.indentCount--;

        if (lines != this.lines) {
            println();
        } else {
            print(' ');
        }
        print0(ucase ? "THEN" : "then");

        this.indentCount++;
        for (int i = 0, size = x.getStatements().size(); i < size; ++i) {
            println();
            SQLStatement item = x.getStatements().get(i);
            item.accept(this);
        }
        this.indentCount--;

        for (SQLIfStatement.ElseIf elseIf : x.getElseIfList()) {
            println();
            elseIf.accept(this);
        }

        if (x.getElseItem() != null) {
            println();
            x.getElseItem().accept(this);
        }
        println();
        print0(ucase ? "END IF" : "end if");
        return false;
    }

    @Override
    public boolean visit(XuguRangeExpr x) {
        x.getLowBound().accept(this);
        print0("..");
        x.getUpBound().accept(this);
        return false;
    }

    @Override
    public boolean visit(XuguPrimaryKey x) {
        if (x.getName() != null) {
            print0(ucase ? "CONSTRAINT " : "constraint ");
            x.getName().accept(this);
            print(' ');
        }
        print0(ucase ? "PRIMARY KEY (" : "primary key (");
        printAndAccept(x.getColumns(), ", ");
        print(')');

        Boolean rely = x.getRely();
        if (rely != null) {
            if (rely.booleanValue()) {
                print0(ucase ? " RELY" : " rely");
            }
        }

        printConstraintState(x);

        Boolean validate = x.getValidate();
        if (validate != null) {
            if (validate.booleanValue()) {
                print0(ucase ? " VALIDATE" : " validate");
            } else {
                print0(ucase ? " NOVALIDATE" : " novalidate");
            }
        }

        return false;
    }

    protected void printConstraintState(XuguConstraint x) {
        this.indentCount++;
        Boolean enable = x.getEnable();
        if (enable != null) {
            if (enable.booleanValue()) {
                print0(ucase ? " ENABLE" : " enable");
            } else {
                print0(ucase ? " DISABLE" : " disable");
            }
        }

        this.indentCount--;
    }

    @Override
    public boolean visit(XuguCreateTableStatement x) {
        printCreateTable(x, false);

        if (x.getOf() != null) {
            println();
            print0(ucase ? "OF " : "of ");
            x.getOf().accept(this);
        }


        if (x.getIncluding().size() > 0) {
            print0(ucase ? " INCLUDING " : " including ");
            printAndAccept(x.getIncluding(), ", ");
            print0(ucase ? " OVERFLOW " : " overflow ");
        }

        //printOracleSegmentAttributes(x);

        if (x.isInMemoryMetadata()) {
            println();
            print0(ucase ? "IN_MEMORY_METADATA" : "in_memory_metadata");
        }

        if (x.isCursorSpecificSegment()) {
            println();
            print0(ucase ? "CURSOR_SPECIFIC_SEGMENT" : "cursor_specific_segment");
        }

        if (x.getParallel() == Boolean.TRUE) {
            println();
            print0(ucase ? "PARALLEL" : "parallel");

            final SQLExpr parallelValue = x.getParallelValue();
            if (parallelValue != null) {
                print(' ');
                printExpr(parallelValue);
            }
        } else if (x.getParallel() == Boolean.FALSE) {
            println();
            print0(ucase ? "NOPARALLEL" : "noparallel");
        }

        if (x.getCache() == Boolean.TRUE) {
            println();
            print0(ucase ? "CACHE" : "cache");
        } else if (x.getCache() == Boolean.FALSE) {
            println();
            print0(ucase ? "NOCACHE" : "nocache");
        }

        if (x.getLobStorage() != null) {
            println();
            x.getLobStorage().accept(this);
        }

        if (x.isOnCommitPreserveRows()) {
            println();
            print0(ucase ? "ON COMMIT PRESERVE ROWS" : "on commit preserve rows");
        } else if (x.isOnCommitDeleteRows()) {
            println();
            print0(ucase ? "ON COMMIT DELETE ROWS" : "on commit delete rows");
        }

        if (x.isMonitoring()) {
            println();
            print0(ucase ? "MONITORING" : "monitoring");
        }

        if (x.getPartitioning() != null) {
            println();
            print0(ucase ? "PARTITION BY " : "partition by ");
            x.getPartitioning().accept(this);
        }

        if (x.getCluster() != null) {
            println();
            print0(ucase ? "CLUSTER " : "cluster ");
            x.getCluster().accept(this);
            print0(" (");
            printAndAccept(x.getClusterColumns(), ",");
            print0(")");
        }

        final SQLSelect select = x.getSelect();
        if (select != null) {
            println();
            print0(ucase ? "AS" : "as");
            println();
            select.accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(XuguGotoStatement x) {
        print0(ucase ? "GOTO " : "GOTO ");
        x.getLabel().accept(this);
        return false;
    }

    @Override
    public boolean visit(XuguAlterTriggerStatement x) {
        print0(ucase ? "ALTER TRIGGER " : "alter trigger ");
        x.getName().accept(this);
        if (x.getEnable() != null) {
            if (x.getEnable().booleanValue()) {
                print0(ucase ? "ENABLE" : "enable");
            } else {
                print0(ucase ? "DISABLE" : "disable");
            }
        }
        return false;
    }
//    @Override
//    public boolean visit(AsOfSnapshotClause x) {
//        print0(ucase ? "AS OF SNAPSHOT(" : "as of snapshot(");
//        x.getExpr().accept(this);
//        print(')');
//        return false;
//    }
//
//    @Override
//    public void endVisit(AsOfSnapshotClause x) {
//
//    }
    @Override
    public boolean visit(SQLTruncateStatement x) {
        print0(ucase ? "TRUNCATE TABLE " : "truncate table ");
        printAndAccept(x.getTableSources(), ", ");

        if (x.isPurgeSnapshotLog()) {
            print0(ucase ? " PURGE SNAPSHOT LOG" : " purge snapshot log");
        }
        return false;
    }

    @Override
    public boolean visit(XuguExitStatement x) {
        print0(ucase ? "EXIT" : "exit");

        if (x.getLabel() != null) {
            print(' ');
            print0(x.getLabel());
        }

        if (x.getWhen() != null) {
            print0(ucase ? " WHEN " : " when ");
            x.getWhen().accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(XuguContinueStatement x) {
        print0(ucase ? "CONTINUE" : "continue");

        String label = x.getLabel();
        if (label != null) {
            print(' ');
            print0(label);
        }

        if (x.getWhen() != null) {
            print0(ucase ? " WHEN " : " when ");
            x.getWhen().accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(XuguRaiseStatement x) {
        print0(ucase ? "RAISE" : "raise");
        if (x.getException() != null) {
            print(' ');
            x.getException().accept(this);
        }
        print(';');
        return false;
    }

    @Override
    public boolean visit(SQLSavePointStatement x) {
        print0(ucase ? "SAVEPOINT" : "savepoint");
        if (x.getName() != null) {
            print0(ucase ? " TO " : " to ");
            x.getName().accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(SQLCreateFunctionStatement x) {
        boolean create = x.isCreate();
        if (!create) {
            print0(ucase ? "FUNCTION " : "function ");
        } else if (x.isOrReplace()) {
            print0(ucase ? "CREATE OR REPLACE FUNCTION " : "create or replace function ");
        } else {
            print0(ucase ? "CREATE FUNCTION " : "create function ");
        }
        x.getName().accept(this);

        int paramSize = x.getParameters().size();

        if (paramSize > 0) {
            print0(" (");
            this.indentCount++;
            println();

            for (int i = 0; i < paramSize; ++i) {
                if (i != 0) {
                    print0(", ");
                    println();
                }
                SQLParameter param = x.getParameters().get(i);
                param.accept(this);
            }

            this.indentCount--;
            println();
            print(')');
        }

        String wrappedSource = x.getWrappedSource();
        if (wrappedSource != null) {
            print0(ucase ? " WRAPPED " : " wrapped ");
            print0(wrappedSource);

            if (x.isAfterSemi()) {
                print(';');
            }
            return false;
        }

        println();
        print(ucase ? "RETURN " : "return ");
        x.getReturnDataType().accept(this);

        if (x.isPipelined()) {
            print(ucase ? "PIPELINED " : "pipelined ");
        }

        if (x.isDeterministic()) {
            print(ucase ? "DETERMINISTIC " : "deterministic ");
        }

        SQLName authid = x.getAuthid();
        if (authid != null) {
            print(ucase ? " AUTHID " : " authid ");
            authid.accept(this);
        }

        SQLStatement block = x.getBlock();

        if (block != null && !create) {
            println();
            println("IS");
        } else {
            println();
            if (block instanceof SQLBlockStatement) {
                SQLBlockStatement blockStatement = (SQLBlockStatement) block;
                if (blockStatement.getParameters().size() > 0 || authid != null) {
                    println(ucase ? "AS" : "as");
                }
            }
        }

        String javaCallSpec = x.getJavaCallSpec();
        if (javaCallSpec != null) {
            print0(ucase ? "LANGUAGE JAVA NAME '" : "language java name '");
            print0(javaCallSpec);
            print('\'');
            return false;
        }

        if (x.isParallelEnable()) {
            print0(ucase ? "PARALLEL_ENABLE" : "parallel_enable");
            println();
        }

        if (x.isAggregate()) {
            print0(ucase ? "AGGREGATE" : "aggregate");
            println();
        }

        SQLName using = x.getUsing();
        if (using != null) {
            print0(ucase ? "USING " : "using ");
            using.accept(this);
        }

        if (block != null) {
            block.accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(SQLCharacterDataType x) {
        print0(x.getName());
        final List<SQLExpr> arguments = x.getArguments();
        if (arguments.size() > 0) {
            print('(');
            SQLExpr arg0 = arguments.get(0);
            printExpr(arg0, false);
            if (x.getCharType() != null) {
                print(' ');
                print0(x.getCharType());
            }
            print(')');
        }
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalYear x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

       //print0(ucase ? " TO MONTH" : " to month");

        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalDay x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        /*print0(ucase ? " TO SECOND" : " to second");

        if (x.getFractionalSeconds().size() > 0) {
            print('(');
            x.getFractionalSeconds().get(0).accept(this);
            print(')');
        }*/

        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalMonth x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO MONTH" : " to month");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalHour x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO MONTH" : " to month");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalMinute x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO MONTH" : " to month");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalSecond x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            //x.getArguments().get(0).accept(this);
            //x.getArguments().stream().forEach(sqlExpr -> sqlExpr.accept(this));//Xugu Interval to Second支持传入精度和标度
            for(int i=0;i<x.getArguments().size();i++){
                if(i!=x.getArguments().size()-1){
                    x.getArguments().get(i).accept(this);
                    print(",");
                }else{
                    x.getArguments().get(i).accept(this);
                }
            }
            print(')');
        }

        //print0(ucase ? " TO MONTH" : " to month");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalYearToMonth x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO MONTH" : " to month");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalDayToHour x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO HOUR" : " to hour");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalDayToMinute x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO MINUTE" : " to minute");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalDayToSecond x) {
        //print0(x.getName());
        print0("INTERVAL DAY");
        if (x.getArguments().size() > 0) {

            print('(');
            x.getArguments().get(0).accept(this);
            //x.getArguments().stream().forEach(sqlExpr -> sqlExpr.accept(this));
            print(')');
        }
        print0(ucase ? " TO SECOND" : " to second");
        if (x.getFractionalSeconds().size() > 0) {
            print('(');
            x.getFractionalSeconds().get(0).accept(this);
            print(')');
        }

        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalHourToMinute x) {
        print0(x.getName());
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        //print0(ucase ? " TO MINUTE" : " to minute");
        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalHourToSecond x) {
        //print0(x.getName());
        print0("INTERVAL HOUR");
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        print0(ucase ? " TO SECOND" : " to second");

        if (x.getFractionalSeconds().size() > 0) {
            print('(');
            x.getFractionalSeconds().get(0).accept(this);
            print(')');
        }

        return false;
    }

    @Override
    public boolean visit(XuguDataTypeIntervalMinuteToSecond x) {
        //print0(x.getName());
        print0("INTERVAL MINUTE");
        if (x.getArguments().size() > 0) {
            print('(');
            x.getArguments().get(0).accept(this);
            print(')');
        }

        print0(ucase ? " TO SECOND" : " to second");
        if (x.getFractionalSeconds().size() > 0) {
            print('(');
            x.getFractionalSeconds().get(0).accept(this);
            print(')');
        }
        return false;
    }

    @Override
    public boolean visit(XuguLobStorageClause x) {
        print0(ucase ? "LOB (" : "lob (");
        printAndAccept(x.getItems(), ",");
        print0(ucase ? ") STORE AS" : ") store as");


        if (x.isSecureFile()) {
            print0(ucase ? " SECUREFILE" : " securefile");
        }

        if (x.isBasicFile()) {
            print0(ucase ? " BASICFILE" : " basicfile");
        }

        SQLName segementName = x.getSegementName();
        if (segementName != null) {
            print(' ');
            segementName.accept(this);
        }
        print0(" (");
        this.indentCount++;
        //printOracleSegmentAttributes(x);

        if (x.getEnable() != null) {
            println();
            if (x.getEnable().booleanValue()) {
                print0(ucase ? "ENABLE STORAGE IN ROW" : "enable storage in row");
            } else {
                print0(ucase ? "DISABLE STORAGE IN ROW" : "disable storage in row");
            }
        }

        if (x.getChunk() != null) {
            println();
            print0(ucase ? "CHUNK " : "chunk ");
            x.getChunk().accept(this);
        }

        if (x.getCache() != null) {
            println();
            if (x.getCache().booleanValue()) {
                print0(ucase ? "CACHE" : "cache");
            } else {
                print0(ucase ? "NOCACHE" : "nocache");
            }
        }

        if (x.getKeepDuplicate() != null) {
            println();
            if (x.getKeepDuplicate().booleanValue()) {
                print0(ucase ? "KEEP_DUPLICATES" : "keep_duplicates");
            } else {
                print0(ucase ? "DEDUPLICATE" : "deduplicate");
            }
        }

        if (x.isRetention()) {
            println();
            print0(ucase ? "RETENTION" : "retention");
        }

        this.indentCount--;
        println();
        print(')');
        return false;
    }

    @Override
    public boolean visit(XuguUnique x) {
        visit((SQLUnique) x);

        printConstraintState(x);
        return false;
    }

    @Override
    public boolean visit(XuguForeignKey x) {
        visit((SQLForeignKeyImpl) x);
        printConstraintState(x);
        return false;
    }

    @Override
    public boolean visit(XuguCheck x) {
        visit((SQLCheck) x);

        printConstraintState(x);
        return false;
    }

    @Override
    protected void printCascade() {
        print0(ucase ? " CASCADE CONSTRAINTS" : " cascade constraints");
    }

    @Override
    public boolean visit(SQLCharExpr x, boolean parameterized) {
        if (x.getText() != null && x.getText().length() == 0) {
            print0(ucase ? "NULL" : "null");
        } else {
            super.visit(x, parameterized);
        }

        return false;
    }


    @Override
    public boolean visit(XuguCreatePackageStatement x) {
        if (x.isOrReplace()) {
            print0(ucase ? "CREATE OR REPLACE PACKAGE " : "create or replace procedure ");
        } else {
            print0(ucase ? "CREATE PACKAGE " : "create procedure ");
        }

        if (x.isBody()) {
            print0(ucase ? "BODY " : "body ");
        }

        x.getName().accept(this);

        if (x.isBody()) {
            println();
            print0(ucase ? "BEGIN" : "begin");
        }

        this.indentCount++;

        List<SQLStatement> statements = x.getStatements();
        for (int i = 0, size = statements.size(); i < size; ++i) {
            println();
            SQLStatement stmt = statements.get(i);
            stmt.accept(this);
        }

        this.indentCount--;

        if (x.isBody() || statements.size() > 0) {
            println();
            print0(ucase ? "END " : "end ");
            x.getName().accept(this);
            print(';');
        }

        return false;
    }

    @Override
    public boolean visit(XuguExecuteImmediateStatement x) {
        print0(ucase ? "EXECUTE IMMEDIATE " : "execute immediate ");
        x.getDynamicSql().accept(this);

        List<SQLExpr> into = x.getInto();
        if (into.size() > 0) {
            print0(ucase ? " INTO " : " into ");
            printAndAccept(into, ", ");
        }

        List<SQLArgument> using = x.getArguments();
        if (using.size() > 0) {
            print0(ucase ? " USING " : " using ");
            printAndAccept(using, ", ");
        }

        List<SQLExpr> returnInto = x.getReturnInto();
        if (returnInto.size() > 0) {
            print0(ucase ? " RETURNNING INTO " : " returnning into ");
            printAndAccept(returnInto, ", ");
        }
        return false;
    }

    @Override
    public boolean visit(XuguCreateSynonymStatement x) {
        if (x.isOrReplace()) {
            print0(ucase ? "CREATE OR REPLACE " : "create or replace ");
        } else {
            print0(ucase ? "CREATE " : "create ");
        }

        if (x.isPublic()) {
            print0(ucase ? "PUBLIC " : "public ");
        }

        print0(ucase ? "SYNONYM " : "synonym ");

        x.getName().accept(this);

        print0(ucase ? " FOR " : " for ");
        x.getObject().accept(this);

        return false;
    }

    @Override
    public boolean visit(XuguCreateTypeStatement x) {
        if (x.isOrReplace()) {
            print0(ucase ? "CREATE OR REPLACE TYPE " : "create or replace type ");
        } else {
            print0(ucase ? "CREATE TYPE " : "create type ");
        }

        if (x.isBody()) {
            print0(ucase ? "BODY " : "body ");
        }

        x.getName().accept(this);

        SQLName under = x.getUnder();
        if (under != null) {
            print0(ucase ? " UNDER " : " under ");
            under.accept(this);
        }

        SQLName authId = x.getAuthId();
        if (authId != null) {
            print0(ucase ? " AUTHID " : " authid ");
            authId.accept(this);
        }

        if (x.isForce()) {
            print0(ucase ? "FORCE " : "force ");
        }

        List<SQLParameter> parameters = x.getParameters();
        SQLDataType tableOf = x.getTableOf();

        if (x.isObject()) {
            print0(" AS OBJECT");
        }

        if (parameters.size() > 0) {
            if (x.isParen()) {
                print(" (");
            } else {
                print0(ucase ? " IS" : " is");
            }
            indentCount++;
            println();

            for (int i = 0; i < parameters.size(); ++i) {
                SQLParameter param = parameters.get(i);
                param.accept(this);

                SQLDataType dataType = param.getDataType();

                if (i < parameters.size() - 1) {
                    if (dataType instanceof XuguFunctionDataType
                            && ((XuguFunctionDataType) dataType).getBlock() != null) {
                        // skip
                        println();
                    } else  if (dataType instanceof XuguProdecureDataType
                            && ((XuguProdecureDataType) dataType).getBlock() != null) {
                        // skip
                        println();
                    } else {
                        println(", ");
                    }
                }
            }

            indentCount--;
            println();

            if (x.isParen()) {
                print0(")");
            } else {
                print0("END");
            }
        } else if (tableOf != null) {
            print0(ucase ? " AS TABLE OF " : " as table of ");
            tableOf.accept(this);
        } else if (x.getVarraySizeLimit() != null) {
            print0(ucase ? " VARRAY (" : " varray (");
            x.getVarraySizeLimit().accept(this);
            print0(ucase ? ") OF " : ") of ");
            x.getVarrayDataType().accept(this);
        }

        Boolean isFinal = x.getFinal();
        if (isFinal != null) {
            if (isFinal.booleanValue()) {
                print0(ucase ? " FINAL" : " final");
            } else {
                print0(ucase ? " NOT FINAL" : " not final");
            }
        }

        Boolean instantiable = x.getInstantiable();
        if (instantiable != null) {
            if (instantiable.booleanValue()) {
                print0(ucase ? " INSTANTIABLE" : " instantiable");
            } else {
                print0(ucase ? " NOT INSTANTIABLE" : " not instantiable");
            }
        }

        String wrappedSource = x.getWrappedSource();
        if (wrappedSource != null) {
            print0(ucase ? " WRAPPED" : " wrapped");
            print0(wrappedSource);
        }

        return false;
    }

    @Override
    public boolean visit(OracleIsOfTypeExpr x) {
        printExpr(x.getExpr());
        print0(ucase ? " IS OF TYPE (" : " is of type (");

        List<SQLExpr> types = x.getTypes();
        for (int i = 0, size = types.size(); i < size; ++i) {
            if (i != 0) {
                print0(", ");
            }
            SQLExpr type = types.get(i);
            if (Boolean.TRUE == type.getAttribute("ONLY")) {
                print0(ucase ? "ONLY " : "only ");
            }
            type.accept(this);
        }

        print(')');
        return false;
    }

    @Override
    public boolean visit(SQLSubPartition x) {
        super.visit(x);
        incrementIndent();
        //printOracleSegmentAttributes(x);
        decrementIndent();
        return false;
    }

    @Override
    public boolean visit(SQLPartitionValue x) {
        super.visit(x);
        incrementIndent();
        //printOracleSegmentAttributes(x);
        decrementIndent();
        return false;
    }
}
