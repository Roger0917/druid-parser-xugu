package com.alibaba.druid.sql.dialect.xugu.parser;

import com.alibaba.druid.sql.parser.Keywords;
import com.alibaba.druid.sql.parser.Lexer;
import com.alibaba.druid.sql.parser.SQLParserFeature;
import com.alibaba.druid.sql.parser.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 10:27
 **/

public class XuguLexer extends Lexer {

    public final static Keywords DEFAULT_XUGU_KEYWORDS;

    static {
        Map<String, Token> map = new HashMap<String, Token>();

        map.putAll(Keywords.DEFAULT_KEYWORDS.getKeywords());

        map.put("BEGIN", Token.BEGIN);
        map.put("COMMENT", Token.COMMENT);
        map.put("COMMIT", Token.COMMIT);
        map.put("CONNECT", Token.CONNECT);
        map.put("CONTINUE", Token.CONTINUE);

        map.put("CROSS", Token.CROSS);
        map.put("CURSOR", Token.CURSOR);
        map.put("DECLARE", Token.DECLARE);
        map.put("ERRORS", Token.ERRORS);
        map.put("EXCEPTION", Token.EXCEPTION);

        map.put("EXCLUSIVE", Token.EXCLUSIVE);
        map.put("EXTRACT", Token.EXTRACT);
        map.put("GOTO", Token.GOTO);
        map.put("IF", Token.IF);
        map.put("ELSIF", Token.ELSIF);

        map.put("LIMIT", Token.LIMIT);
        map.put("LOOP", Token.LOOP);
        map.put("MATCHED", Token.MATCHED);
        map.put("MODE", Token.MODE);
        map.put("NOWAIT", Token.NOWAIT);
        map.put("OF", Token.OF);
        map.put("PRIOR", Token.PRIOR);

        map.put("REJECT", Token.REJECT);
        map.put("RETURN", Token.RETURN);
        map.put("RETURNING", Token.RETURNING);
        map.put("SAVEPOINT", Token.SAVEPOINT);
        map.put("SESSION", Token.SESSION);

        map.put("SHARE", Token.SHARE);
        map.put("START", Token.START);
        map.put("SYSDATE", Token.SYSDATE);
        map.put("UNLIMITED", Token.UNLIMITED);
        map.put("USING", Token.USING);

        map.put("WAIT", Token.WAIT);
        map.put("WITH", Token.WITH);

        map.put("PCTFREE", Token.PCTFREE);
        map.put("INITRANS", Token.INITRANS);
        map.put("MAXTRANS", Token.MAXTRANS);
        map.put("SEGMENT", Token.SEGMENT);
        map.put("CREATION", Token.CREATION);
        map.put("IMMEDIATE", Token.IMMEDIATE);
        map.put("DEFERRED", Token.DEFERRED);
        map.put("STORAGE", Token.STORAGE);
        map.put("NEXT", Token.NEXT);
        map.put("MINEXTENTS", Token.MINEXTENTS);
        map.put("MAXEXTENTS", Token.MAXEXTENTS);
        map.put("MAXSIZE", Token.MAXSIZE);
        map.put("PCTINCREASE", Token.PCTINCREASE);
        map.put("FLASH_CACHE", Token.FLASH_CACHE);
        map.put("CELL_FLASH_CACHE", Token.CELL_FLASH_CACHE);
        map.put("NONE", Token.NONE);
        map.put("LOB", Token.LOB);
        map.put("STORE", Token.STORE);
        map.put("ROW", Token.ROW);
        map.put("CHUNK", Token.CHUNK);
        map.put("CACHE", Token.CACHE);
        map.put("NOCACHE", Token.NOCACHE);
        map.put("NOCOMPRESS", Token.NOCOMPRESS);
        map.put("KEEP_DUPLICATES", Token.KEEP_DUPLICATES);
        map.put("EXCEPTIONS", Token.EXCEPTIONS);
        map.put("PURGE", Token.PURGE);
        map.put("INITIALLY", Token.INITIALLY);

        map.put("FETCH", Token.FETCH);
        map.put("TABLESPACE", Token.TABLESPACE);
        map.put("PARTITION", Token.PARTITION);
        map.put("TRUE", Token.TRUE);
        map.put("FALSE", Token.FALSE);

        map.put("，", Token.COMMA);
        map.put("（", Token.LPAREN);
        map.put("）", Token.RPAREN);

        DEFAULT_XUGU_KEYWORDS = new Keywords(map);
    }

    public XuguLexer(char[] input, int inputLength, boolean skipComment){
        super(input, inputLength, skipComment);
        super.keywods = DEFAULT_XUGU_KEYWORDS;
    }

    public XuguLexer(String input){
        super(input);
        this.skipComment = true;
        this.keepComments = true;
        super.keywods = DEFAULT_XUGU_KEYWORDS;
    }

    public XuguLexer(String input, SQLParserFeature... features){
        super(input);
        this.skipComment = true;
        this.keepComments = true;
        super.keywods = DEFAULT_XUGU_KEYWORDS;

        for (SQLParserFeature feature : features) {
            config(feature, true);
        }
    }
}
