package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreatePackageBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import junit.framework.TestCase;

public class XuguSysPack extends TestCase {

    public void test() throws ParserBusinessException {
        //sys_pack DBMS_DBA
        String sql="CREATE OR REPLACE PACKAGE SYSDBA.DBMS_DBA \n" +
                "IS\n" +
                "       PROCEDURE KILL_TRANS(NODEID INTEGER,TRANID BIGINT);\n" +
                "       PROCEDURE KILL_SESSION(NODEID INTEGER,SESSID INTEGER);\n" +
                "       PROCEDURE KILL_SESSION_TRANS(NODEID INTEGER,SESSID INTEGER);\n" +
                "END SYSDBA.DBMS_DBA;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());
    }

    public void test1() throws ParserBusinessException {
        //sys_pack DBMS_OUTPUT
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_OUTPUT \n" +
                "IS\n" +
                "\tPROCEDURE PUT_LINE(str VARCHAR);\n" +
                "END SYSDBA.DBMS_OUTPUT;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数" + packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数" + packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());
    }

    public void test2() throws ParserBusinessException {
        //sys_pack DBMS_INFO
        //TODO subtype类型暂不支持解析
        String sql="CREATE PACKAGE SYSDBA.DBMS_INFO \n" +
                "IS\n" +
                "\tSUBTYPE  FileInfo IS RECORD(IS_DIR BOOLEAN,DB_PATH VARCHAR(256),OS_PATH VARCHAR(256));\n" +
                "\tSUBTYPE  FInfoTab IS TABLE OF FileInfo;\n" +
                "\tFUNCTION FILE_LIST(_dir VARCHAR) RETURN FInfoTab;\n" +
                "END SYSDBA.DBMS_INFO;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());
    }

