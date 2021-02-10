package com.alibaba.druid.sql.dialect.xugu.api;

import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateCallBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import com.alibaba.druid.sql.dialect.xugu.api.exception.ParserBusinessException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class XuguParserApiTest {

    @Test
    public void parseCreateProcedure() {
        StringBuilder builder = new StringBuilder();
        String sqlIntervalHourToSecond = "create or replace procedure guest.test_proc3(ss in interval hour(5) to second(3))\n" +
                "as\n" +
                "begin\n" +
                "select * from sys_tables ;\n" +
                "end;";

        String sqlIntervalMinuteToSecond = "create or replace procedure syssso.test_proc3(ss in interval minute(6) to second(3))\n" +
                "as\n" +
                "begin\n" +
                " select * from sys_tables ;\n" +
                " delete from sysdba.t3 where sysdba.t3.id=1;"+
                "end;";

        String normal = "create or replace procedure sysdba.test_proc3(ss in interval minute(6) to second(3),xx out interval day(7) to second(3),xx1 out interval hour(7) to second(4),yy in out interval second(9,3),id int,name varchar(20),money numeric(10,5),address char(10))\n" +
                "as\n" +
                " begin\n" +
                " insert into sysdba.t1(id,name) values(1,'roger');"+
                " select * from sysdba.sys_tables ;\n" +
                " update sysdba.t1 set id=1 where name='roger1';"+
                " delete from sysdba.t3 where sysdba.t3.id=1;"+
                " end;";

        String normal2 = "create or replace procedure uu.proc1 is"+
        " begin "+
        " send_msg(6);"+
        " end";

    /*    builder.append(sqlIntervalHourToSecond);
        builder.append(sqlIntervalMinuteToSecond);*/
        builder.append(normal);
        builder.append(normal2);
        try {
            List<CreateProcedureBean> createProcedureBeans = XuguParserApi.parseCreateProcedure(builder.toString());
        } catch (ParserBusinessException e) {
            e.printStackTrace();
        }
        System.out.printf("222");

    }

    @Test
    public void parseCreateFunction() {
        StringBuilder builder = new StringBuilder();
        String sql24 = "create or replace function FUNC_GETTIMESTAMP (inparam int) return timestamp\n" +
                "is\n" +
                "   v_ret timestamp;\n" +
                "begin\n" +
                "   select VALUE into v_ret from Test_getTimestamp where cnt =inparam ;\n" +
                "   return v_ret;\n" +
                "end func_getTimestamp;";
        String sql25 = "create or replace function FUNC_GETURL (inparam int) return varchar\n" +
                "is\n" +
                "   v_ret varchar;\n" +
                "begin\n" +
                "   select chr into v_ret from Test_getURL where cnt =inparam ;\n" +
                "   return v_ret;\n" +
                "end func_getURL;";
        String sql26 = "create or replace function INSERT_EXISTS_TRANS (t1 bigint,t2 int,t3 datetime,t4 varchar ) RETURN INTEGER AS\n" +
                "DECLARE\n" +
                "t_cou INT;\n" +
                "BEGIN\n" +
                "SELECT COUNT(*) INTO t_cou FROM cm_sql_cost WHERE tid=t1 and cluster_id = t4;\n" +
                "IF t_cou>=1 THEN\n" +
                "UPDATE cm_sql_cost SET trans_cost=t2 WHERE tid=t1 and cluster_id = t4;\n" +
                "END IF;\n" +
                "IF t_cou=0 THEN\n" +
                "INSERT INTO cm_sql_cost VALUES(t1,t2,t3,t4);\n" +
                "END IF;\n" +
                "END;";
        String sql27 = "create or replace function MIGRATE_FUNC (a in int,b in int) return int as\n" +
                "res int;\n" +
                "begin\n" +
                "res:=a+b;\n" +
                "send_msg(res);\n" +
                "return res;\n" +
                "end;";
        String sql28 = "create or replace function SYN_BASE_FUNC (a in int default 0,b in interval year to month,c numeric(5,3),d varchar(20),e char(10)) return int as\n" +
                "res int;\n" +
                "begin\n" +
                "res:=a*(-1);\n" +
                "send_msg(res);\n" +
                "end;";
        builder.append(sql24);
        builder.append(sql25);
        builder.append(sql26);
        builder.append(sql27);
        builder.append(sql28);
        try {
            List<CreateFunctionBean> createFunctionBeans = XuguParserApi.parseCreateFunction(builder.toString());
        } catch (ParserBusinessException e) {
            e.printStackTrace();
        }
        System.out.println(222);
    }

    @Test
    public void parseCreatePackage() {
    }

    @Test
    public void parseCreateTrigger() {
    }

    @Test
    public void parseCreateType() {
    }

    @Test
    public void parseCall() {
        StringBuilder builder = new StringBuilder();
        String sql = "call xugu_test_pro(id => 5,name=>'ggg',address => 'vvv');";
        String sql2 = "call xugu_test_pro(:1,:2)";
        String sql3 = "call xugu_test_pro(:id,:name)";
        String sql4 = "call xugu_test_return_fun(id => 5,name=>'ggg',address => 'vvv');";
        builder.append(sql);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        try {
            List<CreateCallBean> callBeanList = XuguParserApi.parseCall(builder.toString());
        } catch (ParserBusinessException e) {
            e.printStackTrace();
        }
        System.out.println(222);
    }

    @Test
    public void parseBlockProAndFun() {
    }

    @Test
    public void parseCreateView() {
    }
}