package com.alibaba.druid.sql.dialect.xugu.parser;

import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.statement.SQLExternalRecordFormat;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleLobStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.clause.OracleStorageClause;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.*;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleExprParser;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleSelectParser;
import com.alibaba.druid.sql.dialect.xugu.ast.clause.XuguLobStorageClause;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreateTableStatement;
import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLCreateTableParser;
import com.alibaba.druid.sql.parser.Token;
import com.alibaba.druid.util.FnvHash;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 10:39
 **/

public class XuguCreateTableParser extends SQLCreateTableParser {

    public XuguCreateTableParser(Lexer lexer){
        super(new XuguExprParser(lexer));
    }

    public XuguCreateTableParser(String sql){
        super(new XuguExprParser(sql));
    }

    @Override
    protected XuguCreateTableStatement newCreateStatement() {
        return new XuguCreateTableStatement();
    }

    @Override
    public XuguCreateTableStatement parseCreateTable(boolean acceptCreate) {
        XuguCreateTableStatement stmt = (XuguCreateTableStatement) super.parseCreateTable(acceptCreate);

        if (lexer.token() == Token.OF) {
            lexer.nextToken();
            stmt.setOf(this.exprParser.name());

            /*if (lexer.identifierEquals("OIDINDEX")) {
                lexer.nextToken();

                OracleCreateTableStatement.OIDIndex oidIndex = new OracleCreateTableStatement.OIDIndex();

                if (lexer.token() != Token.LPAREN) {
                    oidIndex.setName(this.exprParser.name());
                }
                accept(Token.LPAREN);
                this.getExprParser().parseSegmentAttributes(oidIndex);
                accept(Token.RPAREN);

                stmt.setOidIndex(oidIndex);
            }*/
        }

        for (;;) {
            this.getExprParser().parseSegmentAttributes(stmt);

            if (lexer.identifierEquals(FnvHash.Constants.IN_MEMORY_METADATA)) {
                lexer.nextToken();
                stmt.setInMemoryMetadata(true);
                continue;
            } else if (lexer.identifierEquals(FnvHash.Constants.CURSOR_SPECIFIC_SEGMENT)) {
                lexer.nextToken();
                stmt.setCursorSpecificSegment(true);
                continue;
            } else if (lexer.identifierEquals(FnvHash.Constants.NOPARALLEL)) {
                lexer.nextToken();
                stmt.setParallel(false);
                continue;
            } else if (lexer.identifierEquals(FnvHash.Constants.PARALLEL)) {
                lexer.nextToken();
                stmt.setParallel(true);

                if (lexer.token() == Token.LITERAL_INT) {
                    stmt.setParallelValue(this.exprParser.primary());
                }
                continue;
            } else if (lexer.token() == Token.CACHE) {
                lexer.nextToken();
                stmt.setCache(Boolean.TRUE);
                continue;
            } else if (lexer.token() == Token.NOCACHE) {
                lexer.nextToken();
                stmt.setCache(Boolean.FALSE);
                continue;
            } else if (lexer.token() == Token.ENABLE) {
                lexer.nextToken();
                if (lexer.token() == Token.ROW) {
                    lexer.nextToken();
                    acceptIdentifier("MOVEMENT");
                    stmt.setEnableRowMovement(Boolean.TRUE);
                } else {
                    throw new ParserException("TODO : " + lexer.info());
                }
                //stmt.setEnable(Boolean.TRUE);
                continue;
            } else if (lexer.token() == Token.DISABLE) {
                lexer.nextToken();
                if (lexer.token() == Token.ROW) {
                    lexer.nextToken();
                    acceptIdentifier("MOVEMENT");
                    stmt.setEnableRowMovement(Boolean.FALSE);
                } else {
                    throw new ParserException("TODO : " + lexer.info());
                }
                //stmt.setEnable(Boolean.FALSE);
                continue;
            } else if (lexer.token() == Token.ON) {
                lexer.nextToken();
                accept(Token.COMMIT);

                if (lexer.identifierEquals("PRESERVE")) {
                    lexer.nextToken();
                    acceptIdentifier("ROWS");
                    stmt.setOnCommitPreserveRows(true);
                } else {
                    accept(Token.DELETE);
                    acceptIdentifier("ROWS");
                    stmt.setOnCommitDeleteRows(true);
                }
                continue;
           /* } else if (lexer.identifierEquals("STORAGE")) {
                OracleStorageClause storage = ((OracleExprParser) this.exprParser).parseStorage();
                stmt.setStorage(storage);
                continue;
            } else if (lexer.identifierEquals("ORGANIZATION")) {
                parseOrganization(stmt);
                continue;
            } else if (lexer.identifierEquals(FnvHash.Constants.CLUSTER)) {
                lexer.nextToken();
                SQLName cluster = this.exprParser.name();
                stmt.setCluster(cluster);
                accept(Token.LPAREN);
                this.exprParser.names(stmt.getClusterColumns(), cluster);
                accept(Token.RPAREN);
                continue;*/
//            } else if (lexer.token() == Token.STORAGE) {
//                OracleStorageClause storage = ((OracleExprParser) this.exprParser).parseStorage();
//                stmt.setStorage(storage);
//                continue;
            } /*else if (lexer.identifierEquals("MONITORING")) {
                lexer.nextToken();
                stmt.setMonitoring(true);
                continue;
            } else if (lexer.identifierEquals(FnvHash.Constants.INCLUDING)) {
                lexer.nextToken();
                this.exprParser.names(stmt.getIncluding(), stmt);
                acceptIdentifier("OVERFLOW");
                continue;
            }*/ else if (lexer.token() == Token.LOB) {
                XuguLobStorageClause lobStorage = ((XuguExprParser) this.exprParser).parseLobStorage();
                stmt.setLobStorage(lobStorage);
                continue;
            } else if (lexer.token() == Token.SEGMENT) {
                lexer.nextToken();
                accept(Token.CREATION);
                /*if (lexer.token() == Token.IMMEDIATE) {
                    lexer.nextToken();
                    stmt.setDeferredSegmentCreation(OracleCreateTableStatement.DeferredSegmentCreation.IMMEDIATE);
                } else {
                    accept(Token.DEFERRED);
                    stmt.setDeferredSegmentCreation(OracleCreateTableStatement.DeferredSegmentCreation.DEFERRED);
                }*/
                continue;
            } else if (lexer.token() == Token.COLUMN) {
                lexer.nextToken();
                SQLName name = this.exprParser.name();
                if (lexer.token() == Token.NOT) {
                    lexer.nextToken();
                }

                if (lexer.identifierEquals(FnvHash.Constants.SUBSTITUTABLE)) {
                    lexer.nextToken();
                    acceptIdentifier("AT");
                    accept(Token.ALL);
                    acceptIdentifier("LEVELS");
                }
                // skip
                continue;
            } else if (lexer.identifierEquals(FnvHash.Constants.VARRAY)) {
                lexer.nextToken();
                SQLName name = this.exprParser.name();

                accept(Token.STORE);
                accept(Token.AS);
                if (lexer.identifierEquals(FnvHash.Constants.BASICFILE)) {
                    lexer.nextToken();
                }
                this.getExprParser().parseLobStorage();
                throw new ParserException("TODO : " + lexer.info());
            } else if (lexer.token() == Token.PARTITION) {
                lexer.nextToken();

                accept(Token.BY);

                if (lexer.identifierEquals("RANGE")) {
                    SQLPartitionByRange partitionByRange = this.getExprParser().partitionByRange();
                    this.getExprParser().partitionClauseRest(partitionByRange);
                    stmt.setPartitioning(partitionByRange);
                    continue;
                } else if (lexer.identifierEquals("HASH")) {
                    SQLPartitionByHash partitionByHash = this.getExprParser().partitionByHash();
                    this.getExprParser().partitionClauseRest(partitionByHash);

                    if (lexer.token() == Token.LPAREN) {
                        lexer.nextToken();
                        for (;;) {
                            SQLPartition partition = this.getExprParser().parsePartition();
                            partitionByHash.addPartition(partition);
                            if (lexer.token() == Token.COMMA) {
                                lexer.nextToken();
                                continue;
                            } else if (lexer.token() == Token.RPAREN) {
                                lexer.nextToken();
                                break;
                            }
                            throw new ParserException("TODO : " + lexer.info());
                        }
                    }
                    stmt.setPartitioning(partitionByHash);
                    continue;
                } else if (lexer.identifierEquals("LIST")) {
                    SQLPartitionByList partitionByList = partitionByList();
                    this.getExprParser().partitionClauseRest(partitionByList);
                    stmt.setPartitioning(partitionByList);
                    continue;
                } else {
                    throw new ParserException("TODO : " + lexer.info());
                }
            }
            break;
        }

        if (lexer.token() == Token.AS) {
            lexer.nextToken();

            SQLSelect select = new XuguSelectParser(exprParser).select();
            stmt.setSelect(select);
        }

        return stmt;
    }


    protected SQLPartitionByList partitionByList() {
        acceptIdentifier("LIST");
        SQLPartitionByList partitionByList = new SQLPartitionByList();

        accept(Token.LPAREN);
        partitionByList.addColumn(this.exprParser.expr());
        accept(Token.RPAREN);

        this.getExprParser().parsePartitionByRest(partitionByList);

        return partitionByList;
    }

    @Override
    public XuguExprParser getExprParser() {
        return (XuguExprParser) exprParser;
    }
}
