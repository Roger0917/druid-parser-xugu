package com.alibaba.druid.sql.dialect.xugu.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguUpdateStatement;
import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.parser.Token;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 10:42
 **/

public class XuguUpdateParser extends SQLStatementParser {

    public XuguUpdateParser(String sql) {
        super(new XuguExprParser(sql));
    }

    public XuguUpdateParser(Lexer lexer){
        super(new XuguExprParser(lexer));
    }

    @Override
    public XuguUpdateStatement parseUpdateStatement() {
        XuguUpdateStatement update = new XuguUpdateStatement();

        if (lexer.token() == Token.UPDATE) {
            lexer.nextToken();

            parseHints(update);

            if (lexer.identifierEquals("ONLY")) {
                update.setOnly(true);
            }

            SQLTableSource tableSource = this.exprParser.createSelectParser().parseTableSource();
            update.setTableSource(tableSource);

            if ((update.getAlias() == null) || (update.getAlias().length() == 0)) {
                update.setAlias(tableAlias());
            }
        }

        parseUpdateSet(update);

        parseWhere(update);

        //parseReturn(update);

        //parseErrorLoging(update);

        return update;
    }

    /*private void parseErrorLoging(XuguUpdateStatement update) {
        if (lexer.identifierEquals("LOG")) {
            throw new ParserException("TODO. " + lexer.info());
        }
    }*/

    private void parseReturn(XuguUpdateStatement update) {
        if (lexer.token() == Token.RETURN || lexer.token() == Token.RETURNING) {
            lexer.nextToken();

            for (;;) {
                SQLExpr item = this.exprParser.expr();
                update.getReturning().add(item);

                if (lexer.token() == Token.COMMA) {
                    lexer.nextToken();
                    continue;
                }

                break;
            }

            accept(Token.INTO);

            for (;;) {
                SQLExpr item = this.exprParser.expr();
                update.addReturningInto(item);

                if (lexer.token() == Token.COMMA) {
                    lexer.nextToken();
                    continue;
                }

                break;
            }
        }
    }

    private void parseHints(XuguUpdateStatement update) {
        this.exprParser.parseHints(update.getHints());
    }

    private void parseWhere(XuguUpdateStatement update) {
        if (lexer.token() == (Token.WHERE)) {
            lexer.nextToken();
            update.setWhere(this.exprParser.expr());
        }
    }
}
