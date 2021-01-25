/*
package com.alibaba.druid.xugu.procedure;

import static org.junit.Assert.assertTrue;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import org.junit.BeforeClass;
import org.junit.Test;

*/
/**
 * Unit test for simple App.
 *//*

public class ProcedureTest
{
    */
/**
     * Rigorous Test :-)
     *//*


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
    String sql7 = "create or replace procedure test_proc3(ss in interval second)\n" +
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

static XuguParserApi2 xuguParserApi2;
@BeforeClass
public  static void afterClass(){
     xuguParserApi2 = new XuguParserApi2();
}




    @Test
    public void testProcedure() {
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }

    @Test
    public void testProcedure1() {
            CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql);
            if (createProcedureBean != null) {
                for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                    String paraName = createProcedureBean.getParams().get(i).get(0);
                    String paraType = createProcedureBean.getParams().get(i).get(2);
                    String dataType = createProcedureBean.getParams().get(i).get(1);
                    Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                    String paraDefault = createProcedureBean.getParams().get(i).get(4);
                    //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                    System.out.println(paraName);
                    System.out.println(paraType);
                    System.out.println(dataType);
                    System.out.println(paraPosition);
                    System.out.println(paraDefault);
                }
            }
        }
    @Test
    public void testProcedure2() {
                CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql2);
                if (createProcedureBean != null) {
                    for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                        String paraName = createProcedureBean.getParams().get(i).get(0);
                        String paraType = createProcedureBean.getParams().get(i).get(2);
                        String dataType = createProcedureBean.getParams().get(i).get(1);
                        Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                        String paraDefault = createProcedureBean.getParams().get(i).get(4);
                        //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                        System.out.println(paraName);
                        System.out.println(paraType);
                        System.out.println(dataType);
                        System.out.println(paraPosition);
                        System.out.println(paraDefault);
                    }
                }
            }
    @Test
    public void testProcedure3() {
                    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql3);
                    if (createProcedureBean != null) {
                        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                            String paraName = createProcedureBean.getParams().get(i).get(0);
                            String paraType = createProcedureBean.getParams().get(i).get(2);
                            String dataType = createProcedureBean.getParams().get(i).get(1);
                            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                            String paraDefault = createProcedureBean.getParams().get(i).get(4);
                            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                            System.out.println(paraName);
                            System.out.println(paraType);
                            System.out.println(dataType);
                            System.out.println(paraPosition);
                            System.out.println(paraDefault);
                        }
                    }
                }
    @Test
    public void testProcedure4(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql4);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }

    @Test
    public void testProcedure5(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql5);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure6(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql6);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure7(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql7);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure8(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql8);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure9(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql9);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure10(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql10);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure11(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql11);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure12(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql12);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure13(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql13);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure14(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql14);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure15(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql15);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure16(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql16);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure17(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql17);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure18(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql18);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure19(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql19);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure20(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql20);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure21(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql21);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure22(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql22);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure23(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql23);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure24(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql24);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure25(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql25);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure26(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql26);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure27(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql27);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure28(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql28);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }    @Test
public void testProcedure29(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql29);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}
    @Test
    public void testProcedure30(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql30);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }    @Test
public void testProcedure31(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql31);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure32(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql32);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}
    @Test
    public void testProcedure33(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql33);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }    @Test
public void testProcedure34(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql34);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure35(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql35);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}
    @Test
    public void testProcedure36(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql36);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure37(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql37);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }    @Test
public void testProcedure38(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql38);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure39(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql39);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure40(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql40);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure41(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql41);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure42(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql42);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}    @Test
public void testProcedure43(){
    CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql43);
    if (createProcedureBean != null) {
        for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
            String paraName = createProcedureBean.getParams().get(i).get(0);
            String paraType = createProcedureBean.getParams().get(i).get(2);
            String dataType = createProcedureBean.getParams().get(i).get(1);
            Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
            String paraDefault = createProcedureBean.getParams().get(i).get(4);
            //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
            System.out.println(paraName);
            System.out.println(paraType);
            System.out.println(dataType);
            System.out.println(paraPosition);
            System.out.println(paraDefault);
        }
    }
}
    @Test
    public void testProcedure44(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql44);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure45(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql45);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure46(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql46);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure47(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql47);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }

    @Test
    public void testProcedure48(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql48);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }
    @Test
    public void testProcedure49(){
        CreateProcedureBean createProcedureBean = xuguParserApi2.parseCreateProcedure(sql49);
        if (createProcedureBean != null) {
            for (int i = 0; i < createProcedureBean.getParamSize(); i++) {
                String paraName = createProcedureBean.getParams().get(i).get(0);
                String paraType = createProcedureBean.getParams().get(i).get(2);
                String dataType = createProcedureBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createProcedureBean.getParams().get(i).get(3));
                String paraDefault = createProcedureBean.getParams().get(i).get(4);
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }
    }















}
*/
