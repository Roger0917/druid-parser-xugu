package com.alibaba.druid.xugu.function;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateFunctionStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class FunctionTest2 extends TestCase {
    
    public void test(){
        StringBuilder builder = new StringBuilder();
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
                "end;";
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
        String sql32 = "create function dep_view_func(b in varchar) return int as\n" +
                " begin\n" +
                "  for i in 1..5 loop\n" +
                "   insert into dep_base_view_1 values(i,b||i);\n" +
                "  end loop;\n" +
                " end;";
        String sql33 = "create function dep_view_func(b in varchar) return int as\n" +
                " begin\n" +
                "  for i in reverse 5..1 loop\n" +
                "   insert into dep_base_view_1 values(i,b||i);\n" +
                "  end loop;\n" +
                " end;";
        String sql34 = "create or replace function fun_test(A interval day(5) to second(3)) return interval day(5) to second(3)\n" +
                "as\n" +
                "begin\n" +
                "insert into test_1(id,name)values(1,'roger');\n" +
                "end;";
        String sql35 = "create or replace function sysdba.dep_proc_func(a in int, b in int) return int as\n" +
                " begin\n" +
                "   execute sysdba.dep_base_proc_1(a,b);\n" +
                "   call sysdba.dep_base_proc_1(a,b);\n" +
                " end;";
      /*  builder.append(sql1);
        builder.append(sql2);
        builder.append(sql3);
        builder.append(sql4);
        builder.append(sql5);

        builder.append(sql6);
        builder.append(sql7);
        builder.append(sql8);
        builder.append(sql9);
        builder.append(sql10);

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
        builder.append(sql31);
        */
        /*builder.append(sql32);
        builder.append(sql33);
        builder.append(sql34);*/
        builder.append(sql35);


        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLCreateFunctionStatement> createFunctionStatementList = new ArrayList<>();
        List<SQLStatement> statementList = parser.parseStatementList();
        for(SQLStatement statement:statementList){
            if (statement instanceof SQLCreateFunctionStatement){
                SQLCreateFunctionStatement createFunctionStatement = (SQLCreateFunctionStatement) statement;
                createFunctionStatementList.add(createFunctionStatement);
            }
        }
        List<CreateFunctionBean> createFunctionBeanList = XuguParserApi.parseCreateFunction(sql34);
        try{
            String str = createFunctionStatementList.get(0).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        
        System.out.printf("222");
    }
    
}
