package com.alibaba.druid.sql.dialect.xugu.api;

import com.alibaba.druid.sql.ast.*;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectJoin;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.xugu.api.bean.*;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguDataTypeIntervalDayToSecond;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguDataTypeIntervalHourToSecond;
import com.alibaba.druid.sql.dialect.xugu.ast.XuguDataTypeIntervalMinuteToSecond;
import com.alibaba.druid.sql.dialect.xugu.ast.stmt.*;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguFunctionDataType;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguProdecureDataType;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;

import java.util.*;
import java.util.stream.Collectors;

public class XuguParserApi {
    
    public static List<CreateProcedureBean> parseCreateProcedure(String sql){
        List<SQLCreateProcedureStatement> createProcedureStatementList = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        for(SQLStatement sqlStatement:statementList){
            if(sqlStatement instanceof SQLCreateProcedureStatement){
                SQLCreateProcedureStatement sqlCreateProcedureStatement = (SQLCreateProcedureStatement) sqlStatement;
                createProcedureStatementList.add(sqlCreateProcedureStatement);
            }
        }
        return getProcedureBeanListByStatement(createProcedureStatementList);
    }

    public static List<CreateFunctionBean> parseCreateFunction(String sql){
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        for(SQLStatement sqlStatement:statementList){
            if(sqlStatement instanceof SQLCreateFunctionStatement){
                SQLCreateFunctionStatement statement = (SQLCreateFunctionStatement) sqlStatement;
                createFunctionStatementList.add(statement);
            }
        }
        return getFunctionBeanListByStatement(createFunctionStatementList);
    }
    
