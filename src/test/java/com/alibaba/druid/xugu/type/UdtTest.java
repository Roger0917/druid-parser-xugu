package com.alibaba.druid.xugu.type;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateTypeBean;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

/**
 * @Author: Gosin
 * @Date: 2021/1/6 15:45
 */
public class UdtTest {


    String sql1="create type ordi_type as " +
            "object(a int, b int);";
    String sql2="create type schema_type.oth_sche_type_1 as " +
            "object(a int, b varchar(22));";
    String sql3="create type varr_type as object()" ;
    String sql4="create type varray_type is varray(100) of varr_type;";
    String sql5="create type table_type is table of tab_type1;";
    String sql6="create type proc_dep_base_type as object(" +
            "id int," +
            "name varchar," +
            "member function getId() return int," +
            "member function getName() return varchar" +
            ");";
    String sql7="create type func_dep_base_type as object" +
            "(" +
            " id int," +
            " age int," +
            " member function getId(n int,na varchar) return int," +
            " member function getAge(n int,na varchar ) return int" +
            ");create type body func_dep_base_type as" +
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
            " member function getAddr(n int,na varchar) return varchar" +
            ");create type body pack_dep_base_type as" +
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
            "            end;";
    String sql9="create type syno_base_type as object" +
            "(" +
            "  name varchar," +
            "  addr varchar," +
            "  member function getName(n int,na numeric(21,6)) return varchar," +
            "  member function getAddr(n int,na varchar) return varchar" +
            ");" +
            "/" +
            "create type body syno_base_type as" +
            "member function getName(n int,na numeric(21,6)) return varchar" +
            "as" +
            "begin " +
            " return name;" +
            "end;" +
            "member function getAddr(n int,na varchar) return varchar" +
            "as" +
            "begin" +
            " return addr;" +
            "end;" +
            "end;";
    String sql10="create type syno_base_type as object" +
            "(" +
            "  name varchar," +
            "  addr varchar," +
            "  member function getName() return varchar," +
            "  member function getAddr() return varchar" +
            ");create type body syno_base_type as" +
            "member function getName() return varchar" +
            "as" +
            "begin " +
            " return name;" +
            "end;" +
            "member function getAddr() return varchar" +
            "as" +
            "begin" +
            " return addr;" +
            "end;" +
            "end;" ;
    String sql11=" create type acl_ch_type as object" +
            "  (" +
            "  id int," +
            "  name varchar," +
            "  member function getId() return int," +
            "  member function getName() return varchar" +
            "  );  create type body acl_ch_type as" +
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

    XuguParserApi xuguParserApi = new XuguParserApi();
    Map<String,String> attributesHashMap;
    Map<String, String> methodsMap ;

    @Test
    public void udtTest1(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql1);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
            Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                System.out.println("键："+entry.getKey());
                System.out.println("值："+entry.getValue());
            }
    }

    @Test
    public void udtTest2(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql2);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }


    public void udtTest3(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql3);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest4(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql4);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest5(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql5);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest6(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql6);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest7(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql7);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest8(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql8);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest9(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql9);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest10(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql10);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }

    @Test
    public void udtTest11(){
        CreateTypeBean createTypeBean = xuguParserApi.parseCreateType(sql11);
        attributesHashMap =  createTypeBean.getAttributeMap();
        methodsMap = createTypeBean.getMemberMap();
        Iterator<Map.Entry<String, String>> it = attributesHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("键："+entry.getKey());
            System.out.println("值："+entry.getValue());
        }
    }
}
