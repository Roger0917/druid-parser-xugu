package com.alibaba.druid.sql.dialect.xugu.parser;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleFunctionDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-10-27 10:40
 **/

public class XuguFunctionDataType extends SQLDataTypeImpl {

    private boolean isStatic = false;
    private final List<SQLParameter> parameters = new ArrayList<SQLParameter>();

    private SQLDataType returnDataType;

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

    public SQLDataType getReturnDataType() {
        return returnDataType;
    }

    public void setReturnDataType(SQLDataType x) {
        if (x != null) {
            x.setParent(this);
        }
        this.returnDataType = x;
    }

    @Override
    public XuguFunctionDataType clone() {
        XuguFunctionDataType x = new XuguFunctionDataType();
        cloneTo(x);

        x.isStatic = isStatic;
        if (returnDataType != null) {
            x.setReturnDataType(returnDataType.clone());
        }
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
