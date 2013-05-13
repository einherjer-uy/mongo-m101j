package org.einherjer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HelloWorldSparkFreemarkerStyle {

    public static void main(String[] args) {
        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = config.getTemplate("hello.ftl");
                    Map<String, Object> freemarkerData = new HashMap<String, Object>();
                    freemarkerData.put("name", "Freemarker");
                    helloTemplate.process(freemarkerData, writer);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    this.halt(500);
                }
                catch (TemplateException e) {
                    e.printStackTrace();
                    this.halt(500);
                }
                return writer;
            }
        });
    }

}
