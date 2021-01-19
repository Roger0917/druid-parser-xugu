package com.alibaba.druid.sql.dialect.xugu.visitor;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalDay;
import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleLobStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleWithSubqueryEntry;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleIsOfTypeExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleOuterExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.expr.OracleSysdateExpr;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguLobStorageClause;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguWithSubqueryEntry;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.*;
import com.alibaba.druid.sql.repository.SchemaRepository;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.FnvHash;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-03 09:36
 **/

public class XuguSchemaStatVisitor extends SchemaStatVisitor implements XuguASTVisitor {

    public XuguSchemaStatVisitor(){
        this(new ArrayList<Object>());
    }

    public XuguSchemaStatVisitor(SchemaRepository repository) {
        super (repository);
    }

    public XuguSchemaStatVisitor(List<Object> parameters){
        super(DbType.xugu, parameters);
    }

    protected TableStat.Column getColumn(SQLExpr expr) {
        if (expr instanceof OracleOuterExpr) {
            expr = ((OracleOuterExpr) expr).getExpr();
        }

        return super.getColumn(expr);
    }

    /*public boolean visit(XuguSelectTableReference x) {
        SQLExpr expr = x.getExpr();
        TableStat stat = getTableStat(x);

        if (expr instanceof SQLName) {
            if (((SQLName) expr).nameHashCode64() == FnvHash.Constants.DUAL) {
                return false;
            }

            if (expr instanceof SQLPropertyExpr) {
                SQLPropertyExpr propertyExpr = (SQLPropertyExpr) expr;
                if (isSubQueryOrParamOrVariant(propertyExpr)) {
                    return false;
                }
            } else if (expr instanceof SQLIdentifierExpr) {
                SQLIdentifierExpr identifierExpr = (SQLIdentifierExpr) expr;
                if (isSubQueryOrParamOrVariant(identifierExpr)) {
                    return false;
                }
            }

            TableStat.Mode mode = getMode();
            switch (mode) {
                case Delete:
                    stat.incrementDeleteCount();
                    break;
                case Insert:
                    stat.incrementInsertCount();
                    break;
                case Update:
                    stat.incrementUpdateCount();
                    break;
                case Select:
                    stat.incrementSelectCount();
                    break;
                case Merge:
                    stat.incrementMergeCount();
                    break;
                default:
                    break;
            }

            return false;
        }

        // accept(x.getExpr());

        return false;
    }*/

    public boolean visit(XuguUpdateStatement x) {
        if (repository != null
                && x.getParent() == null) {
            repository.resolve(x);
        }

        setMode(x, TableStat.Mode.Update);

        SQLTableSource tableSource = x.getTableSource();
        SQLExpr tableExpr = null;

        if (tableSource instanceof SQLExprTableSource) {
            tableExpr = ((SQLExprTableSource) tableSource).getExpr();
        }

        if (tableExpr instanceof SQLName) {
            TableStat stat = getTableStat((SQLName) tableExpr);
            stat.incrementUpdateCount();
        } else {
            tableSource.accept(this);
        }

        accept(x.getItems());
        accept(x.getWhere());

        return false;
    }

    public boolean visit(XuguSelectQueryBlock x) {
        SQLExprTableSource into = x.getInto();
//        if (into != null && into.getExpr() instanceof SQLName) {
//            TableStat stat = getTableStat((SQLName) into.getExpr());
//            if (stat != null) {
//                stat.incrementInsertCount();
//            }
//        }

        return visit((SQLSelectQueryBlock) x);
    }

    public void endVisit(XuguSelectQueryBlock x) {
        endVisit((SQLSelectQueryBlock) x);
    }

    public boolean visit(SQLPropertyExpr x) {
        if ("ROWNUM".equalsIgnoreCase(x.getName())) {
            return false;
        }

        return super.visit(x);
    }

    public boolean visit(SQLIdentifierExpr x) {
        String name = x.getName();

        if ("+".equalsIgnoreCase(name)) {
            return false;
        }

        long hashCode64 = x.hashCode64();

        if (hashCode64 == FnvHash.Constants.ROWNUM
                || hashCode64 == FnvHash.Constants.SYSDATE
                || hashCode64 == FnvHash.Constants.LEVEL
                || hashCode64 == FnvHash.Constants.SQLCODE) {
            return false;
        }

        if (hashCode64 == FnvHash.Constants.ISOPEN
                && x.getParent() instanceof SQLBinaryOpExpr
                && ((SQLBinaryOpExpr) x.getParent()).getOperator() == SQLBinaryOperator.Modulus) {
            return false;
        }

        return super.visit(x);
    }

/*    @Override
    public boolean visit(XuguSelectSubqueryTableSource x) {
        accept(x.getSelect());
        //accept(x.getPivot());
        accept(x.getFlashback());
        return false;
    }

    @Override
    public boolean visit(XuguWithSubqueryEntry x) {
        x.getSubQuery().accept(this);
        return false;
    }


    @Override
    public boolean visit(OracleLockTableStatement x) {
        getTableStat(x.getTable());
        return false;
    }*/