    public void test3() throws ParserBusinessException {
        //sql_pack DBMS_TEST
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_TEST \n" +
                "IS\n" +
                "\tFUNCTION TEST_PING_PONG1(TARG_NID INTEGER,PACK_NUM INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION TEST_PING_PONG2(TARG_NID INTEGER,PACK_SIZE INTEGER,PACK_NUM INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION TEST_MSG_SEND1(TARG_NID INTEGER,PACK_SIZE INTEGER,PACK_NUM INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION TEST_MSG_SEND2(TARG_NID INTEGER,PACK_SIZE INTEGER,PACK_NUM INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION TEST_RPC_SEND(TARG_NID INTEGER,SEND_SIZE INTEGER) RETURN INTEGER;\n" +
                "END SYSDBA.DBMS_TEST ;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test4() throws ParserBusinessException {
        //sql_pack DBMS_STAT
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_STAT\n" +
                "IS\n" +
                "\tPROCEDURE SET_STAT_INFO(TABLE_NAME VARCHAR,FIELD_NAME VARCHAR,MAX_VAL VARCHAR,MIN_VAL VARCHAR,REPET_RATE DOUBLE,DISPERSION DOUBLE);\n" +
                "\tPROCEDURE ANALYZE_TABLE(TABLE_NAME VARCHAR,FIELD_NAME VARCHAR,SAMPLE_RATE INTEGER,HSITOGRAM_NUM INTEGER);\n" +
                "        PROCEDURE SET_ANALYZE_SCHEME(TABLE_NAME VARCHAR,SCHE_NAME VARCHAR,ENABLE BOOL);\n" +
                "        PROCEDURE SET_ANALYZE_PARAM(TABLE_NAME VARCHAR,MODE TINYINT,THRESHOLD TINYINT,LEVEL TINYINT);\n" +
                "        PROCEDURE SET_ANALYZE_OPTIMIZE(TABLE_NAME VARCHAR,PERIOD SMALLINT,MODE TINYINT);\n" +
                "        PROCEDURE SET_ANALYZE_COLUMNS(TABLE_NAME VARCHAR,FIELD_NAME VARCHAR,STATUS TINYINT); \n" +
                "        FUNCTION  GET_STAT_INFO(TABLE_NAME VARCHAR) RETURN VARCHAR;\n" +
                "END SYSDBA.DBMS_STAT ;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test5() throws ParserBusinessException {
        //sql_pack DBMS_IMPORT
        String sql = "CREATE OR REPLACE  PACKAGE SYSDBA.DBMS_IMPORT\n" +
                "IS\n" +
                "  FUNCTION IMPORT_TABLE_FROM_TXT(TABLE_NAME VARCHAR,MODE INTEGER,FLD_SEPARATOR VARCHAR,REINDEX BOOL) RETURN BIGINT;\n" +
                "  FUNCTION IMPORT_TABLE_FROM_NET(TABLE_NAME VARCHAR,MODE INTEGER,REINDEX BOOL) RETURN BIGINT;\t\n" +
                "END SYSDBA.DBMS_TEST;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    /*public void test5_1(){
        String sql ="CREATE OR REPLACE PACKAGE DBMS_STAT1\n" +
                "AUTHID CURRENT_USER\n" +
                "                IS\n" +
                "              \n" +
                "                       PROCEDURE SET_ANALYZE_PARAM(TABLE_NAME VARCHAR,\"TABLE\" TINYINT,THRESHOLD TINYINT,LEVEL TINYINT);\n" +
                "                       PROCEDURE SET_ANALYZE_OPTIMIZE(TABLE_NAME VARCHAR,PERIOD SMALLINT,MODE TINYINT);\n" +
                "               \n" +
                "                END DBMS_STAT1;";
        final DbType dbType = JdbcConstants.ORACLE; // 可以是ORACLE、POSTGRESQL、SQLSERVER、ODPS等
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        System.out.printf("222");
    }*/
    public void test6() throws ParserBusinessException {
        //sql_pack DBMS_SCHEDULER
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_SCHEDULER \n" +
                "AUTHID CURRENT_USER\n" +
                "IS\n" +
                "\t PROCEDURE CREATE_JOB (\n" +
                "\t   job_name             IN VARCHAR2,\n" +
                "\t   job_type             IN VARCHAR2,\n" +
                "\t   job_action           IN VARCHAR2,\n" +
                "\t   number_of_arguments  IN PLS_INTEGER              DEFAULT 0,\n" +
                "\t   start_date           IN TIMESTAMP  \t\t\t\t\t\t\tDEFAULT NULL,\n" +
                "\t   repeat_interval      IN VARCHAR2                 DEFAULT NULL,\n" +
                "\t   end_date             IN TIMESTAMP \t\t\t\t\t\t\t\tDEFAULT NULL,\n" +
                "\t   job_class            IN VARCHAR2                 DEFAULT 'DEFAULT_JOB_CLASS',\n" +
                "\t   enabled              IN BOOLEAN                  DEFAULT FALSE,\n" +
                "\t   auto_drop            IN BOOLEAN                  DEFAULT TRUE,\n" +
                "\t   comments             IN VARCHAR2                 DEFAULT NULL);\n" +
                "\n" +
                "\t PROCEDURE SET_ATTRIBUTE (\n" +
                "   \tname           IN VARCHAR2,\n" +
                "   \tattribute      IN VARCHAR2,\n" +
                "   \tvalue          IN VARCHAR2);\n" +
                "\n" +
                "\t PROCEDURE SET_ATTRIBUTE (\n" +
                "   \tname           IN VARCHAR2,\n" +
                "   \tattribute      IN VARCHAR2,\n" +
                "   \tvalue          IN TIMESTAMP);\n" +
                "                      \n" +
                "\t PROCEDURE SET_ATTRIBUTE (\n" +
                "   \tname           IN VARCHAR2,\n" +
                "   \tattribute      IN VARCHAR2,\n" +
                "   \tvalue          IN PLS_INTEGER);\n" +
                "   \t\n" +
                "\t PROCEDURE SET_ATTRIBUTE (\n" +
                "   \tname           IN VARCHAR2,\n" +
                "   \tattribute      IN VARCHAR2,\n" +
                "   \tvalue          IN BOOLEAN);\n" +
                "   \t\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN VARCHAR2);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN VARCHAR2);\n" +
                "  \n" +
                "  PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN TINYINT);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN TINYINT); \n" +
                "   \n" +
                "  PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN SMALLINT);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN SMALLINT); \n" +
                "\n" +
                "  PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN INTEGER);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN INTEGER);\n" +
                "\t\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN BIGINT);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN BIGINT);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN FLOAT);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN FLOAT);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN DOUBLE);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN DOUBLE);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN NUMERIC);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN NUMERIC);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN BOOLEAN); \n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN BOOLEAN);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN TIME);\n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN TIME);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN DATE); \n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN DATE);\n" +
                "\n" +
                "PROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_position      IN PLS_INTEGER,\n" +
                "   argument_value         IN DATETIME); \n" +
                "\n" +
                "\tPROCEDURE SET_JOB_ARGUMENT_VALUE (\n" +
                "   job_name               IN VARCHAR2,\n" +
                "   argument_name          IN VARCHAR2,\n" +
                "   argument_value         IN DATETIME);\n" +
                "\n" +
                "\tPROCEDURE RUN_JOB (\n" +
                "   job_name                IN VARCHAR2,\n" +
                "   use_current_session     IN BOOLEAN DEFAULT TRUE);\n" +
                "\n" +
                "\tPROCEDURE ENABLE (\n" +
                "  \tname              \t\tIN VARCHAR2);\n" +
                "\t   \n" +
                "\tPROCEDURE DISABLE (\n" +
                "  \tname              \t\tIN VARCHAR2,\n" +
                "   \tforce             \t\tIN BOOLEAN DEFAULT FALSE);\n" +
                "  \n" +
                "  PROCEDURE DROP_JOB (\n" +
                "   job_name           \t\tIN VARCHAR2,\n" +
                "   force              \t\tIN BOOLEAN DEFAULT FALSE);\n" +
                "\t\n" +
                "\tFUNCTION  JOB_NEXT_RUNTIME(\n" +
                "\t job_name\t\t\t\t\t\t\t\tIN VARCHAR2,\n" +
                "\t last_rt\t\t\t\t\t\t\t\tIN DATETIME) RETURN DATETIME;\n" +
                "\n" +
                "\tFUNCTION  NEXT_RUNTIME(\n" +
                "\t repeat_interval \t\t\t\tIN VARCHAR2,\n" +
                "\t last_rt\t\tIN DATETIME) RETURN DATETIME;\n" +
                "\t \t\t \t   \t \n" +
                "END SYSDBA.DBMS_SCHEDULER;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test7() throws ParserBusinessException {
        //sql_pack DBMS_SQL
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_SQL\n" +
                "AUTHID CURRENT_USER\n" +
                "IS\n" +
                "\t  --参数描述信息结构 \n" +
                "\tSUBTYPE\t TParaInfo\tIS  RECORD(ParaName VARCHAR(128),\t--参数名\n" +
                "\t\t \t\t\tParaNo    INTEGER, \t\t--编号\n" +
                "\t\t \t\t\tParaTid   INTEGER,\t\t--类型ID\n" +
                "\t\t \t\t\tParaScale INTEGER,\t\t--精度标度\n" +
                "\t\t \t\t\tBindState INTEGER);\t \t--绑定状态(0:未绑定 1:已绑定)\t\n" +
                "\tSUBTYPE\t TParams\tIS\tTABLE OF TParaInfo;\n" +
                "\t--结果字段描述信息结构\n" +
                "\tSUBTYPE\t TResInfo\tIS  RECORD(ResName VARCHAR(128),\t--输出字段名\n" +
                "\t\t \t\t\tResTid   INTEGER,\t\t--输出字段类型\n" +
                "\t\t \t\t\tResScale INTEGER,\t\t--输出精度标度\n" +
                "\t\t \t\t\tResFlag  INTEGER,\t\t--输出附加标志\n" +
                "\t\t \t\t\tTargTid INTEGER,\t\t--接收变量的类型ID\n" +
                "\t\t \t\t\tTargScale INTEGER);\t\t--接收变量的类型精度标度\n" +
                "\tSUBTYPE\t TResults\tIS\tTABLE OF TResInfo;\n" +
                "\t--游标对象结构\n" +
                "\tSUBTYPE  TCursor  \tIS  RECORD(StmtState  INTEGER,\t\t--当前状态\n" +
                "\t   \t\t\t\tStmtType INTEGER,\t\t--parse返回的语句类型\n" +
                "\t   \t\t\t\tStmtSQL VARCHAR(2000),\t\t--SQL语句\n" +
                "\t   \t\t\t\tObjPtr VARCHAR(20),\t\t--内部对象名(归系统使用)\n" +
                "\t   \t\t\t\tParaInfos TParams,\t\t--参数描述表\n" +
                "\t   \t\t\t\tResInfos TResults);\t \t--输出描述表\n" +
                "\tSUBTYPE  CursorVarr      IS   VARRAY(100) OF TCursor;\n" +
                "\t--常量定义\n" +
                "\tNATIVE\t\t\tINTEGER :=1;\n" +
                "\t   \n" +
                "\tTYPE_I1\t\t\tINTEGER :=3;\n" +
                "\tTYPE_I2\t\t\tINTEGER :=4;\n" +
                "\tTYPE_I4\t\t\tINTEGER :=5;\n" +
                "\tTYPE_I8\t\t\tINTEGER :=6;\n" +
                "\tTYPE_NUMERIC\t\tINTEGER :=7;\n" +
                "\tTYPE_R4\t\t\tINTEGER :=8;\n" +
                "\tTYPE_R8\t\t\tINTEGER :=9;\t   \n" +
                "\tTYPE_DATE\t\tINTEGER :=10;\n" +
                "\tTYPE_TIME\t\tINTEGER :=11;\n" +
                "\tTYPE_DATETIME\t\tINTEGER :=12;\n" +
                "\tTYPE_STR\t\tINTEGER :=30;\n" +
                "\tTYPE_CHAR\t\tINTEGER :=30;\n" +
                "\tTYPE_VARCHAR\t\tINTEGER :=30;\n" +
                "\tTYPE_CLOB\t\tINTEGER :=32;\n" +
                "\tTYPE_BINARY\t\tINTEGER :=29;\n" +
                "\tTYPE_BLOB\t  \tINTEGER :=30;\n" +
                "\t\n" +
                "\t--游标对象数组 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "\tCursors\t  CursorVarr;\n" +
                "\t   \n" +
                "\tFUNCTION  STROF_CURSOR(CurNo INTEGER) RETURN VARCHAR(5000);\n" +
                "\tFUNCTION  OPEN_CURSOR() RETURN INTEGER;\t\t \n" +
                "\tFUNCTION  IS_OPEN(c IN INTEGER) RETURN BOOLEAN;\n" +
                "\tFUNCTION  PARSE(CurNo INTEGER,StmtSQL VARCHAR,SqlType INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION  EXECUTE(CurNo INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION  FETCH_ROWS(CurNo INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION  CLOSE_CURSOR(CurNo INTEGER) RETURN INTEGER;\n" +
                "\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val TINYINT) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val SMALLINT) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val BIGINT) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val FLOAT) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val DOUBLE) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val NUMERIC) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val VARCHAR) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val CLOB) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val BLOB) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val TIME) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val DATE) RETURN INTEGER;\n" +
                "\tFUNCTION  BIND_VARIABLE(CurNo INTEGER,ColName VARCHAR,Val DATETIME) RETURN INTEGER;\n" +
                "\t\t \n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT TINYINT) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT SMALLINT) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT BIGINT) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT FLOAT) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT DOUBLE) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT NUMERIC) RETURN INTEGER;\t\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT VARCHAR) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT CLOB) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT TIME) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT DATE) RETURN INTEGER;\n" +
                "\tFUNCTION  DEFINE_COLUMN(CurNo INTEGER,ColNo INTEGER,Var  OUT DATETIME) RETURN INTEGER;\n" +
                "\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT TINYINT) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT SMALLINT) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT INTEGER) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT BIGINT) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT FLOAT)  RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT DOUBLE) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT NUMERIC) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT VARCHAR) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT CLOB) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT TIME) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT DATE) RETURN INTEGER;\n" +
                "\tFUNCTION  COLUMN_VALUE(CurNo INTEGER,ColNo INTEGER,Var  OUT DATETIME) RETURN INTEGER;\n" +
                "END  SYSDBA.DBMS_SQL;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());


    }

