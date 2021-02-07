package com.alibaba.druid.xugu.schema;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class GetSchemaFromType extends TestCase {
    
    public void test(){
        String sql = "create type body pack_dep_base_type as" +
                "            member function getId(n int,na varchar(21)) return int" +
                "            as" +
                "            begin" +
                " insert into uu.test_1(id,name)values(1,'roger');\n" +
        "                update uu.test_1 set uu.test_1.id=2,uu.test_1.name='name' where uu.test_1.id=1;\n" +
                "\t       delete from uu.test_1 where uu.test_1.id=1;\n" +
                "             return id;" +
                "            end;" +
                "            member function getAddr(n int,na varchar) return varchar" +
                "            as" +
                "            begin" +
                " insert into uu.test_1(id,name)values(1,'roger');\n" +
        "                update uu.test_1 set uu.test_1.id=2,uu.test_1.name='name' where uu.test_1.id=1;\n" +
                "\t       delete from uu.test_1 where uu.test_1.id=1;\n" +
                "             return addr;" +
                "            end;" +
                "static PROCEDURE printfa(p person)" +
                " IS"+
                " BEGIN "+
                " insert into uu.test_1(id,name)values(1,'roger');\n" +
                "                update uu.test_1 set uu.test_1.id=2,uu.test_1.name='name' where uu.test_1.id=1;\n" +
                "\t       delete from uu.test_1 where uu.test_1.id=1;\n" +
                " send_msg('id='||p.id || ' name='||p.get_name || ' addr=' || p.get_adr());"+
                " END;"+
                "            end;";
        String sql10="create type syno_base_type as object" +
                "(" +
                "  name varchar," +
                "  addr varchar," +
                "  member function getName(n int,na numeric(21,6)) return varchar," +
                "  member function getAddr(n int,na varchar) return varchar"+
                ");";
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
        XuguStatementParser parser = new XuguStatementParser(sql10+sql11);
        List<SQLStatement> statementList = parser.parseStatementList();
        HashMap<String,String> map = new HashMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","uu1");
        map.put("ss","ss1");

        String returnStr= XuguParserApi.replaceTypeSqlSchema(sql10+sql11,map,"ss");
        System.out.println(222);
    }
}
