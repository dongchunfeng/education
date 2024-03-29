package com.online.generator.vue;

import com.online.generator.util.DbUtil;
import com.online.generator.util.Field;
import com.online.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * @Description 用于生成后端代码
 * @Author Mr.Dong <dongcf1997@163.com>
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/4/15 20:30
 */
public class ServerGenerator {
    static String MODULE = "file";
    static String toVuePath = "generator\\src\\main\\java\\com\\online\\generator\\vue\\";

    static String generatorConfigPath = "server\\src\\main\\resources\\generator\\generatorConfig.xml";

    public static void main(String[] args) throws Exception, TemplateException {
        String module = MODULE;

        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader=new SAXReader();
        //读取xml文件到Document中
        Document doc=reader.read(file);
        //获取xml文件的根节点
        Element rootElement=doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”的节点
        tableElement=contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DbUtil.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表："+tableElement.attributeValue("tableName"));
        System.out.println("Domain："+tableElement.attributeValue("domainObjectName"));


        //搜索框名字
        String title = "昵称";
        //搜索的参数
        String Title = "name";
        //api名字
        String SECTION = "FILE";
        //val
        String val = "${val}";

        List<Field> fieldList = DbUtil.getColumnByTableName(tableName);
        Set<String> javaTypes = getJavaTypes(fieldList);

        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("Searchname", Title);
        map.put("searchname", title);
        map.put("tableNameCn", tableNameCn);
        map.put("DOMAIN", SECTION);
        map.put("val", val);
        map.put("module", module);

        map.put("fieldList", fieldList);
        map.put("typeSet", javaTypes);

        FreemarkerUtil.initConfig("vue.ftl");
        FreemarkerUtil.generator(toVuePath + domain + ".vue", map);

        FreemarkerUtil.initConfig("api.ftl");
        FreemarkerUtil.generator(toVuePath + module+"."+domain + ".api.js", map);

    }


//    public static void generateController() throws IOException, TemplateException {
//
//    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
