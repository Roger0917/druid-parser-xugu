package com.alibaba.druid.sql.dialect.xugu.parser;

import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleProcedureDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 10:40
 **/

public class XuguProdecureDataType extends SQLDataTypeImpl {

    private boolean isStatic = false;
    private final List<SQLParameter> parameters = new ArrayList<SQLParameter>();

    private SQLStatement block;

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public List<SQLParameter> getParameters() {
        return parameters;
    }

    @Override
    public XuguProdecureDataType clone() {
        XuguProdecureDataType x = new XuguProdecureDataType();
        cloneTo(x);

        x.isStatic = isStatic;
        for (SQLParameter parameter : parameters) {
            SQLParameter p2 = parameter.clone();
            p2.setParent(x);
            x.parameters.add(p2);
        }

        return x;
    }

    public SQLStatement getBlock() {
        return block;
    }

    public void setBlock(SQLStatement block) {
        if (block != null) {
            block.setParent(this);
        }
        this.block = block;
    }
}
