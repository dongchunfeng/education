package com.online.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @Description
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/15 20:11
 */
public class FreemarkerUtil {

    static String ftlPath = "generator\\src\\main\\java\\com\\online\\generator\\ftl\\";

    static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDirectoryForTemplateLoading(new File(ftlPath));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_31));
        temp = configuration.getTemplate(ftlName);
    }

    public static void generator(String fileName, Map<String,Object> map) throws IOException, TemplateException {
        FileWriter fileWriter = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        temp.process(map, bw);
        bw.close();
        fileWriter.close();
    }


}
