package com.zfc.code.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @Author zufeichao
 * @ProjectName zfc-autocode
 * @Description TODO
 * @Date 2019-09-30 11:24
 * @T: Loader
 **/
public class Loader {
    private static Logger logger = LoggerFactory.getLogger(Loader.class);

    public static URL getResource(String resource){
        URL url = null;
        try{

            ClassLoader classLoader = getTCL();
            if (classLoader != null){
                url = classLoader.getResource(resource);
                if (url != null){
                    return url;
                }
            }

            classLoader = Loader.class.getClassLoader();
            if (classLoader != null){
                url = classLoader.getResource(resource);
                if (url != null){
                    return url;
                }
            }

        }catch (Throwable t){
            logger.warn("无法获取资源！", t);
        }

        return ClassLoader.getSystemResource(resource);

    }

    private static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {
        Method method = null;
        try {
            method = Thread.class.getMethod("getContextClassLoader", null);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return (ClassLoader) method.invoke(Thread.currentThread(), null);
    }


    /**
     * @Author zufeichao
     * @Description 加载指定类下面的配置文件
     * @Date 11:37 2019/9/30
     * @Param [resourceClass, resource]
     * @return java.io.InputStream
     **/
    public static InputStream getResourceAsStream(Class resourceClass,String resource) throws Exception{

        logger.info("Configuration resource: {}", resource);
        InputStream  inputStream = resourceClass.getResourceAsStream(resource);
        if (inputStream == null){
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        }

        if (inputStream == null){
            logger.warn(resource + " not found");
            throw new Exception(resource + " not found");
        }

        return inputStream;
    }


    /**
     * 获取当前CLASSPATH路径下面的资源配置文件
     * @param resource
     * @return
     * @throws Exception
     */
    public static InputStream getResourceAsStream(String resource) throws Exception {
        logger.debug("Configuration resource:{} " , resource);
        InputStream stream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resource);
        if (stream == null) {
            logger.warn(resource + " not found");
            throw new Exception(resource + " not found");
        }
        return stream;
    }




}
