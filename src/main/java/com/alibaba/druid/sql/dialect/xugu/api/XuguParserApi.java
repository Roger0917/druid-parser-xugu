package com.alibaba.druid.sql.dialect.xugu.api;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCallStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTriggerStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTypeStatement;
import com.alibaba.druid.sql.dialect.xugu.api.bean.*;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreatePackageStatement;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreateTypeStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.google.common.collect.Lists;
import org.apache.zookeeper.Op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        if(sql.contains("as")||sql.contains("is")||sql.contains("AS")||sql.contains("IS")){
            for(String str:arr){
                if("as".equalsIgnoreCase(str)||"is".equalsIgnoreCase(str)){
                    builder.append(sql.substring(0,sql.indexOf(str)));
                }
            }
        }else{
            builder.append(sql);
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
        List<SQLCreateTriggerStatement> createTriggerStatementList = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        CreateTriggerBean triggerBean = new CreateTriggerBean();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        String text = Base.print(statementList);

        for (SQLStatement statement : statementList) {
            SQLCreateTriggerStatement sqlCreateTriggerStatement = (SQLCreateTriggerStatement) statement;
            createTriggerStatementList.add(sqlCreateTriggerStatement);
        }

        for(SQLCreateTriggerStatement sqlCreateTriggerStatement:createTriggerStatementList){
            triggerBean.setTriggerName(sqlCreateTriggerStatement.getName().toString());
            if(sqlCreateTriggerStatement.isForEachRow()){
                triggerBean.setTriggerType("FOR EACH ROW");
            }else{
                triggerBean.setTriggerType("FOR STATEMENT");
            }
            triggerBean.setTable(sqlCreateTriggerStatement.getOn().getTableName());
            triggerBean.setTriggerOccasion(sqlCreateTriggerStatement.getTriggerType().name());
            if(sqlCreateTriggerStatement.isInsert()){
                operators.add("Insert");
            }else if(sqlCreateTriggerStatement.isUpdate()){
                operators.add("Update");
            }else if(sqlCreateTriggerStatement.isDelete()){
                operators.add("Delete");
            }
            triggerBean.setOperators(operators);
            if(sqlCreateTriggerStatement.getUpdateOfColumns().size()>0){
                triggerBean.setUpdateColumns(sqlCreateTriggerStatement.getUpdateOfColumns().stream().map(SQLName::getSimpleName).collect(Collectors.toList()));
            }
            if(sqlCreateTriggerStatement.getWhen()!=null){
                triggerBean.setWhen(sqlCreateTriggerStatement.getWhen().toString());
            }
            triggerBean.setBody(sqlCreateTriggerStatement.getBody().toString());
            triggerBean.setDefine(text.substring(0,text.indexOf(sqlCreateTriggerStatement.getBody().toString())));
        }
        return triggerBean;
    }



    public static CreatePackageBean parseCreatePackage(String sql){
        CreatePackageBean createPackageBean = new CreatePackageBean();
        List<XuguCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            XuguCreatePackageStatement xuguCreatePackageStatement = (XuguCreatePackageStatement) statement;
            createPackageStatementList.add(xuguCreatePackageStatement);
        }
        for(XuguCreatePackageStatement xuguCreatePackageStatement:createPackageStatementList){
            List<CreateProcedureBean> procedureBeanList = new ArrayList<>();
            List<CreateFunctionBean> functionBeanList  = new ArrayList<>();
            System.out.println("包名 "+xuguCreatePackageStatement.getName());
            if(!xuguCreatePackageStatement.isBody()){
                for(SQLStatement sqlStatement:xuguCreatePackageStatement.getStatements()){
                    if(sqlStatement instanceof SQLCreateProcedureStatement){
                        procedureBeanList.add(parseCreateProcedure(sqlStatement.toString()));
                    }else if(sqlStatement instanceof SQLCreateFunctionStatement){
                        functionBeanList.add(parseCreateFunction(sqlStatement.toString()));
                    }
                }
                createPackageBean.setCreateProcedureBeans(procedureBeanList);
                createPackageBean.setCreateFunctionBeans(functionBeanList);
            }
            else{
                System.out.println("包体: "+xuguCreatePackageStatement.getStatements());
            }
        }
        return createPackageBean;
    }

    public static CreateTypeBean parseCreateType(String sql){
        CreateTypeBean createTypeBean = new CreateTypeBean();
        List<XuguCreateTypeStatement> createTypeStatementList = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            XuguCreateTypeStatement createTypeStatement = (XuguCreateTypeStatement) statement;
            createTypeStatementList.add(createTypeStatement);
        }
        for(XuguCreateTypeStatement createTypeStatement:createTypeStatementList){
            if(createTypeStatement.isBody()){
                createTypeBean.setBody(createTypeStatement.toString());
                Map<String,String> memberMap = new HashMap<>();
                for (SQLParameter sqlParameter:createTypeStatement.getParameters()){
                    String parameterStr = sqlParameter.toString();
                    if(parameterStr.contains("MEMBER")){
                        String[] arr = parameterStr.split(" ");
                        if(arr[1].equals("PROCEDURE")){
                            memberMap.put(arr[2],arr[1]);
                        }else if(arr[1].equals("FUNCTION")){
                            memberMap.put(arr[2],arr[1]);
                        }
                    }
                }
                createTypeBean.setMemberMap(memberMap);
            }else{
                createTypeBean.setHeader(createTypeStatement.toString());
                Map<String,String> attributeMap = new HashMap<>();
                for (SQLParameter sqlParameter:createTypeStatement.getParameters()){
                    String parameterStr = sqlParameter.toString();
                    if(!parameterStr.contains("MEMBER")){
                        attributeMap.put(parameterStr.substring(0,parameterStr.indexOf(" ")),parameterStr.substring(parameterStr.indexOf(" ")));
                    }
                }
                createTypeBean.setAttributeList(attributeMap);
            }
        }
        return createTypeBean;
    }

    /** 过程和函数call */
    public static List<CreateCallBean> parseCall(String sql){
        List<CreateCallBean> callBeanList = new ArrayList<>();
        List<SQLCallStatement> callStatementList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        //SQLStatement statement = statementList.get(0);

        for(SQLStatement sqlStatement:statementList){
            SQLCallStatement sqlCallStatement = (SQLCallStatement) sqlStatement;
            callStatementList.add(sqlCallStatement);
        }

        for (SQLCallStatement sqlCallStatement:callStatementList){
            CreateCallBean callBean = new CreateCallBean();
            List<String> paramList = new ArrayList<>();
            callBean.setName(sqlCallStatement.getProcedureName().getSimpleName());
            for(SQLExpr sqlExpr:sqlCallStatement.getParameters()){
                if(sqlExpr.toString().contains(":")){
                    paramList.add(sqlExpr.toString().replaceAll(":",""));
                }else if(sqlExpr.toString().contains("=>")){
                    paramList.add(sqlExpr.toString().substring(0,sqlExpr.toString().indexOf("=>")));
                }
            }
            callBean.setParamList(paramList);
            callBeanList.add(callBean);
        }
        return callBeanList;
    }

    /** 解析多语句 */
    public static void parseMultiStatement(String sql){
        String[] arr = sql.split(";");
        String patternStr = "\\screate\\s";
        Pattern pattern1 = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
        if(pattern1.matcher(arr[0]).find()||arr[0].toUpperCase().startsWith("CREATE")){
            System.out.println("true");
        }
    }

    public static void main(String[] args) {
        /*String sql = "create or replace procedure xugu_test_pro(id in int,name in varchar,address out varchar)as\n";
        CreateProcedureBean bean = parseCreateProcedure(sql);
        System.out.println(bean.getProcedureName()+bean.getParamSize());
        System.out.println(bean.getParams().get(0).get(0)+bean.getParams().get(0).get(1)+bean.getParams().get(0).get(2)+bean.getParams().get(0).get(3)
        +bean.getParams().get(0).get(4));*/

        /*String sql2 = "create or replace function xugu_test_fun(id in int,name in varchar,address out varchar) return int as";
        CreateFunctionBean bean2 = parseCreateFunction(sql2);
        System.out.println(bean2.getFunctionName()+bean2.getParamSize()+bean2.getReturnType());
        System.out.println(bean2.getParams().get(0).get(0)+bean2.getParams().get(0).get(1)+bean2.getParams().get(0).get(2)+bean2.getParams().get(0).get(3)
                +bean2.getParams().get(0).get(4));*/

        /** 行级触发器 */
       /* String sql3 = "CREATE OR REPLACE TRIGGER trig_test3 AFTER INSERT OR DELETE OR UPDATE OF id,NANE ON test_trig_tab\n" +
                "FOR each ROW WHEN (old.ID>=10) BEGIN\n" +
                "IF updating THEN INSERT INTO test_trig_tab2 VALUES(new.id,'update tab');\n" +
                "END IF;\n" +
                "IF inserting THEN INSERT INTO test_trig_tab2 VALUES(new.id,'update tab');\n" +
                "END IF;\n"+
                "IF deleting THEN\n" +
                "INSERT INTO test_trig_tab2 VALUES(new.id,'hh');\n" +
                "END IF;\n" +
                "END;";
        System.out.println(parseCreateTrigger(sql3));*/

        /** 语句级触发器 *//*
        String sql4 = "create or replace trigger statement_trigger after insert on table_statement for statement\n" +
                "begin \n" +
                "send_msg('触发了语句级触发器');\n" +
                "end;";
        System.out.println(parseCreateTrigger(sql4));*/

        /*String sql5 = "create or replace package xugu_test_pack is\n" +
                "                procedure pack_proc1(id int,name varchar);\n" +
                "                function pack_fun1(id int,name varchar) return int;\n" +
                "                procedure pack_proc2(x int,y int);\n" +
                "                function pack_fun2(x int,y int) return int;\n" +
                "                end;";
        CreatePackageBean packageBean = parseCreatePackage(sql5);
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.println(packageBean.getCreateFunctionBeans());*/
        /*String sql6 = "CREATE OR REPLACE TYPE person_typ2 AS OBJECT(\n" +
                "     name VARCHAR,gender VARCHAR,\n" +
                "     birthdate DATE,address VARCHAR,\n" +
                "     MEMBER PROCEDURE change_address(new_addr VARCHAR),\n" +
                "     MEMBER FUNCTION get_info(x int) RETURN VARCHAR\n" +
                ");"+
                "CREATE OR REPLACE TYPE BODY person_typ2 IS\n" +
                "       MEMBER PROCEDURE change_address(new_addr VARCHAR)\n" +
                "       IS\n" +
                "       BEGIN\n" +
                "           address:=new_addr;\n" +
                "      END;\n" +
                "      MEMBER FUNCTION get_info(x int) RETURN VARCHAR\n" +
                "      IS\n" +
                "           v_info VARCHAR;\n" +
                "      BEGIN\n" +
                "           v_info := '姓名：'||name||',出生日期：'||birthdate;\n" +
                "           RETURN v_info;\n" +
                "      END;\n" +
                "END;";
        CreateTypeBean createTypeBean = parseCreateType(sql6);
        System.out.println(createTypeBean.getHeader());
        System.out.println(createTypeBean.getBody());
        for (Map.Entry<String, String> entry : createTypeBean.getAttributeList().entrySet()) {
            System.out.println("attributekey = " + entry.getKey() + ", attributevalue = " + entry.getValue());
        }

        for (Map.Entry<String, String> entry : createTypeBean.getMemberMap().entrySet()) {
            System.out.println("memberkey = " + entry.getKey() + ", membervalue = " + entry.getValue());
        }*/
       /* String sql = "call xugu_test_pro(id => 5,name=>'ggg',address => 'vvv');"
        + "call xugu_test_pro(:1,:2)"
        + "call xugu_test_pro(:id,:name)"
        + "call xugu_test_return_fun(id => 5,name=>'ggg',address => 'vvv');";
        List<CreateCallBean> callBeanList = parseCall(sql);
        for(CreateCallBean callBean:callBeanList){
            System.out.println(callBean.getName());
            System.out.println(callBean.getParamList());
        }*/
        String sql = "\tcreate or replace function xugu_test_fun(id in int,name in varchar,address out varchar) return int";
        parseMultiStatement(sql);
    }


}
