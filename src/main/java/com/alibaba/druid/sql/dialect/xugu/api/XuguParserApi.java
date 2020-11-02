package com.alibaba.druid.sql.dialect.xugu.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateTriggerBean;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Roger
 * @create: 2020-11-02 13:48
 **/

public class XuguParserApi {

    public static CreateProcedureBean parseCreateProcedure(String sql){
        List<SQLCreateProcedureStatement> createProcedureStatementList = new ArrayList<>();
        CreateProcedureBean createProcedureBean = new CreateProcedureBean();
        List<List<String>> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        builder.append(sql.substring(0,sql.indexOf(")")+1));
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            SQLCreateProcedureStatement sqlCreateProcedureStatement = (SQLCreateProcedureStatement) statement;
            createProcedureStatementList.add(sqlCreateProcedureStatement);
        }
        for(SQLCreateProcedureStatement sqlCreateProcedureStatement:createProcedureStatementList){
            createProcedureBean.setProcedureName(sqlCreateProcedureStatement.getName().toString());
            createProcedureBean.setParamSize(sqlCreateProcedureStatement.getParameters().size());
            for(int i=0;i<sqlCreateProcedureStatement.getParameters().size();i++){
                List<String> param = new ArrayList<>();
                SQLParameter parameter = sqlCreateProcedureStatement.getParameters().get(i);
                SQLParameter.ParameterType type = parameter.getParamType();
                SQLExpr expr = parameter.getDefaultValue();
                if(type!=null&&expr!=null){
                    param.add(parameter.getName().getSimpleName());
                    param.add(parameter.getParamType().name());
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(parameter.getDefaultValue().toString());
                }else if(type!=null&&expr==null){
                    param.add(parameter.getName().getSimpleName());
                    param.add(parameter.getParamType().name());
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(null);
                }else if(type==null&expr!=null){
                    param.add(parameter.getName().getSimpleName());
                    param.add("In");
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(parameter.getDefaultValue().toString());
                }else{
                    param.add(parameter.getName().getSimpleName());
                    param.add("In");
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(null);
                }
                params.add(param);
            }
        }
        createProcedureBean.setParams(params);
        return createProcedureBean;
    }

    public static CreateFunctionBean parseCreateFunction(String sql){
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();
        CreateFunctionBean createFunctionBean = new CreateFunctionBean();
        List<List<String>> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        String[] arr = sql.split("\\s+");
        for(String str:arr){
            if("as".equals(str)){
                builder.append(sql.substring(0,sql.indexOf(str)));
            }
        }
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            SQLCreateFunctionStatement sqlCreateFunctionStatement = (SQLCreateFunctionStatement) statement;
            createFunctionStatementList.add(sqlCreateFunctionStatement);
        }
        for(SQLCreateFunctionStatement sqlCreateFunctionStatement:createFunctionStatementList){
            createFunctionBean.setFunctionName(sqlCreateFunctionStatement.getName().toString());
            createFunctionBean.setParamSize(sqlCreateFunctionStatement.getParameters().size());
            createFunctionBean.setReturnType(sqlCreateFunctionStatement.getReturnDataType().toString());
            for(int i=0;i<sqlCreateFunctionStatement.getParameters().size();i++){
                List<String> param = new ArrayList<>();
                SQLParameter parameter = sqlCreateFunctionStatement.getParameters().get(i);
                SQLParameter.ParameterType type = parameter.getParamType();
                SQLExpr expr = parameter.getDefaultValue();
                if(type!=null&&expr!=null){
                    param.add(parameter.getName().getSimpleName());
                    param.add(parameter.getParamType().name());
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(parameter.getDefaultValue().toString());
                }else if(type!=null&&expr==null){
                    param.add(parameter.getName().getSimpleName());
                    param.add(parameter.getParamType().name());
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(null);
                }else if(type==null&expr!=null){
                    param.add(parameter.getName().getSimpleName());
                    param.add("In");
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(parameter.getDefaultValue().toString());
                }else{
                    param.add(parameter.getName().getSimpleName());
                    param.add("In");
                    param.add(parameter.getDataType().getName());
                    param.add(i+"");
                    param.add(null);
                }
                params.add(param);
            }
        }
        createFunctionBean.setParams(params);
        return createFunctionBean;
    }

    public static CreateTriggerBean parseCreateTrigger(String sql){

        return null;
    }



    public static void main(String[] args) {
        /*String sql = "create or replace procedure xugu_test_pro(id in int,name in varchar,address out varchar)as\n";
        CreateProcedureBean bean = parseCreateProcedure(sql);
        System.out.println(bean.getProcedureName()+bean.getParamSize());
        System.out.println(bean.getParams().get(0).get(0)+bean.getParams().get(0).get(1)+bean.getParams().get(0).get(2)+bean.getParams().get(0).get(3)
        +bean.getParams().get(0).get(4));*/

        String sql2 = "create or replace function xugu_test_fun(id in int,name in varchar,address out varchar) return int as";
        CreateFunctionBean bean2 = parseCreateFunction(sql2);
        System.out.println(bean2.getFunctionName()+bean2.getParamSize()+bean2.getReturnType());
        System.out.println(bean2.getParams().get(0).get(0)+bean2.getParams().get(0).get(1)+bean2.getParams().get(0).get(2)+bean2.getParams().get(0).get(3)
                +bean2.getParams().get(0).get(4));
    }
}
