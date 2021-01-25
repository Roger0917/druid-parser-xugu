package com.alibaba.druid.xugu.type;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.List;

public class TypeTest2 extends TestCase {
    
    public void test(){
        StringBuilder builder = new StringBuilder();
        String sql1="create type ordi_type as " +
                "object(a int, b int);";
        String sql2="create type schema_type.oth_sche_type_1 as " +
                "object(a int, b varchar(22));";
        String sql3="create type varray_type is varray(100) of varr_type;";
        String sql4="create type table_type is table of tab_type1;";
        String sql5="create type proc_dep_base_type as object(" +
                "id int," +
                "name varchar," +
                "member function getId() return int," +
                "member function getName() return varchar" +
                ");";
        String sql6="create type func_dep_base_type as object" +
                "(" +
                " id int," +
                " age int," +
                " member function getId(n int,na varchar) return int," +
                " member function getAge(n int,na varchar ) return int" +
                ")";

       String sql7= "create type body func_dep_base_type as" +
                "            member function getId(n int,na varchar) return int" +
                "            as" +
                "            begin" +
                "             return id;" +
                "            end;" +
                "            member function getAge(n int,na varchar) return int" +
                "            as" +
                "            begin" +
                "             return age;" +
                "            end;" +
                "            end;";
        String sql8="create type pack_dep_base_type as object" +
                "(" +
                " id int," +
                " addr varchar," +
                " member function getId(n int,na varchar(21)) return int," +
                " member function getAddr(n int,na varchar) return varchar," +
                " static PROCEDURE printfa(p person)"+
                ")";
        String sql9 = "create type body pack_dep_base_type as" +
                "            member function getId(n int,na varchar(21)) return int" +
                "            as" +
                "            begin" +
                "             return id;" +
                "            end;" +
                "            member function getAddr(n int,na varchar) return varchar" +
                "            as" +
                "            begin" +
                "             return addr;" +
                "            end;" +
                "static PROCEDURE printfa(p person)" +
                " IS"+
                " BEGIN "+
                " send_msg('id='||p.id || ' name='||p.get_name || ' addr=' || p.get_adr());"+
                " END;"+
                "            end;";
        String sql10="create type syno_base_type as object" +
                "(" +
                "  name varchar," +
                "  addr varchar," +
                "  member function getName(n int,na numeric(21,6)) return varchar," +
                "  member function getAddr(n int,na varchar) return varchar"+
                ")";
        String sql11=
                "create type body syno_base_type as" +
                " member function getName(n int,na numeric(21,6)) return varchar" +
                " as" +
                " begin " +
                " return name;" +
                " end;" +
                " member function getAddr(n int,na varchar) return varchar" +
                " as" +
                " begin" +
                " return addr;" +
                "end;" +
                "end;";
        String sql12="create type syno_base_type as object" +
                "(" +
                "  name varchar," +
                "  addr varchar," +
                "  member function getName() return varchar," +
                "  member function getAddr() return varchar" +
                ")" +"create type body syno_base_type as" +
                " member function getName() return varchar" +
                " as" +
                " begin " +
                " return name;" +
                "end;" +
                " member function getAddr() return varchar" +
                " as" +
                " begin" +
                " return addr;" +
                " end;" +
                " end;" ;
        String sql13=" create type acl_ch_type as object" +
                "  (" +
                "  id int," +
                "  name varchar," +
                "  member function getId() return int," +
                "  member function getName() return varchar" +
                "  )" +"create type body acl_ch_type as" +
                "  member function getId() return int" +
                "  as" +
                "  begin" +
                "   return id;" +
                "  end;" +
                "  member function getName() return varchar" +
                "  as" +
                "  begin" +
                "   return name;" +
                "  end;" +
                "  end;";

        String sql14 = "CREATE OR REPLACE TYPE person1 UNDER person ( wife CHAR(10), child CHAR(10), member PROCEDURE set_wIFe(name CHAR(10)), member PROCEDURE set_child(name CHAR(10)), member PROCEDURE printf1(),static PROCEDURE printfa(p person));";
        String sql15 = "CREATE OR REPLACE TYPE BODY person1 AS member PROCEDURE set_wife (_name CHAR(10)) IS BEGIN wife:=_name; END;member PROCEDURE set_child(_name CHAR(10)) IS BEGIN child:=_name; END;member PROCEDURE printf1() IS BEGIN send_msg('id='||id || ' name='|| get_name || ' addr=' || get_adr() || ' wife='||wife || ' child='||child); " +
                " END;"+
                "END;";
        String sql16 = "DECLARE TYPE typea IS VARYING ARRAY(20) OF INTEGER; ta typea; BEGIN ta.extend; SELECT id BULK COLLECT INTO ta FROM test_1; FOR i IN ta.first..ta.last LOOP INSERT INTO test_2 VALUES (ta(i),'test'||i); END LOOP; END;";
        builder.append(sql1);
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
        //builder.append(sql16);

        XuguStatementParser parser = new XuguStatementParser(builder.toString());
        List<SQLStatement> statementList = parser.parseStatementList();
        System.out.println(222);
    }

}
