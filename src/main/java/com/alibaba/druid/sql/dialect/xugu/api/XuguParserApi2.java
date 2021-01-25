/**

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLParameter;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleCreateTypeStatement;
import com.alibaba.druid.sql.dialect.xugu.api.bean.*;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreatePackageStatement;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.XuguCreateTypeStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.zookeeper.Op;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;





public class XuguParserApi2 {

    public static CreateProcedureBean parseCreateProcedure(String sql){
        List<SQLCreateProcedureStatement> createProcedureStatementList = new ArrayList<>();
        CreateProcedureBean createProcedureBean = new CreateProcedureBean();
        List<Param> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        if(sql.contains("(")&&sql.contains(")")){
            builder.append(sql.substring(0,sql.indexOf(")")+1));
        }else {
            builder.append(sql);
        }
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            SQLCreateProcedureStatement sqlCreateProcedureStatement = (SQLCreateProcedureStatement) statement;
            createProcedureStatementList.add(sqlCreateProcedureStatement);
        }
        Integer asorisIndex=0;
        String[] arr = sql.split("\\s+");
        List arrList = Arrays.asList(arr);
        if(arrList.contains("as")||arrList.contains("is")||arrList.contains("AS")||arrList.contains("IS")){
            int temp = sql.lastIndexOf("as");
            int temp2 = sql.lastIndexOf("AS");
            int temp3 = sql.lastIndexOf("is");
            int temp4 = sql.lastIndexOf("IS");
            List<Integer> indexList = Arrays.asList(temp,temp2,temp3,temp4);
            for(Integer integer:indexList){
                if(integer>0){
                    asorisIndex=integer;
                }
            }
        }else if(arrList.contains("(")&&arrList.contains(")")){
            asorisIndex = sql.lastIndexOf(")")+1;
        }
 if(StrUtil.indexOfIgnoreCase(sql,"is")>0){
            asorisIndex=StrUtil.indexOfIgnoreCase(sql,"is");
        }else if(StrUtil.indexOfIgnoreCase(sql,"as")>0){
            asorisIndex=StrUtil.indexOfIgnoreCase(sql,"as");
        }else if(StrUtil.contains(sql,"(")&&StrUtil.contains(sql,")")){
            asorisIndex = sql.lastIndexOf(")")+1;
        }
else{
            createProcedureBean.setProcedureName(createProcedureStatementList.get(0).getName().toString());
            createProcedureBean.setParamSize(0);
            createProcedureBean.setParams(new ArrayList<>());
            return createProcedureBean;
        }
        String waitParseSql="";
        if(asorisIndex==0){
               waitParseSql = sql.substring(0,sql.lastIndexOf(")")+1);
        }else{
            waitParseSql = sql.substring(0,asorisIndex);
        }
        String procedureBody = waitParseSql.substring(waitParseSql.lastIndexOf("("),waitParseSql.lastIndexOf(")")+1);
        String trimprocedureBody = procedureBody.trim().substring(1,procedureBody.trim().lastIndexOf(")")).trim();

        //说明为无参数过程
        if(trimprocedureBody.length()==0){
            createProcedureBean.setProcedureName(createProcedureStatementList.get(0).getName().toString());
            createProcedureBean.setParamSize(0);
            createProcedureBean.setParams(new ArrayList<>());
            return createProcedureBean;
        }
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
        List arrList = Arrays.asList(arr);
        if(arrList.contains("as")||arrList.contains("is")||arrList.contains("AS")||arrList.contains("IS")){
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

        String waitParseSql="";
        if(sql.indexOf("return")>0){
            waitParseSql =  sql.substring(0,sql.indexOf("return"));
        }else if(sql.indexOf("RETURN")>0){
            waitParseSql = sql.substring(0,sql.indexOf("RETURN"));
        }
        if(waitParseSql.indexOf("(")==-1&&waitParseSql.indexOf(")")==-1){
            createFunctionBean.setFunctionName(createFunctionStatementList.get(0).getName().toString());
            createFunctionBean.setParamSize(0);
            createFunctionBean.setParams(new ArrayList<>());
            return createFunctionBean;
        }
        String functionBody = waitParseSql.substring(waitParseSql.lastIndexOf("("),waitParseSql.lastIndexOf(")")+1);
        String trimfunctionBody = functionBody.trim().substring(1,functionBody.trim().lastIndexOf(")")).trim();

        //说明为无参数过程
        if(trimfunctionBody.length()==0){
            createFunctionBean.setFunctionName(createFunctionStatementList.get(0).getName().toString());
            createFunctionBean.setParamSize(0);
            createFunctionBean.setParams(new ArrayList<>());
            return createFunctionBean;
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
        //如果创包有句有subtype类型,先处理掉
        String headStr="";
        String waitSplitStr="";
        StringBuilder builder = new StringBuilder();
        String[] arr = sql.split("\\s+");
        List<String> arrList = Arrays.asList(arr);
        if(arrList.contains("as")||arrList.contains("is")||arrList.contains("AS")||arrList.contains("IS")) {
            String tempStr="";
            for(String str:arrList){
                if(str.equalsIgnoreCase("is")||str.equalsIgnoreCase("as")){
                    tempStr = str;
                    break;
                }
            }
            //获取is或as后面的串
if(StrUtil.indexOfIgnoreCase(sql,tempStr)>0){
                waitSplitStr = sql.substring(0,sql.indexOf(tempStr)+2);
            }else if(StrUtil.indexOfIgnoreCase(sql,"as")>0){
                waitSplitStr = sql.substring(0,sql.indexOf("as")+2);
            }

            waitSplitStr = sql.substring(sql.indexOf(tempStr)+2);
            headStr=sql.substring(0,sql.indexOf(waitSplitStr));
            List<String> headStrList = Arrays.asList(headStr.split("\\s+"));
            for(String s:headStrList){
                if(("AUTHID").equalsIgnoreCase(s)){
                    headStr = headStr.replaceAll(headStr.substring(headStr.indexOf(s),headStr.indexOf(tempStr)),"");
                }else if(("comment").equalsIgnoreCase(s)){
                    if(headStr.contains(s)){
                        headStr = headStr.replaceAll(headStr.substring(headStr.indexOf(s),headStr.indexOf(tempStr)),"");
                    }
                }
            }
                    String[] ObjStr = waitSplitStr.split(";");
                    for(String oneobjStr:ObjStr){
                        List list2 = Arrays.asList(oneobjStr.split("\\s+"));
 if(!list2.contains("SUBTYPE")||list2.contains("subtype")){
                            builder.append(oneobjStr);
                        }

                        if(list2.contains("FUNCTION")||list2.contains("function")||list2.contains("PROCEDURE")||list2.contains("procedure")||list2.contains("END")||list2.contains("end")){
                            if(list2.contains("END")||list2.contains("end")){
                               // oneobjStr = StringEscapeUtils.unescapeJava(oneobjStr+";");
                                builder.append("END;");
                            }else{
                                if(!list2.contains("INTERVAL")){
                                    builder.append(oneobjStr+";");
                                }
                            }
                        }
                    }
                }
        sql = headStr+builder.toString();
        CreatePackageBean createPackageBean = new CreatePackageBean();
        List<XuguCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            if(statement instanceof SQLExprStatement){

            }else{
                XuguCreatePackageStatement xuguCreatePackageStatement = (XuguCreatePackageStatement) statement;
                createPackageStatementList.add(xuguCreatePackageStatement);
            }
        }
        for(XuguCreatePackageStatement xuguCreatePackageStatement:createPackageStatementList){
            List<CreateProcedureBean> procedureBeanList = new ArrayList<>();
            List<CreateFunctionBean> functionBeanList  = new ArrayList<>();
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
                //System.out.println("包体: "+xuguCreatePackageStatement.getStatements());
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
                createTypeBean.setMethodMap(memberMap);
            }else{
                createTypeBean.setHeader(createTypeStatement.toString());
                Map<String,String> attributeMap = new HashMap<>();
                for (SQLParameter sqlParameter:createTypeStatement.getParameters()){
                    String parameterStr = sqlParameter.toString();
                    if(!parameterStr.contains("MEMBER")){
                        attributeMap.put(parameterStr.substring(0,parameterStr.indexOf(" ")),parameterStr.substring(parameterStr.indexOf(" ")));
                    }
                }
                createTypeBean.setAttributeMap(attributeMap);
            }
        }
        return createTypeBean;
    }

* 过程和函数call

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

    public static List<BlockProAndFunBean> parseBlockProAndFun(String sql) {
        List<SQLBlockStatement> blockStatementList = new ArrayList<>();
        List<BlockProAndFunBean> blockProAndFunBeans = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(sql);
        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        String text = Base.print(statementList);

        for (SQLStatement sqlStatement : statementList) {
            SQLBlockStatement sqlBlockStatement = (SQLBlockStatement) sqlStatement;
            blockStatementList.add(sqlBlockStatement);
        }

        for (SQLBlockStatement sqlBlockStatement : blockStatementList) {
            for (SQLStatement sqlStatement : sqlBlockStatement.getStatementList()) {
                if (sqlStatement instanceof SQLExprStatement) {
                    SQLExprStatement exprStatement = (SQLExprStatement) sqlStatement;
                    if (exprStatement.getExpr() instanceof SQLMethodInvokeExpr) {
                        BlockProAndFunBean bean = new BlockProAndFunBean();
                        List<String> paramList = new ArrayList<>();
                        SQLMethodInvokeExpr sqlMethodInvokeExpr = (SQLMethodInvokeExpr) exprStatement.getExpr();
                        //System.out.println("方法名: "+sqlMethodInvokeExpr.getMethodName());
                        bean.setName(sqlMethodInvokeExpr.getMethodName());
                        for (SQLExpr expr : sqlMethodInvokeExpr.getArguments()) {
                            if (expr.toString().contains("=>")) {
                                //System.out.println("參數名: "+expr.toString().substring(0,expr.toString().indexOf("=>")));
                                paramList.add(expr.toString().substring(0, expr.toString().indexOf("=>")));
                            } else {
                                //System.out.println("參數名: "+expr.toString());
                                paramList.add(expr.toString());
                            }
                            bean.setParameters(paramList);
                        }
                        blockProAndFunBeans.add(bean);
                    }
                }
            }
        }
        return blockProAndFunBeans;
    }


public static Map<String,String> parseMultiStatement(String sql){
        String[] arr = sql.split(";");
        String createPatternStr = "^\\s+create+\\s+";
        String alterPatternStr = "^\\s+alter+\\s+";
        String dropPatternStr = "^\\s+drop+\\s+";
        String tuuncatePatternStr = "^\\s+truncate+\\s+";
        String insertPatternStr = "^\\s+insert+\\s+";
        String updatePatternStr = "^\\s+update+\\s+";
        String deletePatternStr = "^\\s+delete+\\s+";
        String selectPatternStr = "^\\s+select+\\s+";
        Map<String,String> map = new HashMap<>();
        Pattern pattern1 = Pattern.compile(createPatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile(alterPatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern3 = Pattern.compile(dropPatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern4 = Pattern.compile(tuuncatePatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern5 = Pattern.compile(insertPatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern6 = Pattern.compile(updatePatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern7 = Pattern.compile(deletePatternStr,Pattern.CASE_INSENSITIVE);
        Pattern pattern8 = Pattern.compile(selectPatternStr,Pattern.CASE_INSENSITIVE);

        for(String str:arr){
            if(pattern1.matcher(str).find()||str.toUpperCase().startsWith("CREATE")){1
                map.put(str,"CREATE");
            }else if(pattern2.matcher(str).find()||str.toUpperCase().startsWith("ALTER")){1
                map.put(str,"ALTER");
            }else if(pattern3.matcher(str).find()||str.toUpperCase().startsWith("DROP")){
                map.put(str,"DROP");
            }else if(pattern4.matcher(str).find()||str.toUpperCase().startsWith("TRUNCATE")){1
                map.put(str,"TRUNCATE");
            }else if(pattern5.matcher(str).find()||str.toUpperCase().startsWith("INSERT")){1
                map.put(str,"INSERT");
            }else if(pattern6.matcher(str).find()||str.toUpperCase().startsWith("UPDATE")){1
                map.put(str,"UPDATE");
            }else if(pattern7.matcher(str).find()||str.toUpperCase().startsWith("DELETE")){1
                map.put(str,"DELETE");
            }else if(pattern8.matcher(str).find()||str.toUpperCase().startsWith("SELECT")){1
                map.put(str,"SELECT");
            }
        }
        return map;
    }

    public static List<SQLStatement> parseMultiStatement(String sql){
        final DbType dbType = JdbcConstants.XUGU; // 可以是ORACLE、POSTGRESQL、SQLSERVER、ODPS等
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        return stmtList;
    }





    public static List<CreateViewBean> parseCreateView(String sql){
        List<SQLCreateViewStatement> createViewStatementList = new ArrayList<>();
        List<CreateViewBean> createViewBeans = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            SQLCreateViewStatement createViewStatement = (SQLCreateViewStatement) statement;
            createViewStatementList.add(createViewStatement);
        }
        for(SQLCreateViewStatement createViewStatement:createViewStatementList){
            CreateViewBean viewBean;
            if(createViewStatement.getSchema()!=null){
                 viewBean = CreateViewBean.builder().name(createViewStatement.getName().getSimpleName()).
                        schemaName(createViewStatement.getSchema().toString()).tableName(createViewStatement.getTableSource().getTableName()).build();
            }else{
                 viewBean = CreateViewBean.builder().name(createViewStatement.getName().getSimpleName()).
                        schemaName("").tableName(createViewStatement.getTableSource().getTableName()).build();
            }
            createViewBeans.add(viewBean);
        }
        return createViewBeans;
    }

    public static void main(String[] args) {
String sql = "create database parser_db;\n" +
                "create table test(id int,name varchar);\n" +
                "create table test_jkl(id int,money numeric,name varchar,address varchar);\n" +
                "\n" +
                "create index idx1 on cm_title(title);\n" +
                "\n" +
                "alter table test rename to test5;\n" +
                "\n" +
                "alter table test5 add column address varchar;\n" +
                "\n" +
                "drop database parser_db;\n" +
                "drop index cm_title.idx1;\n" +
                "\n" +
                "truncate table test5;\n" +
                "\n" +
                "insert into test5(id,name,address)values(1,'roger','成都');\n" +
                "update test5 set name='roger' where id=1;\n" +
                "delete from test5 where id=1;\n" +
                "select * from test5;\n" +
                "select * from test5 where id=1 and name='roger';\n" +
                "GRANT ROLE role1 TO test_user;"+
                "REVOKE ROLE rOM test_user;"+
                "create role role1;\n" +

 "BEGIN \n" +
                "for i in 1..100 loop\n" +
                "insert into test1(dl)values(100);\n" +
                "end loop;\n" +
                "end;\n" +
                "\n" +
                "select db_id dbId,schema_id schemaId,'表' typeName ,count(*) from sys_tables where  db_id =1 and schema_id = 1 group by db_id ,schema_id ,typeName\n" +
                "\t\t\t\tunion all\n" +
                "\t\t\t\tselect db_id dbId,schema_id schemaId,'存储过程' typeName ,count(*) from sys_procedures where ret_type is null and db_id = 1 and schema_id = 1 group by db_id ,schema_id ,typeName\n" +
                "\t\t\t\tunion all\n" +
                "\t\t\t\tselect db_id dbId,schema_id schemaId,'函数' typeName ,count(*) from sys_procedures where ret_type is not null and db_id = 1 and schema_id = 1 group by db_id ,schema_id ,typeName;\n" +
                "SELECT gsto_no gstoNo,head_no headNo,tail_no tailNo,next_no nextNo,store_sta storeSta,store_num storeNum,\n" +
                "\t\t\t\tconcat(concat(node_id1,'/'),store_no1)  storeNo1,concat(concat(node_id2,'/'),store_no2)  storeNo2, \n" +
                "\t\t\t\tconcat(concat(node_id3,'/'),store_no3)  storeNo3,lsn,DB_name dbName \n" +
                "\t\t\t\tFROM sys_gstores LEFT JOIN SYS_DATABASES ON SYS_DATABASES.db_id = sys_gstores.db_id where gsto_no < 200 ;\n" +
                "\n" +
                "select c.node_ip,a.ip,a.db_name,a.user_name,a.start_t,a.visit_t,a.trans_start_t,\n" +
                "                b.sql,a.session_id,a.curr_tid,\n" +
                "                (EXTRACT_HOUR(SYSDATE-trans_start_t)*60*60+EXTRACT_MINUTE(SYSDATE-trans_start_t)*60+EXTRACT_SECOND(SYSDATE-trans_start_t)) trans_cost,a.sql\n" +
                "                from sys_all_sessions a,sys_all_thd_session b,sys_clusters c where db_name!='SYSTEM'\n" +
                "                and a.session_id = b.session_id and a.curr_tid = b.curr_tid and a.nodeid = c.node_id order by trans_cost desc limit 10;\n";
        List<SQLStatement> sqlStatementList = parseMultiStatement(sql);
        for(SQLStatement statement:sqlStatementList){
            if(statement instanceof SQLSelectStatement){
                System.out.printf(statement.toString());
            }
        }
        System.out.println(222);


        StringBuilder builder2 = new StringBuilder();;
        String sql4 = "begin\n" +
                "proc1(?,?);\n" +
                "proc2(?,?);\n" +
                "end";
        String sql5 = "begin\n" +
                "proc1(:id,:name);\n" +
                "proc2(:id,:name);\n" +
                "end";

        String sql6 = "call xugu_test_pro(id => 5,name=>'ggg',address => 'vvv');";
        String sql7 = "call xugu_test_pro(:1,:2)";
        String sql8 = "call xugu_test_pro(:id,:name)";
        String sql9 = "call xugu_test_return_fun(id => 5,name=>'ggg',address => 'vvv');";

builder2.append(sql4);
        builder2.append(sql5);
        List<BlockProAndFunBean> list = parseBlockProAndFun(builder2.toString());
        System.out.printf("222");

        builder2.append(sql6);
        builder2.append(sql7);
        builder2.append(sql8);
        builder2.append(sql9);

        List<CreateCallBean> list = parseCall(builder2.toString());
        System.out.printf("222");

    }





}
*/

