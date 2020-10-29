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

        map.put("CURSOR", Token.CURSOR);
        map.put("TOP", Token.TOP);
        map.put("USE", Token.USE);
        map.put("WITH", Token.WITH);
        map.put("PERCENT", Token.PERCENT);
        map.put("IDENTITY", Token.IDENTITY);
        map.put("DECLARE", Token.DECLARE);
        map.put("IF", Token.IF);
        map.put("ELSE", Token.ELSE);
        map.put("BEGIN", Token.BEGIN);
        map.put("END", Token.END);

        map.put("MERGE", Token.MERGE);
        map.put("USING", Token.USING);
        map.put("MATCHED", Token.MATCHED);

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
