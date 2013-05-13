package org.einherjer;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HelloWorldFreemarkerStyle {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        Template helloTemplate = config.getTemplate("hello.ftl");
        StringWriter writter = new StringWritter();
        Map<String, Object> freemarkerData = new HashMap<String, Object>();

    }

}
