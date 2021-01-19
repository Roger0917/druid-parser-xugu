package com.alibaba.druid.xugu.pack;

import com.alibaba.druid.sql.dialect.xugu.api.XuguParserApi;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateFunctionBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreatePackageBean;
import com.alibaba.druid.sql.dialect.xugu.api.bean.CreateProcedureBean;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Gosin
 * @Date: 2021/1/5 16:35
 */
public class PakcageTest {


    /**
     * 包中包含的存储过程和函数存在无参数类型
     *
     * 无参数的包类对象报错为空指针，无法获取到包内对象名
     */

    public void testpackage1(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "FUNCTION dfsfdee() RETURN INTEGER;" +
                "function fefe(id int) return integer;" +
                "function  fefe (name varchar(20)) return integer;" +
                "procedure dfeffd (id integer);" +
                "procedure dfefefef()"+
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateProcedureBean> procedureBeans =  createPackageBean.getCreateProcedureBeans();
        List<CreateFunctionBean> functionBeans = createPackageBean.getCreateFunctionBeans();
        for (int i = 0; i < procedureBeans.size(); i++) {
            System.out.println("存储过程名"+procedureBeans.get(i).getProcedureName());
        }
        for(int i = 0; i<functionBeans.size();i++){
            System.out.println("存储函数名"+functionBeans.get(i).getFunctionName());
        }
    }


    @Test
    public void testPackage1(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name varchar(20)) return integer;" +
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateFunctionBean> functionBeans = createPackageBean.getCreateFunctionBeans();
        System.out.println("存储函数带参数含有标度");
        for(int i = 0; i<functionBeans.size();i++){
            System.out.println("存储函数名"+functionBeans.get(i).getFunctionName());
            System.out.println("参数位置"+functionBeans.get(i).getParamSize());
            System.out.println(functionBeans.get(i).getParams().get(0).get(1).toString());
        }
    }

    @Test
    public void testPackage3(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name varchar(20)) return integer;" +
                "function  feeyk (name varchar(22)) return integer;" +
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateFunctionBean> functionBeans = createPackageBean.getCreateFunctionBeans();
        System.out.println("存储函数带参数含有标度");
        for(int i = 0; i<functionBeans.size();i++){
            System.out.println("存储函数名"+functionBeans.get(i).getFunctionName());
            System.out.println("参数位置"+functionBeans.get(i).getParamSize());
            System.out.println(functionBeans.get(i).getParams().get(0).get(1).toString());
        }
    }


    @Test
    public void testPackage11(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name numeric(20,2)) return integer;" +
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateFunctionBean> functionBeans = createPackageBean.getCreateFunctionBeans();
        System.out.println("存储函数带参数含有精度标度");
        for(int i = 0; i<functionBeans.size();i++){
            System.out.println("存储函数名"+functionBeans.get(i).getFunctionName());
            System.out.println("参数位置"+functionBeans.get(i).getParamSize());
            System.out.println(functionBeans.get(i).getParams().get(0).get(1).toString());
        }
    }

    @Test
    public void testPackage111(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "function  fefe (name numeric(20,2)) return integer;" +
                "function  fredfd (name numeric(21,2)) return integer;"+
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateFunctionBean> functionBeans = createPackageBean.getCreateFunctionBeans();
        System.out.println("多个存储函数带参数含有精度标度");
        for(int i = 0; i<functionBeans.size();i++){
            System.out.println("存储函数名"+functionBeans.get(i).getFunctionName());
            System.out.println("参数位置"+functionBeans.get(i).getParamSize());
            System.out.println(functionBeans.get(i).getParams().get(0).get(1).toString());
        }
    }


    @Test
    public void testpackage2(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "procedure dfeffd (name varchar(33));" +
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateProcedureBean> procedureBeans =  createPackageBean.getCreateProcedureBeans();
        System.out.println("存储过程带参数含有精度");
        for (int i = 0; i < procedureBeans.size(); i++) {
            System.out.println("存储过程名"+procedureBeans.get(i).getProcedureName());
        }

    }

    @Test
    public void testpackage4(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "FUNCTION dfsfdee() RETURN INTEGER;" +
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateFunctionBean> functionBeans = createPackageBean.getCreateFunctionBeans();
        System.out.println("存储函数无参数");
        for(int i = 0; i<functionBeans.size();i++){
            System.out.println("存储函数名"+functionBeans.get(i).getFunctionName());
        }
    }


    @Test
    public void testpackage5(){
        String sql =  "CREATE OR REPLACE PACKAGE table_con IS " +
                "procedure dfefefef()"+
                " END;";
        XuguParserApi xuguParserApi = new XuguParserApi();
        CreatePackageBean createPackageBean = xuguParserApi.parseCreatePackage(sql);
        List<CreateProcedureBean> procedureBeans =  createPackageBean.getCreateProcedureBeans();
        System.out.println("存储函数无参数");
        for (int i = 0; i < procedureBeans.size(); i++) {
            System.out.println("存储过程名"+procedureBeans.get(i).getProcedureName());
        }
    }



}
