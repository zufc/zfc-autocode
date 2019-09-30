package com.zfc.code;

import com.zfc.code.data.DataService;
import com.zfc.code.data.TableProperty;
import com.zfc.code.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zufeichao
 * @ProjectName zfc-autocode
 * @Description TODO
 * @Date 2019-09-30 10:52
 * @T: CreatorCode
 **/
public class CreatorCode {

    protected TableProperty table;

    protected  String targetDir;

    protected  String fileName = "temp";

    protected String tmpl;

    private static List<CreatorCode> creators = new ArrayList<CreatorCode>();
    private static Map<String, Object> map = new HashMap<String, Object>();

    public CreatorCode(){

    }

    public static List<CreatorCode> allCreatorCode(TableProperty table ,Map<String,String> modelToFile,String tmpDir) throws Exception{
        List<CreatorCode> creators = new ArrayList<CreatorCode>();
        map.put("table",table);
        for(String k : modelToFile.keySet()){
            CreatorCode cc = new CreatorCode();
            cc.setTable(table);
            String target = modelToFile.get(k);
            cc.setTmpl(k);
            int i = target.lastIndexOf("\\");
            String filename = target.substring(i+1);
            String targetDir = target.substring(0, i+1);
            System.out.println("targetDir:"+targetDir);
            cc.setTargetDir(targetDir);
            /*System.out.println(filename);*/
            cc.setFileName(filename);
            creators.add(cc);

            String content = DataService.buildData(cc.tmpl,map);

            System.out.println("----转换后的内容----"+content);

            //特殊处理{\}"\\{\\}
            content = content.replace("ZZ", "");
            //写到临时目录

            String fn = tmpDir + File.separator + cc.table.getName() + File.separator + cc.getFileName();
            System.out.println("写文件开始fileName:"+fn);
            FileUtil.writeFile(fn, content);
        }
        return creators;
    }




    public TableProperty getTable() {
        return table;
    }

    public void setTable(TableProperty table) {
        this.table = table;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTmpl() {
        return tmpl;
    }

    public void setTmpl(String tmpl) {
        this.tmpl = tmpl;
    }

    public static List<CreatorCode> getCreators() {
        return creators;
    }

    public static void setCreators(List<CreatorCode> creators) {
        CreatorCode.creators = creators;
    }

    public static Map<String, Object> getMap() {
        return map;
    }

    public static void setMap(Map<String, Object> map) {
        CreatorCode.map = map;
    }
}
