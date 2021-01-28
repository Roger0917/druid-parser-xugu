package com.alibaba.druid.xugu.schema;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLCreateViewStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLUnionQuery;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectJoin;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.parser.XuguStatementParser;
import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetSchemaFromView extends TestCase {
    
    public void test6(){
        String sql = "";
    }
    
    public  void test(){
        String sql = "create view sysdba.view1 as select id from sysdba.students";
        List<String> schemas = XuguParserApi.getSchemaFromView(sql);
        System.out.printf("222");
    }
    public void test2(){
        String sql = "create view user_sod.view2 as SELECT all_spaces.nodeid AS `nodeId`,clusters.node_ip AS `nodeIp`,all_spaces.space_id AS `spaceId`,all_spaces.space_name AS `spaceName`,all_datafiles.PATH AS `path` \n" +
                "FROM u1.sys_all_tablespaces  all_spaces INNER JOIN\n" +
                "                u2.sys_all_datafiles all_datafiles ON all_spaces.space_id=all_datafiles.space_id INNER JOIN u3.sys_clusters clusters ON all_spaces.nodeid=clusters.node_id\n" +
                "                WHERE all_spaces.nodeid = all_datafiles.nodeid and  all_spaces.space_type='DATA_SPACE';";
        List<String> schemas = XuguParserApi.getSchemaFromView(sql);
        System.out.println(222);
    }
    
    public void test3(){
        String sql = "create view sysdba.view1 as SELECT tags.tagname\n" +
                "FROM u1.posts\n" +
                "    JOIN u2.posts_tags ON posts.id = posts_tags.post_id\n" +
                "    JOIN u1.tags ON posts_tags.tag_id = tags.id\n" +
                "    JOIN u2.tags_active ON tags.id = tags_active.tag_id\n" +
                "WHERE user_sod.tags_active.active = 'yes'";
        List<String> schemas = XuguParserApi.getSchemaFromView(sql);
        System.out.println(222);

        HashMap map = new HashMap();
        map.put("u1","t1");
        map.put("u2","t2");
        map.put("u3","t3");
        map.put("u4","t4");
        String replaceStr = XuguParserApi.replaceViewSqlSchema(sql,map);
        System.out.println(222);
    }
    
    public void test4(){
        String sql = "create view user_sod.view3 as\n" +
                "SELECT country FROM u1.Websites\n" +
                "UNION\n" +
                "SELECT country FROM u3.apps\n" +
                "ORDER BY country;";
        List<String> schemas = XuguParserApi.getSchemaFromView(sql);
        System.out.println(222);

    }
    
    private List<String> digui(OracleSelectJoin join,List<String> schemas){
        if(join.getLeft() instanceof OracleSelectTableReference){
            if(((OracleSelectTableReference) join.getRight()).getExpr() instanceof SQLPropertyExpr){
                schemas.add(((SQLPropertyExpr) ((OracleSelectTableReference) join.getRight()).getExpr()).getOwnerName());
            }
            if(((OracleSelectTableReference) join.getLeft()).getExpr() instanceof SQLPropertyExpr){
                schemas.add(((SQLPropertyExpr) ((OracleSelectTableReference) join.getLeft()).getExpr()).getOwnerName());
            }
        }else if (join.getLeft() instanceof OracleSelectJoin){
            OracleSelectTableReference reference = (OracleSelectTableReference) join.getRight();
            if(reference.getExpr() instanceof SQLPropertyExpr){
                schemas.add(((SQLPropertyExpr) reference.getExpr()).getOwnerName());
            }
            if(join.getLeft() instanceof OracleSelectJoin){
                digui((OracleSelectJoin) join.getLeft(),schemas);
            }else if(join.getLeft() instanceof OracleSelectTableReference){
                OracleSelectTableReference tableReference = (OracleSelectTableReference) join.getLeft();
                if(tableReference.getExpr() instanceof SQLPropertyExpr){
                    schemas.add(((SQLPropertyExpr)tableReference.getExpr()).getOwnerName());
                }
            }
        }
        return schemas;
    }
}
