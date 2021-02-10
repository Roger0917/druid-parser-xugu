package com.alibaba.druid.xugu.procedure;

import cn.hutool.core.map.CaseInsensitiveMap;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLBlockStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateProcedureStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import com.alibaba.druid.xugu.XuguTest;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcedureTest2 extends XuguTest {

    public void test() throws ParserBusinessException {
        StringBuilder builder = new StringBuilder();
        String sql = "-- Create Procedure --\n" +
                "CREATE OR REPLACE PROCEDURE prc_test1()\n" +
                " IS\n" +
                " BEGIN \n" +
                "FOR i IN 1..10 LOOP\n" +
                " INSERT INTO test_t(id,dtime) VALUES(myseq1.nextval(),current_time);\n" +
                " END LOOP;\n" +
                " INSERT INTO test_t(id,dtime,info) VALUES(DEFAULT,current_time,'过 程执行完成 '||to_char( myseq2.nextval())||' 次'); \n" +
                "COMMIT;\n" +
                " END;";
        String sql1 = "CREATE OR REPLACE PROCEDURE test_p_jobs ()\n" +
                " AS i INT; BEGIN SELECT count(*) INTO i FROM test_job;\n" +
                " INSERT INTO test_job VALUES(i,to_char('本次插入时间为'||systime()));COMMIT; END;";
        String sql2 = "create or replace  procedure test_pro2(num1 in smallint ,num2 in integer,aa in  varchar, num3 in float,tt out date)\n" +
                "as \n" +
                "begin\n" +
                "insert into test_table2 (num11,num22,name,num33) values(num1,num2,aa,num3);\n" +
                "tt:=sysdate;\n" +
                "end;\n";
        String sql3 = "create or replace procedure test_proc(names in varchar,outStr out varchar(20))\n" +
                "\n" +
                "as \n" +
                "begin\n" +
                "select password into outstr  from USERT where name = names;\n" +
                " end;";
        String sql4 = "create or replace \n" +
                " procedure proc9(name1 out varchar, pass out numeric,name2 out varchar,name3  varchar)\n" +
                " as\n" +
                " begin\n" +
                "select num1,xugu1 ,xugu2 into pass ,  name1,  name2 from  test_numeric1 where xugu3 = name3;\n" +
                "end;";
        String sql5 = "CREATE OR REPLACE PROCEDURE test_proc1()\n" +
                " IS\n" +
                " BEGIN \n" +
                " \n" +
                " SELECT * from test_table1;\n" +
                " \n" +
                "COMMIT;\n" +
                " END;";
        String sql6 = "CREATE OR REPLACE PROCEDURE sysdba.test_proc2(NAME  VARCHAR)\n" +
                " IS\n" +
                " BEGIN \n" +
                " \n" +
                "send_msg('ssss');\n" +
                " \n" +
                " END;";
        String sql7 = "create or replace procedure test_proc3(ss in interval year to month)\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";
        String sql8 = "CREATE OR REPLACE PROCEDURE job_proc1(_i INTEGER) \n" +
                "IS BEGIN INSERT INTO job_test VALUES(_i,sysdate);\n" +
                " send_msg('job_proc1 running'); END; ";
        String sql9 = "create or replace procedure proc_setBlob(inparam_cnt int, inparam_num blob) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getBlob(CNT,LB) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setBlob;";
        String sql10 = "create or replace procedure proc_getBlob(inparam int, ret out blob) \n" +
                "is\n" +
                "begin\n" +
                "   select lb into ret from Test_getBlob where cnt =inparam;\n" +
                "end proc_getBlob;";
        String sql11 = "create or replace procedure proc_setBoolean(inparam_cnt int, inparam_num BOOLEAN) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getBoolean(CNT,VALUE) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setBoolean;";
        String sql12 = "create or replace procedure proc_getBoolean(inparam int, ret out BOOLEAN) \n" +
                "is\n" +
                "begin\n" +
                "   select VALUE into ret from Test_getBoolean where cnt =inparam;\n" +
                "end proc_getBoolean;";
        String sql13 = "create or replace procedure proc_setByte(inparam_cnt int, inparam_num TINYINT) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getByte(CNT,VALUE) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setByte;";
        String sql14 = "create or replace procedure proc_getByte(inparam int, ret out TINYINT) \n" +
                "is\n" +
                "begin\n" +
                "   select VALUE into ret from Test_getByte where cnt =inparam;\n" +
                "end proc_getByte;";
        String sql15 = "create or replace procedure proc_setBytes(inparam_cnt int, inparam_num binary) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getBytes(CNT,VALUE) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setBytes;";
        String sql16 = "create or replace procedure proc_getBytes(inparam int, ret out binary) \n" +
                "is\n" +
                "begin\n" +
                "   select VALUE into ret from Test_getBytes where cnt =inparam;\n" +
                "end proc_getBytes;";
        String sql17 = "create or replace procedure proc_setClob(inparam_cnt int, inparam_num clob) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getClob(CNT,LB) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setClob;";
        String sql18 = "create or replace procedure proc_getClob(inparam int, ret out clob) \n" +
                "is\n" +
                "begin\n" +
                "   select lb into ret from Test_getClob where cnt =inparam;\n" +
                "end proc_getClob;";
        String sql19 = "create or replace procedure proc_setDate(inparam_cnt int, inparam_num date) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getDate(CNT,VALUE) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setDate;";
        String sql20 = "create or replace procedure proc_getDate(inparam int, ret out date) \n" +
                "is\n" +
                "begin\n" +
                "   select VALUE into ret from Test_getDate where cnt =inparam;\n" +
                "end proc_getDate;";
        String sql21 ="create or replace procedure proc_setDouble(inparam_cnt int, inparam_num double) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getDouble(CNT,NUM) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setDouble;";
        String sql22 ="create or replace procedure proc_getDouble(inparam int, ret out double) \n" +
                "is\n" +
                "begin\n" +
                "   select num into ret from Test_getDouble where cnt =inparam;\n" +
                "end proc_getDouble;";
        String sql23 ="create or replace procedure proc_setFloat(inparam_cnt int, inparam_num float) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getFloat(CNT,NUM) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setFloat;";
        String sql24 ="create or replace procedure proc_getFloat(inparam int, ret out float) \n" +
                "is\n" +
                "begin\n" +
                "   select num into ret from Test_getFloat where cnt =inparam;\n" +
                "end proc_getFloat;";
        String sql25 ="create or replace procedure proc_setInt(inparam_cnt int, inparam_num int) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getInt(CNT,NUM) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setInt;";
        String sql26 ="create or replace procedure proc_getInt(inparam int, ret out int) \n" +
                "is\n" +
                "begin\n" +
                "   select num into ret from Test_getInt where cnt =inparam;\n" +
                "end proc_getInt;";
        String sql27 ="create or replace procedure proc_setLong(inparam_cnt int, inparam_num bigint) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getLong(CNT,NUM) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setLong;";
        String sql28 ="create or replace procedure proc_getLong(inparam int, ret out bigint) \n" +
                "is\n" +
                "begin\n" +
                "   select num into ret from Test_getLong where cnt =inparam;\n" +
                "end proc_getLong;";
        String sql29 ="create or replace procedure proc_setNClob(inparam_cnt int, inparam_num clob) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getNClob(CNT,LB) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setNClob;";
        String sql30 ="create or replace procedure proc_getNClob(inparam int, ret out clob) \n" +
                "is\n" +
                "begin\n" +
                "   select lb into ret from Test_getNClob where cnt =inparam;\n" +
                "end proc_getNClob;";
        String sql31 ="create or replace procedure proc_setNString(inparam_cnt int, inparam_num varchar) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getNString(CNT,CHR) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setNString;";
        String sql32 ="create or replace procedure proc_getNString(inparam int, ret out varchar) \n" +
                "is\n" +
                "begin\n" +
                "   select CHR into ret from Test_getNString where cnt =inparam;\n" +
                "end proc_getNString;";
        String sql33 ="create or replace procedure proc_setRef(inparam_cnt int, inparam_num varchar) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getRef(CNT,CHR) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setRef;";
        String sql34 ="create or replace procedure proc_getRef(inparam int, ret out varchar) \n" +
                "is\n" +
                "begin\n" +
                "   select chr into ret from Test_getRef where cnt =inparam;\n" +
                "end proc_getRef;";
        String sql35 ="create or replace procedure proc_setSQLXML(inparam_cnt int, inparam_num varchar) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getSQLXML(CNT,CHR) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setSQLXML;";
        String sql36 ="create or replace procedure proc_getSQLXML(inparam int, ret out varchar) \n" +
                "is\n" +
                "begin\n" +
                "   select chr into ret from Test_getSQLXML where cnt =inparam;\n" +
                "end proc_getSQLXML;";
        String sql37 ="create or replace procedure proc_setShort(inparam_cnt int, inparam_num smallint) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getShort(CNT,NUM) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setShort;";
        String sql38 ="create or replace procedure proc_getShort(inparam int, ret out smallint) \n" +
                "is\n" +
                "begin\n" +
                "   select num into ret from Test_getShort where cnt =inparam;\n" +
                "end proc_getShort;";
        String sql39 ="create or replace procedure proc_setString(inparam_cnt int, inparam_num varchar) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getString(CNT,CHR) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setString;";
        String sql40 ="create or replace procedure proc_getString(inparam int, ret out varchar) \n" +
                "is\n" +
                "begin\n" +
                "   select CHR into ret from Test_getString where cnt =inparam;\n" +
                "end proc_getString;";
        String sql41 ="create or replace procedure proc_setTime(inparam_cnt int, inparam_num time) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getTime(CNT,VALUE) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setTime;";
        String sql42 ="create or replace procedure proc_getTime(inparam int, ret out time) \n" +
                "is\n" +
                "begin\n" +
                "   select VALUE into ret from Test_getTime where cnt =inparam;\n" +
                "end proc_getTime;";
        String sql43 ="create or replace procedure proc_setTimestamp(inparam_cnt int, inparam_num timestamp) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getTimestamp(CNT,VALUE) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setTimestamp;";
        String sql44 ="create or replace procedure proc_getTimestamp(inparam int, ret out timestamp) \n" +
                "is\n" +
                "begin\n" +
                "   select VALUE into ret from Test_getTimestamp where cnt =inparam;\n" +
                "end proc_getTimestamp;";
        String sql45 ="create or replace procedure proc_setURL(inparam_cnt int, inparam_num varchar) \n" +
                "is \n" +
                "begin \n" +
                "  insert into Test_getURL(CNT,CHR) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end proc_setURL;";
        String sql46 ="create or replace procedure proc_getURL(inparam int, ret out varchar) \n" +
                "is\n" +
                "begin\n" +
                "   select chr into ret from Test_getURL where cnt =inparam;\n" +
                "end proc_getURL;";
        String sql47 ="-- create procedure --\n" +
                "create or replace procedure sysauditor.prc_test1 ()\n" +
                " is\n" +
                " begin \n" +
                "for i in 1..10 loop\n" +
                " insert into test_t(id,dtime) values(myseq1.nextval(),current_time);\n" +
                " end loop;\n" +
                " insert into test_t(id,dtime,info) values(default,current_time,'过 程执行完成 '||to_char( myseq2.nextval())||' 次'); \n" +
                "commit;\n" +
                " end;";
        String sql48 ="create or replace procedure sysauditor.proc_setfloat (inparam_cnt int, inparam_num float) \n" +
                "is \n" +
                "begin \n" +
                "  insert into test_getfloat(cnt,num) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end sysauditor.proc_setfloat ;";
        String sql49 ="create or replace procedure sysauditor.proc_setint (inparam_cnt int, inparam_num int) \n" +
                "is \n" +
                "begin \n" +
                "  insert into test_getint(cnt,num) values (inparam_cnt,inparam_num); \n" +
                "  commit;\n" +
                "end sysauditor.proc_setint ;";

        String sqlIntervalYear = "create or replace procedure test_proc3(ss in interval year(3))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalMonth = "create or replace procedure test_proc3(ss in interval month(5))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalDay = "create or replace procedure test_proc3(ss in interval day(4))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalHour = "create or replace procedure test_proc3(ss in interval hour(6))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalMinute = "create or replace procedure test_proc3(ss in interval minute(5))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalSecond = "create or replace procedure test_proc3(ss in interval second(3,7))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalYearToMonth = "create or replace procedure test_proc3(ss in interval year(5) to month)\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalDayToHour = "create or replace procedure test_proc3(ss in interval day(5) to hour)\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalDayToMinute = "create or replace procedure test_proc3(ss in interval day(6) to minute)\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalDayToSecond = "create or replace procedure test_proc3(ss in interval day(6) to second(6))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalHourToMinute = "create or replace procedure test_proc3(ss in interval hour(4) to minute)\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalHourToSecond = "create or replace procedure test_proc3(ss in interval hour(5) to second(3))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalMinuteToSecond = "create or replace procedure test_proc3(ss in interval minute(6) to second(3))\n" +
                "as\n" +
                "begin\n" +
                " select * from sys_tables ;\n" +
                " delete from sysdba.t3 where sysdba.t3.id=1;"+
                "end;";

        String normal = "create or replace procedure test_proc3(ss in interval minute(6) to second(3),id int,name varchar(20),money numeric(10,5),address char(10))\n" +
                "as\n" +
                " begin\n" +
                " insert into sysdba.t1(id,name) values(1,'roger');"+
                " select * from sysdba.sys_tables ;\n" +
                " update sysdba.t1 set id=1 where name='roger1';"+
                " delete from sysdba.t3 where sysdba.t3.id=1;"+
                " end;";
        String normal2 =  "create or replace procedure test_proc3(id int,name out varchar,address inout int)\n" +
                "as\n" +
                " begin\n" +
                " insert into sysdba.t1(id,name) values(1,'roger');"+
                " select * from sysdba.sys_tables ;\n" +
                " update sysdba.t1 set id=1 where name='roger1';"+
                " delete from sysdba.t3 where sysdba.t3.id=1;"+
                " end;";
        String normal3 = "create or replace procedure uu.test_proc3(id func_dep_base_type,name syno_base_type,address syno_base_type1)\n" +
                "as\n" +
                " begin\n" +
                " insert into uu.t1(id,name) values(1,'roger');"+
                " select * from uu.sys_tables ;\n" +
                " update uu.t1 set id=1 where name='roger1';"+
                " delete from uu.t3 where uu.t3.id=1;"+
                " end;";
        String execImmediate = "create or replace procedure pro_immediate(id int,name varchar,a in int, b in int) as \n" +
                "begin\n" +
                "select t1.* from test_1 t1 inner join test_2 t2 inner join test_3 t3 on t1.id=t2.id and t2.id=t3.id;\n" +
                "select t1.* from test_1;\n" +
                "update test_1 set id=1 where name='roger';\n" +
                "insert into test_1(id,name)values(1,'roger');\n" +
                "delete from test_1 where id=1;\n" +
                "execute dep_base_proc_1(a,b);\n" +
                "call dep_base_proc_1(a,b);\n" +
                "execute immediate 'create schema sc';\n" +
                "execute immediate 'create table sc.tb1(id int,name varhcar)';\n" +
                "execute immediate 'create table sc.students(id int,name varhcar)';\n" +
                "execute immediate 'create index sc.idx1 on sc.tb1(id)';\n" +
                "execute immediate 'create view sc.view1 as select * from sc.tb1';\n" +
                "execute immediate 'create synonym sc.stu for sc.students';\n" +
                "execute immediate 'truncate table sc.tb1';\n" +
                "execute immediate 'drop table sc.tb1';\n" +
                "end;";
       /* builder.append(sql);
        builder.append(sql1);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);

        builder.append(sql5);
        builder.append(sql6);
        builder.append(sql7);
        builder.append(sql8);
        builder.append(sql9);
        builder.append(sql11);
        builder.append(sql12);
        builder.append(sql13);
        builder.append(sql14);
        builder.append(sql15);
        builder.append(sql16);
        builder.append(sql17);
        builder.append(sql18);
        builder.append(sql19);
        builder.append(sql20);
        builder.append(sql21);
        builder.append(sql22);
        builder.append(sql23);
        builder.append(sql24);
        builder.append(sql25);
        builder.append(sql26);
        builder.append(sql27);
        builder.append(sql28);
        builder.append(sql29);
        builder.append(sql30);

        builder.append(sqlIntervalYear);
        builder.append(sqlIntervalMonth);
        builder.append(sqlIntervalDay);
        builder.append(sqlIntervalHour);
        builder.append(sqlIntervalMinute);
        builder.append(sqlIntervalSecond);
        builder.append(sqlIntervalYearToMonth);
        builder.append(sqlIntervalDayToHour);
        builder.append(sqlIntervalDayToMinute);
        builder.append(sqlIntervalDayToSecond);
        builder.append(sqlIntervalHourToMinute);
        builder.append(sqlIntervalHourToSecond);
        builder.append(sqlIntervalMinuteToSecond);
        builder.append(normal);*/
        builder.append(normal2);
        builder.append(normal3);
        builder.append(execImmediate);

        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();

        List<SQLStatement> createProcedureSqlStatementList = new ArrayList<>();
        for(SQLStatement statement:statementList){
            if (statement instanceof SQLCreateProcedureStatement){
                //SQLCreateProcedureStatement createProcedureStatement = (SQLCreateProcedureStatement) statement;
                createProcedureSqlStatementList.add(statement);
            }
        }
        List<CreateProcedureBean> procedureBeans = XuguParserApi.parseCreateProcedure(execImmediate);
        print(createProcedureSqlStatementList);
        Map<String,String> map = new CaseInsensitiveMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","uu1");
        String renturnStr = XuguParserApi.replaceProcedureSqlSchema(execImmediate,map,"sysdba");
        System.out.printf("222");
    }
}
