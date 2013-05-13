package org.einherjer;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class HelloWorldMongoDBStyle {

    //insert something in the DB first:
    //  use blog;
    //  db.posts.save({title:"test post"});
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo("localhost:27017");
        DB db = mongo.getDB("blog");
        DBCollection postsCollection = db.getCollection("posts");
        DBObject document = postsCollection.findOne();
        
        System.out.println(document);
    }

}
