package com.zfc.code.data;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @Author zufeichao
 * @ProjectName zfc-autocode
 * @Description TODO
 * @Date 2019-09-30 11:22
 * @T: DataSource
 **/
public class DataService {

    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    public static  String  buildData(String tpl, Map<String,Object> dataInfo) throws Exception {
        logger.info("template file :{}, build data : {}",tpl,dataInfo);
        Properties properties = new Properties();

        properties.setProperty(Velocity.INPUT_ENCODING,"UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING,"UTF-8");

        properties.setProperty(Velocity.RUNTIME_LOG,"false");
        properties.setProperty(Velocity.RUNTIME_LOG_ERROR_STACKTRACE,"true");
        properties.setProperty(Velocity.RUNTIME_LOG_INFO_STACKTRACE,"false");
        properties.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM,"false");
        properties.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.NullLogSystem");
        properties.setProperty(Velocity.RUNTIME_LOG_REFERENCE_LOG_INVALID,"false");
        properties.setProperty(Velocity.RUNTIME_LOG_WARN_STACKTRACE,"false");

        Velocity velocity = new Velocity();
        velocity.init(properties);

        VelocityContext context=new VelocityContext(dataInfo);

        return convert(tpl,context);

    }


     public static String convert(String vmFileName, VelocityContext context) throws Exception{
        StringWriter w = new StringWriter();
        Velocity.evaluate( context, w, "util.velocity", Loader.getResourceAsStream(vmFileName) );
        System.out.println(w.toString());
        return w.toString();
    }
}
