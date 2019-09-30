package com.zfc.code;

import com.zfc.code.data.ItemProperty;
import com.zfc.code.data.JavaType;
import com.zfc.code.data.TableProperty;
import com.zfc.code.util.NameFormator;
import com.zfc.code.util.Underline2Camel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zufeichao
 * @ProjectName zfc-autocode
 * @Description 代码生成工具
 * @Date 2019-09-30 9:36
 * @T: AutoCode
 **/
public class AutoCode {

    public static final String url = "jdbc:mysql://127.0.0.1:3306/study?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public static final String  username = "root";
    public static final String  password =  "123456";

    /** 驱动*/
    public static final String  driver = "com.mysql.jdbc.Driver";
    /**基础包路径*/
    public static final String  basePackage = "com.zfc.mall";
    /**模块名称   产品模块 填产品包名*/
    public static final String  modelName = "order";
    /** 临时路径*/
    public static final String tmpDir = "C:\\Users\\11190\\Desktop\\test";
    /** 项目名称 如果项目名与目录名，数据库名不一致需要手动更改生成文件的目录*/
    public static final String project = "mall-tiny-01";
    /*** 数据库表名称**/
    private static final String TABLE_NAME = "user";
    /** 查询条件*/
    public static final String searchField = null;

    public static final String preDir = "C:\\Users\\11190\\Desktop\\test";

    /**代码基础路径*/
    public static final String baseDir = "C:\\Users\\11190\\Desktop\\test";

    /**
     * @Author zufeichao
     * @Description 主函数开始
     * @Date 9:38 2019/9/30
     * @Param [args]
     * @return void
     **/
    public static void main(String[] args) {
        try{
            String tables[] = TABLE_NAME.split(",");
            for(String tableName : tables){
                //初始化表
                TableProperty table = initTable(tableName,project,searchField);

                /**初始化模块对应的路径*/
                Map<String,String> modelToFile =  initModelToFile(table,preDir);

                //生成代码
                List<CreatorCode> ccs = CreatorCode.allCreatorCode(table,modelToFile,tmpDir);



            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @Author zufeichao
     * @Description 获取表信息
     * @Date 10:08 2019/9/30
     * @Param
     * @return
     **/
     public static TableProperty initTable(String tableName,String project,String... searchField) throws  Exception{
        TableProperty table = new TableProperty();
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        String comm = "";
        //show table status 获取表的信息
        ResultSet rs = conn.prepareStatement("show table STATUS").executeQuery();
        while(rs.next()){
            //1.Name 表名称 18.Comment 表备注
            if (rs.getString("Name").equals(tableName)){
                comm = rs.getString("Comment");
                break ;
            }
        }
         List<ItemProperty> items = new ArrayList<ItemProperty>(),
                 search = new ArrayList<ItemProperty>();

         ItemProperty priKey = new ItemProperty();
         //show full fields from table;查看表信息
         // 1.Field 字段 2.Type 字段类型 4.Null NO不能为空，YES可以为空 5.Key 主键 9.Comment 备注
         rs = conn.prepareStatement("show full fields from " + tableName).executeQuery();

         while(rs.next()){
             ItemProperty item = new ItemProperty();
             String fieldName = rs.getString("Field");
             String type = rs.getString("Type");
             String comment = rs.getString("Comment");
             String key = rs.getString("Key");
             String isNUll = rs.getString("Null");

             item.setFiledName(fieldName);
             //下划线转驼峰名称 数据库使用下划线
             item.setName(Underline2Camel.underline2Camel(fieldName,true));
             item.setDbType(type);
             item.setType(type.split("\\(")[0]);

             item.setJtype(JavaType.get(item.getType()));
             item.setComment(comment);

             //判断是否可空
             item.setNullable(isNUll);

             //判断主键
             if("PRI".equals(key)) {
                 priKey = item;
                 item.setPriKey(true);
             } else {
                 item.setPriKey(false);
             }
             items.add(item);

             if(searchField == null || searchField.length == 0){ continue;}
             for(String s : searchField) {
                 if(fieldName.equals(s)) {
                     search.add(item);
                     break;
                 }
             }

         }

         if(search.size() == 0) {
             search.addAll(items);
         }
         table.setName(tableName);
         table.setFirstBigName(Underline2Camel.upperCase(tableName));
         table.setFirstLowerName(Underline2Camel.lowerCase(Underline2Camel.underline2Camel(tableName,false)));
         table.setComment(comm.split(";")[0]);
         table.setItems(items);
         table.setSearch(search);
         table.setPrikey(priKey);
         table.setBasicPackage(basePackage);
         table.setModeName(modelName);

         table.setProject(project);
         return table;
     }

     /**
      * @Author zufeichao
      * @Description 初始化模块对应的路径
      * @Date 10:44 2019/9/30
      * @Param [table, preDir]
      * @return java.util.Map<java.lang.String,java.lang.String>
      **/
     public static Map<String,String> initModelToFile(TableProperty table ,String preDir){

         Map<String,String> modelToFile = new HashMap<String,String>();

         modelToFile.put("java/Query.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Query.java");
         modelToFile.put("java/Form.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Form.java");
         modelToFile.put("java/VO.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"VO.java");
         modelToFile.put("java/PO.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"PO.java");
         modelToFile.put("java/service.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Service.java");
         modelToFile.put("java/serviceImpl.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"ServiceImpl.java");
         modelToFile.put("java/cover.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Converter.java");
         modelToFile.put("java/Mapper.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Mapper.java");
         modelToFile.put("java/ApiController.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Controller.java");
         modelToFile.put("java/xml.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"Mapper.xml");
         modelToFile.put("java/json.vm", baseDir+"\\"+NameFormator.toUUCase(table.getName())+"\\"+ NameFormator.toUUCase(table.getName())+"json.txt");

         return modelToFile;

     }

}