    public void test8() throws ParserBusinessException {
        //sql_pack CTX_DOC
        // "CREATE OR REPLACE PACKAGE  \"SYSDBA\".\"CTX_DOC\"\n" +
        String sql =  "CREATE OR REPLACE PACKAGE  \"SYSDBA\".\"CTX_DOC\"\n" +
                "AUTHID CURRENT_USER\n" +
                "COMMENT '全文检索支持包'  \n" +
                "IS\n" +
                "\tSUBTYPE TOKEN_REC  IS RECORD(TOKEN VARCHAR(64),OFFSET INTEGER,LENGTH INTEGER);\n" +
                "\tSUBTYPE TOKEN_REC2 IS RECORD(TOKEN VARCHAR(64),REPET_N INTEGER,LENGTH INTEGER);\n" +
                "\tSUBTYPE TOKEN_TAB  IS TABLE OF TOKEN_REC;\n" +
                "\tSUBTYPE TOKEN_TAB2 IS TABLE OF TOKEN_REC2;\n" +
                "\t\n" +
                "\tSP_MAX_LEN\t\tCONSTANT INTEGER := 0;\t--按最长词划分\n" +
                "\tSP_MIN_LEN\t\tCONSTANT INTEGER := 1;\t--按最短词划分\n" +
                "\tSP_SUB_WORD1\tCONSTANT INTEGER := 2;\t--结果包括大词的所有子词\n" +
                "\tSP_SUB_WORD2\tCONSTANT INTEGER := 4;\t--结果包括大词有非头部子词\n" +
                "\tSP_ASCII\t    CONSTANT INTEGER := 8;\t--结果包括ascii串\n" +
                "\tSP_DIGIT\t    CONSTANT INTEGER := 16; --结果包括数字串\n" +
                "\t\n" +
                "\tSPILIT_POLICY INTEGER:= 0;\t--MAX_LEN\n" +
                "\tVOCA_NAME VARCHAR(20):='STD_VOC';\n" +
                "\tKEY_TYPE  VARCHAR(10):='PRIMARY';\n" +
                "\t\n" +
                "\tPROCEDURE SET_KEY_TYPE(key_type  IN VARCHAR2);  --设置键类型\n" +
                "\tPROCEDURE SET_SPLIT_POLICY(_policy  IN INTEGER);--设置分词策略\n" +
                "\tPROCEDURE TOKENS(index_name      IN VARCHAR2,\t\t\n" +
                "                   textkey         IN VARCHAR2,\n" +
                "                   restab          IN OUT TOKEN_TAB);\n" +
                "  PROCEDURE TOKENS(index_name      IN VARCHAR2,\n" +
                "                   textkey         IN VARCHAR2,\n" +
                "                   restab          IN VARCHAR2,\n" +
                "                   query_id        IN NUMBER DEFAULT 0);\n" +
                "  PROCEDURE TOKENS(content         IN VARCHAR2,\n" +
                "                   restab          IN OUT TOKEN_TAB);\n" +
                "  PROCEDURE TOKENS(content         IN CLOB,\n" +
                "                   restab          IN OUT TOKEN_TAB);\n" +
                "               \n" +
                "\tPROCEDURE TOKENS2(index_name     IN VARCHAR2,\n" +
                "                    textkey        IN VARCHAR2,\n" +
                "                    restab         IN OUT TOKEN_TAB);\n" +
                "  PROCEDURE TOKENS2(index_name     IN VARCHAR2,\n" +
                "                    textkey        IN VARCHAR2,\n" +
                "                    restab         IN VARCHAR2,\n" +
                "                    query_id       IN NUMBER DEFAULT 0);\n" +
                "  PROCEDURE TOKENS2(content        IN VARCHAR2,\n" +
                "                    restab         IN OUT TOKEN_TAB);\n" +
                "  PROCEDURE TOKENS2(content        IN CLOB,\n" +
                "                    restab         IN OUT TOKEN_TAB);\n" +
               // "END  SYSDBA.CTX_DOC;";
        "END  \"SYSDBA\".\"CTX_DOC\";";

        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test9() throws ParserBusinessException {
        //sql_pack DBMS_LOB
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_LOB \n" +
                "IS\n" +
                "\tFUNCTION  GETLENGTH(lob_loc BLOB) RETURN INTEGER;\n" +
                "\tFUNCTION  GETLENGTH(lob_loc CLOB) RETURN INTEGER;\n" +
                "\tPROCEDURE READ(lob_loc BLOB,AMOUNT INTEGER,OFFSET INTEGER, OUT_BUFF OUT VARCHAR);\n" +
                "\tPROCEDURE READ(lob_loc CLOB,AMOUNT INTEGER,OFFSET INTEGER, OUT_BUFF OUT VARCHAR);\n" +
                "END SYSDBA.DBMS_LOB;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test10() throws ParserBusinessException {
        //sql_pack UTL_RAW
        String sql = "CREATE PACKAGE SYSDBA.UTL_RAW\n" +
                "IS    \n" +
                "       big_endian     CONSTANT PLS_INTEGER := 1;\n" +
                "       little_endian  CONSTANT PLS_INTEGER := 2;\n" +
                "       machine_endian CONSTANT PLS_INTEGER := 3;  \n" +
                "       FUNCTION BIT_AND (r1 IN RAW, r2 IN RAW) RETURN RAW;\n" +
                "       FUNCTION BIT_COMPLEMENT (r1 IN RAW, r2 IN RAW) RETURN RAW;\n" +
                "       FUNCTION BIT_OR (r1 IN RAW, r2 IN RAW) RETURN RAW;\n" +
                "       FUNCTION BIT_XOR (r1 IN RAW, r2 IN RAW) RETURN RAW;\n" +
                "       FUNCTION CAST_FROM_BINARY_DOUBLE (n IN BINARY_DOUBLE, endianess IN BINARY_INTEGER DEFAULT 1) RETURN RAW;\n" +
                "       FUNCTION CAST_FROM_BINARY_FLOAT (n IN BINARY_FLOAT, endianess IN BINARY_INTEGER DEFAULT 1) RETURN RAW;\n" +
                "       FUNCTION CAST_FROM_BINARY_INTEGER (n IN BINARY_INTEGER, endianess IN BINARY_INTEGER DEFAULT 1) RETURN RAW;\n" +
                "       FUNCTION CAST_FROM_NUMBER (n  IN NUMBER) RETURN RAW;\n" +
                "       FUNCTION CAST_TO_BINARY_DOUBLE (r IN RAW,endianess IN BINARY_INTEGER DEFAULT 1) RETURN BINARY_DOUBLE;\n" +
                "       FUNCTION CAST_TO_BINARY_FLOAT (r IN RAW, endianess IN BINARY_INTEGER DEFAULT 1) RETURN BINARY_FLOAT;\n" +
                "       FUNCTION CAST_TO_BINARY_INTEGER (r IN RAW, endianess IN BINARY_INTEGER DEFAULT 1) RETURN BINARY_INTEGER;\n" +
                "       FUNCTION CAST_TO_NUMBER (r IN RAW) RETURN NUMBER;\n" +
                "       FUNCTION CAST_TO_NVARCHAR2 (r IN RAW) RETURN NVARCHAR2;\n" +
                "       FUNCTION CAST_TO_RAW (c IN VARCHAR2) RETURN RAW;\n" +
                "       FUNCTION CAST_TO_VARCHAR2 (r IN RAW) RETURN VARCHAR2;\n" +
                "       FUNCTION COMPARE (r1 IN RAW,r2 IN RAW,pad IN RAW DEFAULT NULL) RETURN INTEGER;\n" +
                "       FUNCTION CONCAT (r1  IN RAW DEFAULT NULL,\n" +
                "\t\t\t  r2  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr3  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr4  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr5  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr6  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr7  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr8  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr9  IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr10 IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr11 IN RAW DEFAULT NULL,\n" +
                "   \t\t\tr12 IN RAW DEFAULT NULL) \n" +
                "  \t\t\tRETURN RAW;\n" +
                "       FUNCTION CONVERT(r IN RAW,to_charset IN VARCHAR2,from_charset IN VARCHAR2) RETURN RAW;\n" +
                "       FUNCTION COPIES (r IN RAW,n IN INTEGER) RETURN RAW;\n" +
                "       FUNCTION LENGTH (r IN RAW) RETURN INTEGER;\n" +
                "       FUNCTION OVERLAY(overlay_str IN RAW,target IN RAW,\n" +
                "\t\t\tpos IN BINARY_INTEGER DEFAULT 1,\n" +
                "\t\t\tlen IN BINARY_INTEGER DEFAULT NULL,\n" +
                "   \t\t\tpad IN RAW DEFAULT NULL) \n" +
                "  \t\t\tRETURN RAW;\n" +
                "       FUNCTION REVERSE (r IN RAW) RETURN RAW;\n" +
                "       FUNCTION SUBSTR (r IN RAW,pos IN BINARY_INTEGER,len IN BINARY_INTEGER DEFAULT NULL) RETURN RAW;\n" +
                "       FUNCTION TRANSLATE (r IN RAW,from_set IN RAW,to_set IN RAW) RETURN RAW;\n" +
                "       FUNCTION TRANSLITERATE (r IN RAW,to_set IN RAW DEFAULT NULL,from_set IN RAW DEFAULT NULL,pad IN RAW DEFAULT NULL) RETURN RAW;\n" +
                "       FUNCTION XRANGE (start_byte IN RAW DEFAULT NULL,end_byte IN RAW DEFAULT NULL) RETURN RAW;\n" +
                "END SYSDBA.UTL_RAW;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test11() throws ParserBusinessException {
        //sql_pack DBMS_REPLICATION
        String sql ="CREATE OR REPLACE PACKAGE SYSDBA.DBMS_REPLICATION\n" +
                "IS    \n" +
                "  PROCEDURE CREATE_MODIFY_SOURCE(SCHEMA_NAME VARCHAR,TABLE_NAME VARCHAR);\n" +
                "  PROCEDURE DROP_MODIFY_SOURCE(SCHEMA_NAME VARCHAR,TABLE_NAME VARCHAR);\n" +
                " \n" +
                "  PROCEDURE CREATE_SUBSCRIBER(SUBSCRIBER_NAME VARCHAR,USEER_NAME VARCHAR);\n" +
                "  PROCEDURE DROP_SUBSCRIBER(SUBSCRIBER_NAME VARCHAR,USEER_NAME VARCHAR);\n" +
                "\n" +
                "  PROCEDURE POLL_MODIFY_DATA(SUBSCRIBER_NAME VARCHAR,PARTI_NO INTEGER,FILTER VARCHAR,POLL_LEN BIGINT,KEEP_POS BOOLEAN,FNO INTEGER DEFAULT -1,FPOS BIGINT DEFAULT 0,RET_TYPE INTEGER DEFAULT 0);\n" +
                "END SYSDBA.DBMS_REPLICATION;";

        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test12() throws ParserBusinessException {
        //sql_pack DBMS_CRYPTO
        String sql = "CREATE OR REPLACE PACKAGE SYSDBA.DBMS_CRYPTO IS\n" +
                "    -- hash算法\n" +
                "    HASH_MD4  INTEGER :=     1;\n" +
                "    HASH_MD5  INTEGER :=     2;\n" +
                "    HASH_SH1  INTEGER :=     3;\n" +
                "\n" +
                "    -- 消息认证码hash方式\n" +
                "    HMAC_MD5  INTEGER :=     1;\n" +
                "    HMAC_SH1  INTEGER :=     2;\n" +
                "\n" +
                "    -- 块加密算法\n" +
                "    ENCRYPT_DES        INTEGER :=     1;  -- 0x0001\n" +
                "    ENCRYPT_3DES_2KEY  INTEGER :=     2;  -- 0x0002\n" +
                "    ENCRYPT_3DES       INTEGER :=     3;  -- 0x0003\n" +
                "    ENCRYPT_AES        INTEGER :=     4;  -- 0x0004\n" +
                "    ENCRYPT_PBE_MD5DES INTEGER :=     5;  -- 0x0005\n" +
                "    ENCRYPT_AES128     INTEGER :=     6;  -- 0x0006\n" +
                "    ENCRYPT_AES192     INTEGER :=     7;  -- 0x0007\n" +
                "    ENCRYPT_AES256     INTEGER :=     8;  -- 0x0008\n" +
                "\n" +
                "    -- 块加密模式\n" +
                "    CHAIN_CBC INTEGER :=   256;  -- 0x0100\n" +
                "    CHAIN_CFB INTEGER :=   512;  -- 0x0200\n" +
                "    CHAIN_ECB INTEGER :=   768;  -- 0x0300\n" +
                "    CHAIN_OFB INTEGER :=  1024;  -- 0x0400\n" +
                "\n" +
                "    -- 块加密填充方式\n" +
                "    PAD_PKCS5 INTEGER :=  4096;  -- 0x1000\n" +
                "    PAD_NONE  INTEGER :=  8192;  -- 0x2000\n" +
                "    PAD_ZERO  INTEGER := 12288;  -- 0x3000\n" +
                "    PAD_XUGU  INTEGER := 16384;  -- 0x4000\n" +
                "\n" +
                "    -- 流加密算法\n" +
                "    ENCRYPT_RC4        INTEGER :=   129;  -- 0x0081\n" +
                "    \n" +
                "    -- 常用加密套件\n" +
                "    DES_CBC_PKCS5      INTEGER := ENCRYPT_DES+ CHAIN_CBC+ PAD_PKCS5;\n" +
                "    DES3_CBC_PKCS5     INTEGER := ENCRYPT_3DES+ CHAIN_CBC+ PAD_PKCS5;\n" +
                "    AES_CBC_PKCS5      INTEGER := ENCRYPT_AES+ CHAIN_CBC+ PAD_PKCS5;\n" +
                "\t\t--加密接口\n" +
                "    FUNCTION  Encrypt (src IN RAW,typ IN INTEGER, key IN RAW,iv IN RAW DEFAULT NULL) RETURN RAW;\n" +
                "    FUNCTION  Encrypt (src IN VARCHAR,typ IN INTEGER, key IN VARCHAR,iv IN VARCHAR DEFAULT NULL) RETURN RAW;\n" +
                "    \n" +
                "    PROCEDURE Encrypt (dst IN OUT BLOB,src IN BLOB,typ IN INTEGER,key IN RAW,iv IN RAW DEFAULT NULL);\n" +
                "    PROCEDURE Encrypt (dst IN OUT BLOB,src IN CLOB ,typ IN INTEGER,key IN RAW,iv  IN RAW DEFAULT NULL);\n" +
                "\t\t--解密接口\n" +
                "    FUNCTION  Decrypt (src IN RAW,typ IN INTEGER,key IN RAW,iv  IN RAW DEFAULT NULL)RETURN RAW;\n" +
                "    FUNCTION  Decrypt (src IN RAW,typ IN INTEGER,key IN VARCHAR,iv  IN VARCHAR DEFAULT NULL)RETURN VARCHAR;\n" +
                "    \n" +
                "    PROCEDURE Decrypt (dst IN OUT BLOB,src IN BLOB,typ IN INTEGER,key IN RAW,iv  IN RAW DEFAULT NULL);\n" +
                "    PROCEDURE Decrypt (dst IN OUT CLOB,src IN BLOB,typ IN INTEGER,key IN RAW,iv  IN RAW DEFAULT NULL);\n" +
                "\n" +
                "\t\t--hash 接口\n" +
                "    FUNCTION Hash (src IN RAW,typ IN INTEGER)RETURN RAW ;\n" +
                "    FUNCTION Hash (src IN BLOB,typ IN INTEGER)RETURN RAW ;\n" +
                "    FUNCTION Hash (src IN CLOB,typ IN INTEGER)RETURN RAW ;\n" +
                "\n" +
                "\t\t--消息认证码\n" +
                "    FUNCTION Mac (src IN RAW,typ IN INTEGER,key IN RAW)RETURN RAW;\n" +
                "    FUNCTION Mac (src IN BLOB,typ IN INTEGER,key IN RAW)RETURN RAW;\n" +
                "    FUNCTION Mac (src IN CLOB,typ IN INTEGER,key IN RAW)RETURN RAW;\n" +
                "\n" +
                "\t\t--随机函数\n" +
                "    FUNCTION RandomBytes (number_bytes IN INTEGER) RETURN RAW;\n" +
                "    FUNCTION RandomNumber RETURN NUMBER;\n" +
                "    FUNCTION RandomInteger RETURN INTEGER;\n" +
                "\n" +
                "END SYSDBA.DBMS_CRYPTO;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test13() throws ParserBusinessException {
        //sql_pack DBMS_BACKUP
        //"CREATE OR REPLACE PACKAGE \"SYSDBA\".\"DBMS_BACKUP\"\n" +
        String sql = "CREATE OR REPLACE PACKAGE \"SYSDBA\".\"DBMS_BACKUP\"\n" +
                "COMMENT '''''ӦӦóóóóóó'  \n" +
                "IS\n" +
                "  /**\n" +
                "        :\n" +
                "    planname\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    plantype \t\t\t\t\t\t\t\t\t ϵͳͳͳͳݣݣBAKKKor  \u07FC\u07FC\u07FC\u07FC\u07FCݣݣEXPPP\n" +
                "    path\t\t \t\t\t\t\t\tļļļļļļ·······ʱַַַַַַַַַַַַֻ֧֧·············ֱֱֱֱֱ֧֧ͻͻͻͻ\n" +
                "    starttime\t\tƻƻƻƻʼʱʱʱ\n" +
                "    period\t\t\tƻƻִִִִִִڣڣڣڣڣڣΪΪΪλ\n" +
                "  **/\n" +
                "\tPROCEDURE  Create_Plan(planname VARCHAR, plantype VARCHAR, path VARCHAR, starttime DATETIME, period INTERVAL DAY TO MINUTE);\n" +
                "\t\n" +
                "\t/**\n" +
                "        :\n" +
                "    planname\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    ena \t\t\ttruee\uEDAF̬̬̬falseeeee̬̬̬ϵͳͳͳͳҽҽҽҽһһһһƻƻΪΪ\uEDAF̬̬̬\n" +
                "  **/\n" +
                "\tPROCEDURE  Enable_Plan(planname VARCHAR, ena BOOLEAN);\t\n" +
                "\t\t\n" +
                "\t/**\n" +
                "        :\n" +
                "    planname\t\t\t\tݼƻƻƻƻƻƣƣƣƣƣƣɾɾɾɾ\uEDAF̬̬ƻƻƻƻɾɾɾʱͬʱɾɾɾɾƻƻƻƻƻƻƻƻƻƻĿ\n" +
                "  **/\n" +
                "\tPROCEDURE  Drop_Plan(planname VARCHAR); \n" +
                "\t\n" +
                "\t/**\n" +
                "\t\tϵͳͳͳͳݣݣBAKKKK\uED34\uED34\uED34\uED34ӿӿ,,,,,,,,,,,ȫȫȫȫȫʽ\n" +
                "        :\n" +
                "    planname\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    filename \t\t\t\t\t\t\t\t\t\t\tļļļļ\n" +
                "    time_offset\t\t\t\t\t\tڼƻƻƻƻʼʱʱʱʱʱʱʱʱƫƫƣƣƣƣƣƣΪΪΪλλλλλλλλܳܳܳܳܳƻƻƻƻƻƻ\n" +
                "    iperiod\t\t\t\tƻƻִִִִִִڣڣڣڣڣڣΪΪΪλ,ȫȫȫʱʱ˲˲˲˲ΪNULL\n" +
                "  **/\n" +
                "\tPROCEDURE  Add_Backup_Item(planname\tVARCHAR,filename VARCHAR, time_offset INTERVAL DAY TO MINUTE);\n" +
                "\tPROCEDURE  Add_Backup_Inc_Item(planname\tVARCHAR,time_offset INTERVAL DAY TO MINUTE,iperiod INTERVAL DAY TO MINUTE DEFAULT NULL);\n" +
                "\t\n" +
                "\t/**\n" +
                "\t\t\t\u07FC\u07FC\u07FC\u07FC\u07FCݣݣEXPPPP\uED34\uED34\uED34\uED34ӿӿ,,,,,,⼶⼶ͱ�\n" +
                "        :\n" +
                "    planname\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    obj_name\t\t\t\t\t\t\t\t\t\t\t\t\n" +
                "    obj_type\t\t\t\t\tݶݶݶݶݶݶݶݶ ȡֵֵֵDATABASE,TABLE)\n" +
                "    filename \t\t\t\t\t\t\t\t\t\t\tļļļļļļļļļļļļļļ·····ϢϢϢ\n" +
                "    time_offset\t\t\t\t\t\tڼƻƻƻƻʼʱʱʱʱʱʱʱʱƫƫƣƣƣƣƣƣΪΪΪλλλλλλλλܳܳܳܳܳƻƻƻƻƻƻ\n" +
                "  **/\n" +
                "\tPROCEDURE  Add_Export_Item(planname VARCHAR,obj_name VARCHAR, obj_type\tVARCHAR, filename VARCHAR,time_offset INTERVAL DAY TO MINUTE);\n" +
                "\t\n" +
                "\t/**\n" +
                "\t\t\tļļļļļļļļļļļӽӿӿ\n" +
                "        :\n" +
                "    planname\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    filename \t\t\t\t\t\t\t\t\t\t\tļļļļļļļļļļļļļļ·····ϢϢϢ\n" +
                "    bak_count\t\t\t\t\t\t\tļļļļļļļļļļ\n" +
                "    optype\t\t\t\t\t\t\t\tļļļļļ�ʽDELETEEEMOVE\n" +
                "    dstpath\t\t\tMOVE      ǵǵĿĿĿ···\n" +
                "  **/\n" +
                "\tPROCEDURE  Add_File_Item(planname VARCHAR,filename VARCHAR, bak_count INTEGER,optype VARCHAR,dstpath VARCHAR DEFAULT NULL);\n" +
                "\t\t\t\t\t\t\n" +
                "\t/**\n" +
                "\t\tɾɾɾϵͳͳͳͳݼƻƻƻƻƻƻ\n" +
                "        :\n" +
                "    planname\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    item_n \t\t\t\t\t\t\t\t\t\tţţţţţţʱʱԶԶԶԶԶɣɣ\n" +
                "  **/\n" +
                "\tPROCEDURE  Drop_Item(planname VARCHAR, item_n INTEGER);\n" +
                "\t\n" +
                "\t/**\n" +
                "\t\tɾɾɾɾ\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FC\u07FCļļļļļļļļļļ\n" +
                "        :\n" +
                "    planname\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    objtype \t\t\t\t\t\t\t\t\t\t ȡֵֵֵ'DATABASE','TABLE','FILE'''\n" +
                "  **/\n" +
                "\tPROCEDURE  Alter_Plan_Del_Item(planname VARCHAR,objtype VARCHAR,objname VARCHAR);\t\n" +
                "\t/**\n" +
                "\t\t\t\tüƻƻƻƻǰǰǰĿ\n" +
                "\t     :\n" +
                "    planname\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    curr_item \t\t\tǰǰǰĿĿĿĿĿ\n" +
                "\t**/\n" +
                "\tPROCEDURE  alter_modify_curr_item(planname VARCHAR,curr_item INTEGER);\n" +
                "\t/**\n" +
                "\t\t\t\t\t\t´´´´´´ʱʱʱ\n" +
                "\t\t\t\t\t:\n" +
                "    planname\t\t\t\t\t\tݼƻƻƻƻƻƻ\n" +
                "    curr_item \t\t\t\tǰǰǰĿĿĿĿĿ\n" +
                "    next_run_time  ´´´´´´ʱʱʱ\n" +
                "    istart_t\t\t\t\t\t\t\t\t\tʼʱʱʱ\n" +
                "\t**/\n" +
                "\tPROCEDURE  alter_modify_run_time(planname VARCHAR,curr_item INTEGER,next_run_time DATETIME,istart_t DATETIME DEFAULT NULL);\n" +
                "\t\n" +
                "\t/**\n" +
                "\t\t\t\t\tݼƻƻִִкккк\n" +
                "  **/\t\t\t\t\t\t\n" +
                "\tPROCEDURE  Run();\n" +
                "\t\n" +
                "\t/**\n" +
                "\t\t\t\t־־ļļɾɾɾɾӿڣڣڣڣδʹʹʹ\n" +
                "  **/\t\n" +
                "\tPROCEDURE  Del_xfn();\n" +
                "\tPROCEDURE  FILE_OPTION(PATH VARCHAR,OBJ_NAME VARCHAR,BAK_COUNT INT,OP_TYPE VARCHAR,PLAN_NAME VARCHAR,ITEM_NO INT);\n" +
                " \n" +
               // "END SYSDBA.DBMS_BACKUP;";
               "END \"SYSDBA\".\"DBMS_BACKUP\"";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());

    }

    public void test14() throws ParserBusinessException {
        String sql = "CREATE OR REPLACE PACKAGE DBMS_STAT1\n" +
                "comment '测试包'\n" +
                "                IS\n" +
                "              \n" +
                "                       PROCEDURE SET_ANALYZE_PARAM(TABLE_NAME VARCHAR,\"TABLE\" TINYINT,THRESHOLD TINYINT,LEVEL TINYINT);\n" +
                "                       PROCEDURE SET_ANALYZE_OPTIMIZE(TABLE_NAME VARCHAR,PERIOD SMALLINT,MODE TINYINT);\n" +
                "               \n" +
                "                END DBMS_STAT1;";
        CreatePackageBean packageBean = XuguParserApi.parseCreatePackage(sql);
        System.out.printf("存储过程个数"+packageBean.getCreateProcedureBeans().size());
        System.out.println(packageBean.getCreateProcedureBeans());
        System.out.printf("存储函数个数"+packageBean.getCreateFunctionBeans().size());
        System.out.println(packageBean.getCreateFunctionBeans());
    }
}
