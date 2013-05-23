package org.einherjer.week1.samples;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HelloWorldFreemarkerStyle {

    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        try {
            Template helloTemplate = config.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String, Object> freemarkerData = new HashMap<String, Object>();
            freemarkerData.put("name", "Freemarker");
            helloTemplate.process(freemarkerData, writer);
            System.out.println(writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}
