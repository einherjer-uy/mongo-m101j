package org.einherjer.week1.samples;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HelloWorldMongoDBSparkFreemarkerStyle {

    //insert something in the DB first. It has to have a "name" key, which is expected by the freemarker template:
    //  use blog;
    //  db.users.save({name:"mongo user"});
    public static void main(String[] args) throws UnknownHostException {
        final Configuration config = new Configuration();
        config.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        Mongo mongo = new Mongo("localhost:27017");
        DB db = mongo.getDB("blog");
        final DBCollection postsCollection = db.getCollection("users");
        
        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = config.getTemplate("week1/samples/hello.ftl");
                    DBObject document = postsCollection.findOne();

                    //BasicDBObject extends BasicBSONObject implements DBObject -> BasicBSONObject extends LinkedHashMap,
                    //  that's why BasicDBObject can be use here in the place of a Map
                    helloTemplate.process(document, writer);
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
