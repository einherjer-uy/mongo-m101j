package org.einherjer.week2.homework;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class CrudHomework {

    /**
     * assumes the existence of a DB "students" with a collection "grades",
     * each document with student_id, type, score
     */
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo("localhost:27017");
        DB db = mongo.getDB("students");
        DBCollection gradesCollection = db.getCollection("grades");
        DBObject document = gradesCollection.findOne();
        
        System.out.println(document);
    }

}