  /*  @Override
    public boolean visit(OracleSysdateExpr x) {
        return false;
    }*/

    @Override
    public boolean visit(XuguExceptionStatement.Item x) {
        SQLExpr when = x.getWhen();
        if (when instanceof SQLIdentifierExpr) {
            SQLIdentifierExpr ident = (SQLIdentifierExpr) when;
            if (ident.getName().equalsIgnoreCase("OTHERS")) {
                // skip
            } else {
                this.visit(ident);
            }
        } else if (when != null) {
            when.accept(this);
        }

        for (SQLStatement stmt : x.getStatements()) {
            stmt.accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(XuguExplainStatement x) {
        return false;
    }

    @Override
    public boolean visit(XuguAlterTableDropPartition x) {
        return false;
    }

    @Override
    public boolean visit(XuguAlterTableTruncatePartition x) {
        return false;
    }

    @Override
    public boolean visit(XuguForStatement x) {
        x.getRange().accept(this);
        accept(x.getStatements());
        return false;
    }

    @Override
    public boolean visit(XuguPrimaryKey x) {
        accept(x.getColumns());

        return false;
    }

    @Override
    public boolean visit(XuguCreateTableStatement x) {
        this.visit((SQLCreateTableStatement) x);

        if (x.getSelect() != null) {
            x.getSelect().accept(this);
        }

        return false;
    }

    @Override
    public boolean visit(XuguGotoStatement x) {
        return false;
    }

    @Override
    public boolean visit(XuguAlterTriggerStatement x) {
        return false;
    }

    @Override
    public boolean visit(XuguExitStatement x) {
        return false;
    }

    /*@Override
    public boolean visit(XuguContinueStatement x) {
        return false;
    }*/

    @Override
    public boolean visit(XuguRaiseStatement x) {
        return false;
    }


    /*@Override
    public boolean visit(OracleDataTypeIntervalYear x) {
        return false;
    }

    @Override
    public boolean visit(OracleDataTypeIntervalDay x) {
        return false;
    }*/


    @Override
    public boolean visit(XuguLobStorageClause x) {
        return false;
    }

    @Override
    public boolean visit(XuguCreatePackageStatement x) {
        if (repository != null
                && x.getParent() == null) {
            repository.resolve(x);
        }

        for (SQLStatement stmt : x.getStatements()) {
            stmt.accept(this);
        }
        return false;
    }

    @Override
    public boolean visit(XuguExecuteImmediateStatement x) {
        SQLExpr dynamicSql = x.getDynamicSql();

        String sql = null;

        if (dynamicSql instanceof SQLIdentifierExpr) {
            String varName = ((SQLIdentifierExpr) dynamicSql).getName();

            SQLExpr valueExpr = null;
            if (x.getParent() instanceof SQLBlockStatement) {
                List<SQLStatement> statementList = ((SQLBlockStatement) x.getParent()).getStatementList();
                for (int i = 0, size = statementList.size(); i < size; ++i) {
                    SQLStatement stmt = statementList.get(i);
                    if (stmt == x) {
                        break;
                    }

                    if (stmt instanceof SQLSetStatement) {
                        List<SQLAssignItem> items = ((SQLSetStatement) stmt).getItems();
                        for (SQLAssignItem item : items) {
                            if (item.getTarget().equals(dynamicSql)) {
                                valueExpr = item.getValue();
                                break;
                            }
                        }
                    }
                }
            }

            if (valueExpr != null) {
                dynamicSql = valueExpr;
            }
        }

        if (dynamicSql instanceof SQLCharExpr) {
            sql = ((SQLCharExpr) dynamicSql).getText();
        }

        if (sql != null) {
            List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
            for (SQLStatement stmt : stmtList) {
                stmt.accept(this);
            }
        }
        return false;
    }

    @Override
    public boolean visit(XuguCreateSynonymStatement x) {
        return false;
    }

    @Override
    public boolean visit(XuguCreateTypeStatement x) {
        return false;
    }

    @Override
    public boolean visit(OracleIsOfTypeExpr x) {
        return false;
    }
}
