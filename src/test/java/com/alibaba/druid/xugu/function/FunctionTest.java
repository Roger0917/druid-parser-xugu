/*
package com.alibaba.druid.xugu.function;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import org.junit.Test;

import java.util.List;

*/
/**
 * @Author: Gosin
 * @Date: 2021/1/6 9:28
 *//*

public class FunctionTest {

    @Test
    public void testFunction() {
        String sql = "create or replace function func_getSQLXML() return varchar\n" +
                "as\n" +
                "   v_ret varchar;\n" +
                "begin\n" +
                "   select chr into v_ret from Test_getSQLXML where cnt =inparam ;\n" +
                "   return v_ret;\n" +
                "end func_getSQLXML;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        List<CreateFunctionBean> createFunctionBeanList = xuguParserApi.parseCreateFunction(sql);

if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {
                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);
            }
        }

    }


    String sql1="create function \"abc123ABC啊~!@#$%^&*(\"  (a in out int) return int as\n" +
            "begin\n" +
            "a:=a*(-1);\n" +
            "send_msg(a);\n" +
            "end;";
    String sql2="create function FUNC4 (id1 int)\n" +
            "return datetime as\n" +
            "curr_time datetime;\n" +
            "begin\n" +
            "select test_time into curr_time  from testDate where id = id1;\n" +
            "return curr_time;\n" +
            "end;";
    String sql3="create function FUNC5 (name1 varchar)\n" +
            "return varchar as\n" +
            "name2 varchar;\n" +
            "begin\n" +
            "select password into name2   from Usert where name = name1;\n" +
            "return name2;\n" +
            "end";
    String sql4="create function FUNCTION_TEST (student_id IN INTEGER)\n" +
            " RETURN DOUBLE AS count_num DOUBLE;\n" +
            " BEGIN\n" +
            " SELECT english+math+chinese INTO count_num FROM student WHERE id= student_id;\n" +
            " RETURN count_num;\n" +
            " END;";
    String sql5="create function FUNCTION_TEST1 (student_id IN INTEGER)\n" +
            " RETURN DOUBLE AS count_num DOUBLE;\n" +
            " BEGIN\n" +
            " SELECT english+math+chinese INTO count_num FROM student WHERE id= student_id;\n" +
            " RETURN count_num;\n" +
            " END;";
    String sql6="create function FUNC_GETBLOB (inparam int) return blob\n" +
            "is\n" +
            "   v_ret BLOB;\n" +
            "begin\n" +
            "   select lb into v_ret from Test_getBlob where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getBlob;";
    String sql7="create function FUNC_GETBOOLEAN (inparam int) return BOOLEAN\n" +
            "is\n" +
            "   v_ret boolean;\n" +
            "begin\n" +
            "   select VALUE into v_ret from Test_getBoolean where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getBoolean;";
    String sql8="create function FUNC_GETBYTE (inparam int) return TINYINT\n" +
            "is\n" +
            "   v_ret tinyint;\n" +
            "begin\n" +
            "   select VALUE into v_ret from Test_getByte where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getByte;";
    String sql9="create function FUNC_GETBYTE (inparam int) return TINYINT\n" +
            "is\n" +
            "   v_ret tinyint;\n" +
            "begin\n" +
            "   select VALUE into v_ret from Test_getByte where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getByte;";
    String sql10="create function FUNC_GETBYTES (inparam int) return binary\n" +
            "is\n" +
            "   v_ret binary;\n" +
            "begin\n" +
            "   select VALUE into v_ret from Test_getBytes where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getBytes;";
    String sql11="create function FUNC_GETCLOB (inparam int) return clob\n" +
            "is\n" +
            "   v_ret CLOB;\n" +
            "begin\n" +
            "   select lb into v_ret from Test_getClob where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getClob;";
    String sql12="create function FUNC_GETDATE (inparam int) return date\n" +
            "is\n" +
            "   v_ret date;\n" +
            "begin\n" +
            "   select VALUE into v_ret from Test_getDate where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getDate;";
    String sql13="create or replace function FUNC_GETDOUBLE (inparam int) return double\n" +
            "is\n" +
            "   v_ret double;\n" +
            "begin\n" +
            "   select num into v_ret from Test_getDouble where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getDouble; ";
    String sql14="create or replace function FUNC_GETFLOAT (inparam int) return float\n" +
            "is\n" +
            "   v_ret float;\n" +
            "begin\n" +
            "   select num into v_ret from Test_getFloat where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getFloat;";
    String sql15="create or replace function FUNC_GETINT (inparam int) return int\n" +
            "is\n" +
            "   v_ret int;\n" +
            "begin\n" +
            "   select num into v_ret from Test_getInt where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getInt;";
    String sql16="create or replace function FUNC_GETLONG (inparam int) return bigint\n" +
            "is\n" +
            "   v_ret bigint;\n" +
            "begin\n" +
            "   select num into v_ret from Test_getLong where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getLong;";
    String sql17="create or replace function FUNC_GETNCLOB (inparam int) return clob\n" +
            "is\n" +
            "   v_ret CLOB;\n" +
            "begin\n" +
            "   select lb into v_ret from Test_getNClob where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getNClob;";
    String sql18="create or replace function FUNC_GETNSTRING (inparam int) return varchar\n" +
            "is\n" +
            "   v_ret varchar;\n" +
            "begin\n" +
            "   select CHR into v_ret from Test_getNString where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getNString;";
    String sql19="create or replace function FUNC_GETREF (inparam int) return varchar\n" +
            "is\n" +
            "   v_ret varchar;\n" +
            "begin\n" +
            "   select chr into v_ret from Test_getRef where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getRef;";
    String sql20="create or replace function FUNC_GETSHORT (inparam int) return smallint\n" +
            "is\n" +
            "   v_ret bigint;\n" +
            "begin\n" +
            "   select num into v_ret from Test_getShort where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getShort;";
    String sql21="create or replace function FUNC_GETSQLXML (inparam int) return varchar\n" +
            "is\n" +
            "   v_ret varchar;\n" +
            "begin\n" +
            "   select chr into v_ret from Test_getSQLXML where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getSQLXML;";
    String sql22="create or replace function FUNC_GETSTRING (inparam int) return varchar\n" +
            "is\n" +
            "   v_ret varchar;\n" +
            "begin\n" +
            "   select CHR into v_ret from Test_getString where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getString;";
    String sql23="create or replace function FUNC_GETTIME (inparam int) return time\n" +
            "is\n" +
            "   v_ret time;\n" +
            "begin\n" +
            "   select VALUE into v_ret from Test_getTime where cnt =inparam ;\n" +
            "   return v_ret;\n" +
            "end func_getTime;";

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
    String sql28 = "create or replace function SYN_BASE_FUNC (a in int) return int as\n" +
            "res int;\n" +
            "begin\n" +
            "res:=a*(-1);\n" +
            "send_msg(res);\n" +
            "end;";
    String sql29 = "create or replace function TEST_F (name1 varchar)\n" +
            "return varchar is\n" +
            "name2 varchar;\n" +
            "begin\n" +
            "name2:=name1;\n" +
            "return  name2;\n" +
            "end;";

    String sql30 = "create or replace function TEST_F (name1 varchar(20))\n" +
            "return varchar is\n" +
            "name2 varchar;\n" +
            "begin\n" +
            "name2:=name1;\n" +
            "return  name2;\n" +
            "end;";

    String sql31 = "create or replace function TEST_F (name1 numeric(13,2))\n" +
            "return varchar is\n" +
            "name2 varchar;\n" +
            "begin\n" +
            "name2:=name1;\n" +
            "return  name2;\n" +
            "end;";




    @Test
    public void testFunction1(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql1);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction2(){
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreateFunctionBean createFunctionBean = xuguParserApi.parseCreateFunction(sql2);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction3(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql3);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction4(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql4);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction5(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql5);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction6(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql6);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction7(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql7);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction8(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql8);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction9(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql9);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
                //monitor,procedure实例，参数名，数据类型，参数模式，参数位置，默认值。
                System.out.println(paraName);
                System.out.println(paraType);
                System.out.println(dataType);
                System.out.println(paraPosition);
                System.out.println(paraDefault);

              
            }
        }
    }    @Test
    public void testFunction10(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql10);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction11(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql11);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction12(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql12);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction13(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql13);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction14(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql14);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction15(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql15);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction16(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql16);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction17(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql17);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction18(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql18);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction19(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql19);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction20(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql20);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction21(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql21);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction22(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql22);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction23(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql23);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction24(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql24);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction25(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql25);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction26(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql26);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction27(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql27);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction28(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql28);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction29(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql29);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction30(){
        XuguParserApi2 xuguPaserApi = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguPaserApi.parseCreateFunction(sql30);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
    public void testFunction31(){
        XuguParserApi2 xuguParserApi2 = new XuguParserApi2();
        CreateFunctionBean createFunctionBean = xuguParserApi2.parseCreateFunction(sql31);
        if (createFunctionBean != null) {
            for (int i = 0; i < createFunctionBean.getParamSize(); i++) {

                String paraName = createFunctionBean.getParams().get(i).get(0);
                String paraType = createFunctionBean.getParams().get(i).get(2);
                String dataType = createFunctionBean.getParams().get(i).get(1);
                Integer paraPosition = Integer.valueOf(createFunctionBean.getParams().get(i).get(3));
                String paraDefault = createFunctionBean.getParams().get(i).get(4);
                String returnType = createFunctionBean.getReturnType();
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
