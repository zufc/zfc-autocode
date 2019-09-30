package com.zfc.code.util;

import org.omg.PortableInterceptor.TRANSPORT_RETRY;

import java.io.*;

/**
 * @Author zufeichao
 * @ProjectName zfc-autocode
 * @Description TODO
 * @Date 2019-09-30 11:48
 * @T: FileUtil
 **/
public class FileUtil {

    public static void writeFile(String fileName, String str) throws IOException {
        writeFile(fileName,str,"UTF-8");
    }

    /**
     * @Author zufeichao
     * @Description 往一个指定文件里全新写入指定编码字符串
     * @Date 12:04 2019/9/30
     * @Param [fileName, str, encoding]
     * @return void
     **/
    public static void writeFile(String fileName, String str, String encoding) throws IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            mkDir(fileName.substring(0,fileName.lastIndexOf(File.separator)));
            fw = new FileWriter(fileName);
            bw = new BufferedWriter(fw);
            bw.write(new String(str.getBytes(encoding)));
            bw.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (fw != null) {
                fw.close(); // 关闭档案
            }
            if (bw != null){
                bw.close();
            }
        }
    }

    public static String readFile(String fileName){
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line = "";
        StringBuffer sb = new StringBuffer();
        try{
            File file = new File(fileName);
            inputStreamReader = new InputStreamReader(new FileInputStream(file), "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\r");
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return sb.toString();
    }



    /**
     * @Author zufeichao
     * @Description 创建一个目录
     * @Date 11:58 2019/9/30
     * @Param [directoryName]
     * @return boolean
     **/
    public static boolean mkDir(String directoryName){
        boolean flag = false;
        File file = new File(directoryName);
        if ( file.mkdirs()){
            flag = true;
        }
        return flag;
    }


    /**
     * 拷贝文件
     *
     * @param sourceFile
     * @param destDir
     * @param newFileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String copyFile(String sourceFile, String destDir,
                                  String newFileName) throws FileNotFoundException, IOException {
        return copyFile(new FileInputStream(sourceFile), destDir, newFileName);
    }

    /**
     * 拷贝文件
     *
     * @param source
     * @param destDir
     * @param newFileName
     * @return
     * @throws IOException
     */
    public static String copyFile(InputStream source, String destDir,String newFileName) throws IOException {
        File dir = new File(destDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!dir.isDirectory()) {
            throw new IOException("dest dir (" + destDir + ") is not a folder");
        }
        String destFileFullName = null;
        BufferedOutputStream out = null;
        try {
            destFileFullName = destDir + File.separator + newFileName;
            out = new BufferedOutputStream(new FileOutputStream(
                    destFileFullName));
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = source.read(buffer, 0, 8192)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

        } finally {
            if (out != null) {
                out.close();
            }
        }
        return destFileFullName;
    }


    /**
     * 删除一个指定的文件
     */
    public static boolean deleteFile(String FileName) {
        boolean bRet = false;
        if (FileName != null && FileName.length() > 0) {
            File filename = new File(FileName);
            if (filename.delete()) {
                bRet = true;
            }
        }
        return bRet;
    }


}