    public static CreatePackageBean parseCreatePackage(String sql){
        CreatePackageBean createPackageBean = new CreatePackageBean();
        List<XuguCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();

        for (SQLStatement statement : statementList) {
            if(statement instanceof XuguCreatePackageStatement){
                XuguCreatePackageStatement xuguCreatePackageStatement = (XuguCreatePackageStatement) statement;
                createPackageStatementList.add(xuguCreatePackageStatement);
            }
        }
        List<SQLCreateProcedureStatement> createProcedureStatements = new ArrayList<>();
        List<SQLCreateFunctionStatement> createFunctionStatements = new ArrayList<>();

        for(SQLStatement sqlStatement:createPackageStatementList.get(0).getStatements()){
            if(sqlStatement instanceof SQLCreateProcedureStatement){
                SQLCreateProcedureStatement statement = (SQLCreateProcedureStatement) sqlStatement;
                createProcedureStatements.add(statement);
            }else if(sqlStatement instanceof SQLCreateFunctionStatement){
                SQLCreateFunctionStatement statement = (SQLCreateFunctionStatement) sqlStatement;
                createFunctionStatements.add(statement);
            }
        }
        createPackageBean.setCreateProcedureBeans(getProcedureBeanListByStatement(createProcedureStatements));
        createPackageBean.setCreateFunctionBeans(getFunctionBeanListByStatement(createFunctionStatements));
        return createPackageBean;
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
            }
            if(sqlCreateTriggerStatement.isUpdate()){
                operators.add("Update");
            }
            if(sqlCreateTriggerStatement.isDelete()){
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
            Map<String,String> attributeMap = new HashMap<>();
            if(!createTypeStatement.isBody()){
                createTypeBean.setHeader(createTypeStatement.toString());
                for(SQLParameter sqlParameter:createTypeStatement.getParameters()){
                    //普通类型
                    if(sqlParameter.getName()!=null){
                        attributeMap.put(sqlParameter.getName().toString(),sqlParameter.getDataType().toString());
                    }else{
                        //过程或函数类型
                        if(sqlParameter.getDataType() instanceof XuguProdecureDataType){
                            attributeMap.put(sqlParameter.getDataType().getName(), "Procedure");
                        }else if(sqlParameter.getDataType() instanceof XuguFunctionDataType){
                            attributeMap.put(sqlParameter.getDataType().getName(), "Function");
                        }

                    }
                }
                createTypeBean.setAttributeMap(attributeMap);
            }else{
                createTypeBean.setBody(createTypeStatement.toString());
                Map<String,String> methodMap = new HashMap<>();
                for(SQLParameter sqlParameter:createTypeStatement.getParameters()){
                    methodMap.put(sqlParameter.getDataType().getName(),sqlParameter.toString());
                }
                createTypeBean.setMethodMap(methodMap);
            }
        }
        return createTypeBean;
    }

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
            OracleSelectQueryBlock block=null;
            if(createViewStatement.getSubQuery().getQuery() instanceof OracleSelectQueryBlock){
                block = (OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery();
            }
            if(createViewStatement.getSchema()!=null){
                viewBean = CreateViewBean.builder().name(createViewStatement.getName().getSimpleName()).
                        schemaName(createViewStatement.getSchema()).tableName(block.getFrom().toString()).build();
            }else{
                viewBean = CreateViewBean.builder().name(createViewStatement.getName().getSimpleName()).
                        schemaName(null).tableName(block.getFrom().toString()).build();
            }
            createViewBeans.add(viewBean);
        }
        return createViewBeans;
    }

    private static List<CreateProcedureBean> getProcedureBeanListByStatement(List<SQLCreateProcedureStatement> createProcedureStatementList){
        List<CreateProcedureBean> returnBeanList = new ArrayList<>();
        for(SQLCreateProcedureStatement statement:createProcedureStatementList){
            CreateProcedureBean createProcedureBean = new CreateProcedureBean();
            createProcedureBean.setProcedureName(statement.getName().toString());
            createProcedureBean.setParamSize(statement.getParameters().size());
            List<Param> params = new ArrayList<>();

            for(int i=0;i<statement.getParameters().size();i++){
                Param param = new Param();
                SQLParameter parameter = statement.getParameters().get(i);
                SQLParameter.ParameterType type = parameter.getParamType();
                SQLExpr expr = parameter.getDefaultValue();
                if(type!=null&&expr!=null){
                    param.setIndex(i+1);
                    param.setName(parameter.getName().toString());
                    param.setParamType(parameter.getParamType().name());
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(parameter.getDefaultValue().toString());
                    getPrecisionAndScale(parameter,param);
                }else if(type!=null&&expr==null){
                    param.setIndex(i);
                    param.setName(parameter.getName().toString());
                    param.setParamType(parameter.getParamType().name());
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(null);
                    getPrecisionAndScale(parameter,param);
                }else if(type==null&expr!=null){
                    param.setIndex(i);
                    param.setName(parameter.getName().toString());
                    param.setParamType("In");
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(parameter.getDefaultValue().toString());
                    getPrecisionAndScale(parameter,param);
                }else{
                    param.setIndex(i);
                    param.setName(parameter.getName().toString());
                    param.setParamType("In");
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(null);
                    getPrecisionAndScale(parameter,param);
                }
                params.add(param);
            }
            createProcedureBean.setParams(params);
            returnBeanList.add(createProcedureBean);
        }
        return returnBeanList;
    }
    
    private static List<CreateFunctionBean> getFunctionBeanListByStatement(List<SQLCreateFunctionStatement> createFunctionStatementList){
        List<CreateFunctionBean> returnBeanList = new ArrayList<>();
        for(SQLCreateFunctionStatement sqlCreateFunctionStatement:createFunctionStatementList){
            CreateFunctionBean createFunctionBean = new CreateFunctionBean();
            createFunctionBean.setFunctionName(sqlCreateFunctionStatement.getName().toString());
            createFunctionBean.setParamSize(sqlCreateFunctionStatement.getParameters().size());
            List<Param> params = new ArrayList<>();
            try{
                createFunctionBean.setReturnType(sqlCreateFunctionStatement.getReturnDataType().toString());
            }catch(Exception e){
                createFunctionBean.setReturnType(sqlCreateFunctionStatement.getReturnDataType().getName());
            }
            for(int i=0;i<sqlCreateFunctionStatement.getParameters().size();i++){
                Param param = new Param();
                SQLParameter parameter = sqlCreateFunctionStatement.getParameters().get(i);
                SQLParameter.ParameterType type = parameter.getParamType();
                SQLExpr expr = parameter.getDefaultValue();
                if(type!=null&&expr!=null){
                    param.setIndex(i+1);
                    param.setName(parameter.getName().toString());
                    param.setParamType(parameter.getParamType().name());
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(parameter.getDefaultValue().toString());
                    getPrecisionAndScale(parameter,param);
                }else if(type!=null&&expr==null){
                    param.setIndex(i);
                    param.setName(parameter.getName().toString());
                    param.setParamType(parameter.getParamType().name());
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(null);
                    getPrecisionAndScale(parameter,param);
                }else if(type==null&expr!=null){
                    param.setIndex(i);
                    param.setName(parameter.getName().toString());
                    param.setParamType("In");
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(parameter.getDefaultValue().toString());
                    getPrecisionAndScale(parameter,param);
                }else{
                    param.setIndex(i);
                    param.setName(parameter.getName().toString());
                    param.setParamType("In");
                    param.setDataType(parameter.getDataType().getName());
                    param.setDefaultValue(null);
                    getPrecisionAndScale(parameter,param);
                }
                params.add(param);
            }
            createFunctionBean.setParams(params);
            returnBeanList.add(createFunctionBean);
        }
        return returnBeanList;
    }
    
    private static void getPrecisionAndScale(SQLParameter parameter,Param param){
        if(parameter.getDataType().getArguments().size()==0){
            param.setPrecision(null); //精度
            param.setScale(null); //标度
        }else if(parameter.getDataType().getArguments().size()==2){
            param.setPrecision(Integer.parseInt(parameter.getDataType().getArguments().get(0).toString()));
            param.setScale(Integer.parseInt(parameter.getDataType().getArguments().get(1).toString()));
        }else if(parameter.getDataType().getArguments().size()==1){
            param.setPrecision(Integer.parseInt(parameter.getDataType().getArguments().get(0).toString()));
            if(parameter.getDataType() instanceof XuguDataTypeIntervalDayToSecond){
                XuguDataTypeIntervalDayToSecond dayToSecond = (XuguDataTypeIntervalDayToSecond) parameter.getDataType();
                if(dayToSecond.getFractionalSeconds().size()>0){
                    param.setScale(Integer.parseInt(dayToSecond.getFractionalSeconds().get(0).toString()));
                }
            }else if(parameter.getDataType() instanceof XuguDataTypeIntervalHourToSecond){
                XuguDataTypeIntervalHourToSecond hourToSecond = (XuguDataTypeIntervalHourToSecond) parameter.getDataType();
                if(hourToSecond.getFractionalSeconds().size()>0){
                    param.setScale(Integer.parseInt(hourToSecond.getFractionalSeconds().get(0).toString()));
                }
            }else if(parameter.getDataType() instanceof XuguDataTypeIntervalMinuteToSecond){
                XuguDataTypeIntervalMinuteToSecond minuteToSecond = (XuguDataTypeIntervalMinuteToSecond) parameter.getDataType();
                if(minuteToSecond.getFractionalSeconds().size()>0){
                    param.setScale(Integer.parseInt(minuteToSecond.getFractionalSeconds().get(0).toString()));
                }
            }else{
                param.setScale(null);
            }
        }
    }
    
    public static String replaceTypeSqlSchema(String sql,HashMap<String,String> map){
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<XuguCreateTypeStatement> createTypeStatementList = new ArrayList<>();
        statementList.forEach(x->{
            if(x instanceof XuguCreateTypeStatement){
                createTypeStatementList.add((XuguCreateTypeStatement) x);
            }
        });
        if(createTypeStatementList.get(0).getName() instanceof SQLPropertyExpr){
            if(map.containsKey(((SQLPropertyExpr) createTypeStatementList.get(0).getName()).getOwnerName())){
                String key = ((SQLPropertyExpr) createTypeStatementList.get(0).getName()).getOwnerName();
                ((SQLPropertyExpr) createTypeStatementList.get(0).getName()).setOwner(map.get(key));
            }
        }
        createTypeStatementList.get(0).getParameters().forEach(x->{
            if(x.getDataType() instanceof XuguFunctionDataType){
                if(((XuguFunctionDataType) x.getDataType()).getBlock() instanceof SQLBlockStatement){
                    replaceBlockSchema((SQLBlockStatement) ((XuguFunctionDataType) x.getDataType()).getBlock(),map);
                }
            }else if(x.getDataType() instanceof XuguProdecureDataType){
                if(((XuguProdecureDataType) x.getDataType()).getBlock() instanceof SQLBlockStatement){
                    replaceBlockSchema((SQLBlockStatement) ((XuguProdecureDataType) x.getDataType()).getBlock(),map);
                }
            }
        });
        return createTypeStatementList.get(0).toString();
    }
    
    public static String replacePackageSqlSchema(String sql,HashMap<String,String> map){
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<XuguCreatePackageStatement> createPackageStatementList = new ArrayList<>();
        statementList.forEach(x->{
            if(x instanceof XuguCreatePackageStatement){
                createPackageStatementList.add((XuguCreatePackageStatement) x);
            }
        });
        //替换包名
        if(createPackageStatementList.get(0).getName() instanceof SQLPropertyExpr){
            if(map.containsKey(((SQLPropertyExpr) createPackageStatementList.get(0).getName()).getOwnerName())){
                String key = ((SQLPropertyExpr) createPackageStatementList.get(0).getName()).getOwnerName();
                ((SQLPropertyExpr) createPackageStatementList.get(0).getName()).setOwner(map.get(key));
            }
        }
        createPackageStatementList.get(0).getStatements().forEach(x->{
            if(x instanceof SQLCreateProcedureStatement){
                SQLCreateProcedureStatement procedureStatement = (SQLCreateProcedureStatement) x;
                if(procedureStatement.getBlock() instanceof SQLBlockStatement){
                    replaceBlockSchema((SQLBlockStatement) procedureStatement.getBlock(),map);
                }
            }else if(x instanceof SQLCreateFunctionStatement){
                SQLCreateFunctionStatement functionStatement = (SQLCreateFunctionStatement) x;
                replaceBlockSchema((SQLBlockStatement) functionStatement.getBlock(),map);
            }
        });
        
        return createPackageStatementList.get(0).toString();
    }
    
    public static String replaceViewSqlSchema(String sql,HashMap<String,String> map){
        XuguStatementParser parser = new XuguStatementParser(sql);
        //List<String> schemas = new ArrayList<>();
        List<SQLCreateViewStatement> createViewStatements = new ArrayList<>();
        List<SQLStatement> statementList = parser.parseStatementList();
        for(SQLStatement statement:statementList){
            if(statement instanceof SQLCreateViewStatement){
                SQLCreateViewStatement createViewStatement = (SQLCreateViewStatement) statement;
                createViewStatements.add(createViewStatement);
            }
        }
        //schemas.add(createViewStatements.get(0).getSchema());
        //TODO 替换视图模式
        if(map.containsKey(createViewStatements.get(0).getSchema())){
            if(createViewStatements.get(0).getName() instanceof SQLPropertyExpr){
                ((SQLPropertyExpr) createViewStatements.get(0).getName()).setOwner(map.get(createViewStatements.get(0).getSchema()));
            }
        }
        //List<String> reverseSchemas = new ArrayList<>();

        for(SQLCreateViewStatement createViewStatement:createViewStatements){
            if(createViewStatement.getSubQuery().getQuery() instanceof OracleSelectQueryBlock){
                //对于join的,v1,v2,v3需倒排序模式
                if(((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom() instanceof OracleSelectJoin){
                    OracleSelectJoin join = (OracleSelectJoin) ((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom();
                    viewRecursion2(join,map);
                }else if(((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom() instanceof OracleSelectTableReference){
                    SQLExpr expr = ((OracleSelectTableReference) ((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom()).getExpr();
                    if(expr instanceof SQLPropertyExpr){
                        //reverseSchemas.add(((SQLPropertyExpr) expr).getOwnerName());
                        if(map.containsKey(((SQLPropertyExpr) expr).getOwnerName())){
                            ((SQLPropertyExpr) expr).setOwner(map.get(((SQLPropertyExpr) expr).getOwnerName()));
                        }
                    }
                }
            }else if(createViewStatement.getSubQuery().getQuery() instanceof SQLUnionQuery){
                //对于union的,union的每个语句里的模式要倒排,但union语句间模式保持顺序不变
                SQLUnionQuery unionQuery = (SQLUnionQuery) createViewStatement.getSubQuery().getQuery();
                for(SQLSelectQuery sqlSelectQuery:unionQuery.getRelations()){
                    List<String> oneUnionReverseSchemas = new ArrayList<>();
                    if(sqlSelectQuery instanceof OracleSelectQueryBlock){
                        if(((OracleSelectQueryBlock) sqlSelectQuery).getFrom() instanceof OracleSelectJoin){
                            OracleSelectJoin join = (OracleSelectJoin) ((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom();
                            viewRecursion2(join,map);
                        }else if (((OracleSelectQueryBlock) sqlSelectQuery).getFrom() instanceof OracleSelectTableReference){
                            SQLExpr expr = ((OracleSelectTableReference) ((OracleSelectQueryBlock) sqlSelectQuery).getFrom()).getExpr();
                            if(expr instanceof SQLPropertyExpr){
                                //oneUnionReverseSchemas.add(((SQLPropertyExpr) expr).getOwnerName());
                                if(map.containsKey(((SQLPropertyExpr) expr).getOwnerName())){
                                    ((SQLPropertyExpr) expr).setOwner(map.get(((SQLPropertyExpr) expr).getOwnerName()));
                                }
                            }
                        }
                    }
                    //Collections.reverse(oneUnionReverseSchemas);
                    //schemas.addAll(oneUnionReverseSchemas);
                }
            }
        }
        //Collections.reverse(reverseSchemas);
        //schemas.addAll(reverseSchemas);
        return createViewStatements.get(0).toString();
    }
    
    private static List<String> getSchemaFromView(String sql){
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<String> schemas = new ArrayList<>();
        List<SQLCreateViewStatement> createViewStatements = new ArrayList<>();
        List<SQLStatement> statementList = parser.parseStatementList();
        for(SQLStatement statement:statementList){
            if(statement instanceof SQLCreateViewStatement){
                SQLCreateViewStatement createViewStatement = (SQLCreateViewStatement) statement;
                createViewStatements.add(createViewStatement);
            }
        }
        schemas.add(createViewStatements.get(0).getSchema());
        List<String> reverseSchemas = new ArrayList<>();

        for(SQLCreateViewStatement createViewStatement:createViewStatements){
            if(createViewStatement.getSubQuery().getQuery() instanceof OracleSelectQueryBlock){
                //对于join的,v1,v2,v3需倒排序模式
                if(((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom() instanceof OracleSelectJoin){
                    OracleSelectJoin join = (OracleSelectJoin) ((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom();
                    viewRecursion(join,reverseSchemas);
                }else if(((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom() instanceof OracleSelectTableReference){
                    SQLExpr expr = ((OracleSelectTableReference) ((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom()).getExpr();
                    if(expr instanceof SQLPropertyExpr){
                        reverseSchemas.add(((SQLPropertyExpr) expr).getOwnerName());
                    }
                }
            }else if(createViewStatement.getSubQuery().getQuery() instanceof SQLUnionQuery){
                //对于union的,union的每个语句里的模式要倒排,但union语句间模式保持顺序不变
                SQLUnionQuery unionQuery = (SQLUnionQuery) createViewStatement.getSubQuery().getQuery();
                for(SQLSelectQuery sqlSelectQuery:unionQuery.getRelations()){
                    List<String> oneUnionReverseSchemas = new ArrayList<>();
                    if(sqlSelectQuery instanceof OracleSelectQueryBlock){
                        if(((OracleSelectQueryBlock) sqlSelectQuery).getFrom() instanceof OracleSelectJoin){
                            OracleSelectJoin join = (OracleSelectJoin) ((OracleSelectQueryBlock) createViewStatement.getSubQuery().getQuery()).getFrom();
                            viewRecursion(join,oneUnionReverseSchemas);
                        }else if (((OracleSelectQueryBlock) sqlSelectQuery).getFrom() instanceof OracleSelectTableReference){
                            SQLExpr expr = ((OracleSelectTableReference) ((OracleSelectQueryBlock) sqlSelectQuery).getFrom()).getExpr();
                            if(expr instanceof SQLPropertyExpr){
                                oneUnionReverseSchemas.add(((SQLPropertyExpr) expr).getOwnerName());
                            }
                        }
                    }
                    Collections.reverse(oneUnionReverseSchemas);
                    schemas.addAll(oneUnionReverseSchemas);
                }
            }
        }
        Collections.reverse(reverseSchemas);
        schemas.addAll(reverseSchemas);
        return schemas;
    }



    /** 视图递归 **/
    private static void viewRecursion(OracleSelectJoin join, List<String> reverseSchemas){
        if(join.getLeft() instanceof OracleSelectTableReference){
            if(((OracleSelectTableReference) join.getRight()).getExpr() instanceof SQLPropertyExpr){
                reverseSchemas.add(((SQLPropertyExpr) ((OracleSelectTableReference) join.getRight()).getExpr()).getOwnerName());
            }
            if(((OracleSelectTableReference) join.getLeft()).getExpr() instanceof SQLPropertyExpr){
                reverseSchemas.add(((SQLPropertyExpr) ((OracleSelectTableReference) join.getLeft()).getExpr()).getOwnerName());
            }
        }else if (join.getLeft() instanceof OracleSelectJoin){
            OracleSelectTableReference reference = (OracleSelectTableReference) join.getRight();
            if(reference.getExpr() instanceof SQLPropertyExpr){
                reverseSchemas.add(((SQLPropertyExpr) reference.getExpr()).getOwnerName());
            }
            if(join.getLeft() instanceof OracleSelectJoin){
                viewRecursion((OracleSelectJoin) join.getLeft(),reverseSchemas);
            }else if(join.getLeft() instanceof OracleSelectTableReference){
                OracleSelectTableReference tableReference = (OracleSelectTableReference) join.getLeft();
                if(tableReference.getExpr() instanceof SQLPropertyExpr){
                    reverseSchemas.add(((SQLPropertyExpr)tableReference.getExpr()).getOwnerName());
                }
            }
        }
       /* if(reverseSchemas.size()>0){
            Collections.reverse(reverseSchemas);
        }*/
    }

    private static void viewRecursion2(OracleSelectJoin join,HashMap<String,String> map){
        if(join.getLeft() instanceof OracleSelectTableReference){
            if(((OracleSelectTableReference) join.getRight()).getExpr() instanceof SQLPropertyExpr){
                //reverseSchemas.add(((SQLPropertyExpr) ((OracleSelectTableReference) join.getRight()).getExpr()).getOwnerName());
                if(map.containsKey(((SQLPropertyExpr) ((OracleSelectTableReference) join.getRight()).getExpr()).getOwnerName())){
                    ((SQLPropertyExpr) ((OracleSelectTableReference) join.getRight()).getExpr()).setOwner(map.get(((SQLPropertyExpr) ((OracleSelectTableReference) join.getRight()).getExpr()).getOwnerName()));
                }
            }
            if(((OracleSelectTableReference) join.getLeft()).getExpr() instanceof SQLPropertyExpr){
                //reverseSchemas.add(((SQLPropertyExpr) ((OracleSelectTableReference) join.getLeft()).getExpr()).getOwnerName());
                if(map.containsKey(((SQLPropertyExpr) ((OracleSelectTableReference) join.getLeft()).getExpr()).getOwnerName())){
                    ((SQLPropertyExpr) ((OracleSelectTableReference) join.getLeft()).getExpr()).setOwner(map.get(((SQLPropertyExpr) ((OracleSelectTableReference) join.getLeft()).getExpr()).getOwnerName()));
                }
            }
        }else if (join.getLeft() instanceof OracleSelectJoin){
            OracleSelectTableReference reference = (OracleSelectTableReference) join.getRight();
            if(reference.getExpr() instanceof SQLPropertyExpr){
                //reverseSchemas.add(((SQLPropertyExpr) reference.getExpr()).getOwnerName());
                if(map.containsKey(((SQLPropertyExpr) reference.getExpr()).getOwnerName())){
                    ((SQLPropertyExpr) reference.getExpr()).setOwner(map.get(((SQLPropertyExpr) reference.getExpr()).getOwnerName()));
                }
            }
            if(join.getLeft() instanceof OracleSelectJoin){
                viewRecursion2((OracleSelectJoin) join.getLeft(),map);
            }else if(join.getLeft() instanceof OracleSelectTableReference){
                OracleSelectTableReference tableReference = (OracleSelectTableReference) join.getLeft();
                if(tableReference.getExpr() instanceof SQLPropertyExpr){
                    //reverseSchemas.add(((SQLPropertyExpr)tableReference.getExpr()).getOwnerName());
                    if(map.containsKey(((SQLPropertyExpr) tableReference.getExpr()).getOwnerName())){
                        ((SQLPropertyExpr) tableReference.getExpr()).setOwner(map.get(((SQLPropertyExpr) tableReference.getExpr()).getOwnerName()));
                    }
                }
            }
        }
       /* if(reverseSchemas.size()>0){
            Collections.reverse(reverseSchemas);
        }*/
    }
    
    public static String replaceProcedureSqlSchema(String sql,HashMap<String,String> map){
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<SQLCreateProcedureStatement> createProcedureStatementList = new ArrayList<>();
        //List<SQLBlockStatement> blockStatementList = new ArrayList<>();
        statementList.forEach(x-> {
            if(x instanceof SQLCreateProcedureStatement) {
                createProcedureStatementList.add((SQLCreateProcedureStatement) x);
            }
            /*}else if(x instanceof SQLBlockStatement){
                blockStatementList.add((SQLBlockStatement) x);
            }*/
        });
        //过程修改模式
        if(createProcedureStatementList.get(0).getName() instanceof SQLPropertyExpr){
            SQLPropertyExpr expr = (SQLPropertyExpr) createProcedureStatementList.get(0).getName();
            if (map.containsKey(expr.getOwnerName())){
                expr.setOwner(map.get(expr.getOwnerName()));
            }
        }

        if(createProcedureStatementList.get(0).getBlock()!=null){
            replaceBlockSchema((SQLBlockStatement) createProcedureStatementList.get(0).getBlock(),map);
        }
        return createProcedureStatementList.get(0).toString();
    }

    public static String replaceFunctionSqlSchema(String sql,HashMap<String,String> map){
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();
        //List<SQLBlockStatement> blockStatementList = new ArrayList<>();
        statementList.forEach(x-> {
            if(x instanceof SQLCreateFunctionStatement) {
                createFunctionStatementList.add((SQLCreateFunctionStatement) x);
            }
            /*}else if(x instanceof SQLBlockStatement){
                blockStatementList.add((SQLBlockStatement) x);
            }*/
        });
        //过程修改模式
        if(createFunctionStatementList.get(0).getName() instanceof SQLPropertyExpr){
            SQLPropertyExpr expr = (SQLPropertyExpr) createFunctionStatementList.get(0).getName();
            if (map.containsKey(expr.getOwnerName())){
                expr.setOwner(map.get(expr.getOwnerName()));
            }
        }

        if(createFunctionStatementList.get(0).getBlock()!=null){
            replaceBlockSchema((SQLBlockStatement) createFunctionStatementList.get(0).getBlock(),map);
        }
        return createFunctionStatementList.get(0).toString();
    }

    public static String replaceTriggerSchema(String sql,HashMap<String,String> map){
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        List<SQLCreateTriggerStatement> createTriggerStatementList = new ArrayList<>();
        statementList.forEach(x->{
            if (x instanceof SQLCreateTriggerStatement){
                createTriggerStatementList.add((SQLCreateTriggerStatement) x);
            }
        });
        //替换触发器名中模式
        if(createTriggerStatementList.get(0).getName() instanceof SQLPropertyExpr){
            SQLPropertyExpr propertyExpr = (SQLPropertyExpr) createTriggerStatementList.get(0).getName();
            if(map.containsKey(propertyExpr.getOwnerName())){
                propertyExpr.setOwner(map.get(propertyExpr.getOwnerName()));
            }
        }
        //替换on
        if(createTriggerStatementList.get(0).getOn().getExpr() instanceof SQLPropertyExpr){
            SQLPropertyExpr propertyExpr = (SQLPropertyExpr) createTriggerStatementList.get(0).getOn().getExpr();
            if(map.containsKey(propertyExpr.getOwnerName())){
                propertyExpr.setOwner(map.get(propertyExpr.getOwnerName()));
            }
        }
        //替换列
        if(createTriggerStatementList.get(0).getUpdateOfColumns().size()>0){
            createTriggerStatementList.get(0).getUpdateOfColumns().forEach(x->{
                if(x instanceof SQLPropertyExpr){
                    SQLPropertyExpr expr = (SQLPropertyExpr) x;
                    if(expr.getOwner() instanceof SQLPropertyExpr){
                        SQLPropertyExpr expr1 = (SQLPropertyExpr) expr.getOwner();
                        if(map.containsKey(expr1.getOwnerName())){
                            expr1.setOwner(map.get(expr1.getOwnerName()));
                        }
                    }
                }
            });
        }
        //替换begin end块中模式
        if(createTriggerStatementList.get(0).getBody() instanceof SQLBlockStatement){
            SQLBlockStatement blockStatement = (SQLBlockStatement) createTriggerStatementList.get(0).getBody();
            replaceBlockSchema(blockStatement,map);
        }

        return createTriggerStatementList.get(0).toString();
    }
    
    private static void replaceBlockSchema(SQLBlockStatement blockStatement,HashMap<String,String> map){
        for(SQLStatement statement:blockStatement.getStatementList()){
            if(statement instanceof XuguInsertStatement){
                XuguInsertStatement insertStatement = (XuguInsertStatement) statement;
                if(map.containsKey(insertStatement.getTableSource().getSchema())){
                    insertStatement.getTableSource().setSchema(map.get(insertStatement.getTableSource().getSchema()));
                }
            }else if(statement instanceof XuguUpdateStatement){
                XuguUpdateStatement updateStatement = (XuguUpdateStatement) statement;
                if(updateStatement.getTableSource() instanceof OracleSelectTableReference){
                    if(((OracleSelectTableReference) updateStatement.getTableSource()).getExpr() instanceof SQLPropertyExpr){
                        if(map.containsKey(((SQLPropertyExpr) ((OracleSelectTableReference) updateStatement.getTableSource()).getExpr()).getOwnerName())){
                            String key = ((SQLPropertyExpr) ((OracleSelectTableReference) updateStatement.getTableSource()).getExpr()).getOwnerName();
                            ((SQLPropertyExpr) ((OracleSelectTableReference) updateStatement.getTableSource()).getExpr()).setOwner(map.get(key));
                        }
                    }
                }
                updateStatement.getItems().forEach(x->{
                    if(x instanceof SQLUpdateSetItem){
                        if(x.getColumn() instanceof SQLPropertyExpr){
                            if(((SQLPropertyExpr) x.getColumn()).getOwner() instanceof SQLPropertyExpr){
                                if (map.containsKey(((SQLPropertyExpr) ((SQLPropertyExpr) x.getColumn()).getOwner()).getOwnerName())){
                                    String key = ((SQLPropertyExpr) ((SQLPropertyExpr) x.getColumn()).getOwner()).getOwnerName();
                                    ((SQLPropertyExpr) ((SQLPropertyExpr) x.getColumn()).getOwner()).setOwner(map.get(key));
                                }
                            }
                        }
                    }
                });
                if(updateStatement.getWhere() instanceof SQLBinaryOpExpr){
                    recursionWhere((SQLBinaryOpExpr) updateStatement.getWhere(),map);
                }
            }else if(statement instanceof XuguDeleteStatement){
                //delete 需替换表名模式和列名中模式
                XuguDeleteStatement deleteStatement = (XuguDeleteStatement) statement;
                if(deleteStatement.getTableSource() instanceof SQLExprTableSource){
                    if(((SQLExprTableSource) deleteStatement.getTableSource()).getExpr() instanceof SQLPropertyExpr){
                        if(map.containsKey(((SQLPropertyExpr) ((SQLExprTableSource) deleteStatement.getTableSource()).getExpr()).getOwnerName())){
                            String key = ((SQLPropertyExpr) ((SQLExprTableSource) deleteStatement.getTableSource()).getExpr()).getOwnerName();
                            ((SQLPropertyExpr) ((SQLExprTableSource) deleteStatement.getTableSource()).getExpr()).setOwner(map.get(key));
                        }
                    }
                }
                if(deleteStatement.getWhere() instanceof SQLBinaryOpExpr){
                    recursionWhere((SQLBinaryOpExpr) deleteStatement.getWhere(),map);
                }
            }else if(statement instanceof SQLSelectStatement){
                    if(((SQLSelectStatement) statement).getSelect().getQuery() instanceof OracleSelectQueryBlock){
                        //先处理selectlist
                        ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getSelectList().forEach(x->{
                            if(x instanceof SQLSelectItem){
                                if(x.getExpr() instanceof SQLPropertyExpr){
                                    if(((SQLPropertyExpr) x.getExpr()).getOwner() instanceof SQLPropertyExpr){
                                        if(map.containsKey(((SQLPropertyExpr) ((SQLPropertyExpr) x.getExpr()).getOwner()).getOwnerName())){
                                            String key = ((SQLPropertyExpr) ((SQLPropertyExpr) x.getExpr()).getOwner()).getOwnerName();
                                            ((SQLPropertyExpr) ((SQLPropertyExpr) x.getExpr()).getOwner()).setOwner(map.get(key));
                                        }
                                    }
                                }
                            }
                        });
                        //然后处理from里的left和right,对于join的,v1,v2,v3需倒排序模式
                        if(((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getFrom() instanceof OracleSelectJoin){
                            recursionWhere((SQLBinaryOpExpr) ((OracleSelectJoin) ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getFrom()).getCondition(),map);
                            OracleSelectJoin join = (OracleSelectJoin) ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getFrom();
                            viewRecursion2(join,map);
                        }else if(((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getFrom() instanceof OracleSelectTableReference){
                            //recursionWhere((SQLBinaryOpExpr) ((OracleSelectJoin) ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getFrom()).getCondition(),map);
                            recursionWhere((SQLBinaryOpExpr) ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getWhere(),map);
                            SQLExpr expr = ((OracleSelectTableReference) ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getFrom()).getExpr();
                            if(expr instanceof SQLPropertyExpr){
                                //reverseSchemas.add(((SQLPropertyExpr) expr).getOwnerName());
                                if(map.containsKey(((SQLPropertyExpr) expr).getOwnerName())){
                                    ((SQLPropertyExpr) expr).setOwner(map.get(((SQLPropertyExpr) expr).getOwnerName()));
                                }
                            }
                        }
                        if(((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getWhere() instanceof SQLBinaryOpExpr){
                            recursionWhere((SQLBinaryOpExpr) ((OracleSelectQueryBlock) ((SQLSelectStatement) statement).getSelect().getQuery()).getWhere(),map);
                        }
                    }else if(((SQLSelectStatement) statement).getSelect().getQuery() instanceof SQLUnionQuery){
                        //先处理selectlist
                        //对于union的,union的每个语句里的模式要倒排,但union语句间模式保持顺序不变
                        SQLUnionQuery unionQuery = (SQLUnionQuery) ((SQLSelectStatement) statement).getSelect().getQuery();
                        for(SQLSelectQuery sqlSelectQuery:unionQuery.getRelations()){
                            //List<String> oneUnionReverseSchemas = new ArrayList<>();
                            if(sqlSelectQuery instanceof OracleSelectQueryBlock){
                                if(((OracleSelectQueryBlock) sqlSelectQuery).getFrom() instanceof OracleSelectJoin){
                                    OracleSelectJoin join = (OracleSelectJoin)((OracleSelectQueryBlock) sqlSelectQuery).getFrom();
                                    viewRecursion2(join,map);
                                }else if (((OracleSelectQueryBlock) sqlSelectQuery).getFrom() instanceof OracleSelectTableReference){
                                    SQLExpr expr = ((OracleSelectTableReference) ((OracleSelectQueryBlock) sqlSelectQuery).getFrom()).getExpr();
                                    if(expr instanceof SQLPropertyExpr){
                                        //oneUnionReverseSchemas.add(((SQLPropertyExpr) expr).getOwnerName());
                                        if(map.containsKey(((SQLPropertyExpr) expr).getOwnerName())){
                                            ((SQLPropertyExpr) expr).setOwner(map.get(((SQLPropertyExpr) expr).getOwnerName()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if(statement instanceof XuguExecuteStatement){
                   if(((XuguExecuteStatement) statement).getDynamicSql() instanceof SQLMethodInvokeExpr){
                       if(((SQLMethodInvokeExpr) ((XuguExecuteStatement) statement).getDynamicSql()).getOwner() instanceof SQLIdentifierExpr){
                           if(map.containsKey(((SQLIdentifierExpr) ((SQLMethodInvokeExpr) ((XuguExecuteStatement) statement).getDynamicSql()).getOwner()).getName())){
                               String key = ((SQLIdentifierExpr) ((SQLMethodInvokeExpr) ((XuguExecuteStatement) statement).getDynamicSql()).getOwner()).getName();
                               ((SQLIdentifierExpr) ((SQLMethodInvokeExpr) ((XuguExecuteStatement) statement).getDynamicSql()).getOwner()).setName(map.get(key));
                           }
                       }
                   }
            }else if(statement instanceof SQLCallStatement){
                if(((SQLCallStatement) statement).getProcedureName() instanceof SQLPropertyExpr){
                    if(map.containsKey(((SQLPropertyExpr) ((SQLCallStatement) statement).getProcedureName()).getOwnerName())){
                        String key = ((SQLPropertyExpr) ((SQLCallStatement) statement).getProcedureName()).getOwnerName();
                        ((SQLPropertyExpr) ((SQLCallStatement) statement).getProcedureName()).setOwner(map.get(key));
                    }
                }
            }
            }
        }

    private static void recursionWhere(SQLBinaryOpExpr binaryOpExpr,HashMap<String,String> map){
        if(binaryOpExpr.getRight() instanceof SQLBinaryOpExpr){
            if(((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft() instanceof SQLPropertyExpr){
                if(((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwner() instanceof SQLPropertyExpr){
                    if(map.containsKey(((SQLPropertyExpr) ((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwner()).getOwnerName())){
                        String key = ((SQLPropertyExpr) ((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwner()).getOwnerName();
                        ((SQLPropertyExpr) ((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwner()).setOwner(map.get(key));
                    }
                }else if(((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwner() instanceof SQLIdentifierExpr){
                    if(map.containsKey(((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwnerName())){
                        String key = ((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).getOwnerName();
                        ((SQLPropertyExpr) ((SQLBinaryOpExpr) binaryOpExpr.getRight()).getLeft()).setOwner(map.get(key));
                    }
                }
            }
            //recursionWhere((SQLBinaryOpExpr) binaryOpExpr.getRight(),map);
        }else if(binaryOpExpr.getRight() instanceof SQLPropertyExpr){
            if(((SQLPropertyExpr) binaryOpExpr.getRight()).getOwner() instanceof SQLPropertyExpr){
                if(map.containsKey(((SQLPropertyExpr) ((SQLPropertyExpr) binaryOpExpr.getRight()).getOwner()).getOwnerName())){
                    String key = ((SQLPropertyExpr) ((SQLPropertyExpr) binaryOpExpr.getRight()).getOwner()).getOwnerName();
                    ((SQLPropertyExpr) ((SQLPropertyExpr) binaryOpExpr.getRight()).getOwner()).setOwner(map.get(key));
                }
            }
        }
        if(binaryOpExpr.getLeft() instanceof SQLBinaryOpExpr){
            recursionWhere((SQLBinaryOpExpr) binaryOpExpr.getLeft(),map);
        }else if(binaryOpExpr.getLeft() instanceof SQLPropertyExpr){
            if(((SQLPropertyExpr) binaryOpExpr.getLeft()).getOwner() instanceof SQLPropertyExpr){
                String key = ((SQLPropertyExpr) ((SQLPropertyExpr) binaryOpExpr.getLeft()).getOwner()).getOwnerName();
                if(map.containsKey(key)){
                    ((SQLPropertyExpr) ((SQLPropertyExpr) binaryOpExpr.getLeft()).getOwner()).setOwner(map.get(key));
                }
            }else if(((SQLPropertyExpr) binaryOpExpr.getLeft()).getOwner() instanceof SQLIdentifierExpr){
                String key = ((SQLPropertyExpr) binaryOpExpr.getLeft()).getOwnerName();
                if(map.containsKey(key)){
                    ((SQLPropertyExpr) binaryOpExpr.getLeft()).setOwner(map.get(key));
                }
            }
        }
    }


}
