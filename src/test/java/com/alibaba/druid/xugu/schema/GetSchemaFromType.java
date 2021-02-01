package com.alibaba.druid.xugu.schema;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class GetSchemaFromType extends TestCase {
    
    public void test(){
        String sql = "create type body sysdba.pack_dep_base_type as" +
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
        XuguStatementParser parser = new XuguStatementParser(sql);
        List<SQLStatement> statementList = parser.parseStatementList();
        HashMap<String,String> map = new HashMap<>();
        map.put("sysdba","sysdba1");
        map.put("uu","uu1");

        String returnStr= XuguParserApi.replaceTypeSqlSchema(sql,map);
        System.out.println(222);
    }
}
