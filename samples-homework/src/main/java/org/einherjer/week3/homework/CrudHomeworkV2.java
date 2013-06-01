package org.einherjer.week3.homework;

import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

public class CrudHomeworkV2 {

    /**
     * assumes the existence of a DB "school" with a collection "students",
     * each document with name, and an array of scores, each with a type and score
     * (can be imported from students.json file in src/main/resources/week3/homework
     * by running mongoimport -d school -c students < students.json)
     */
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo("localhost:27017");
        DB db = mongo.getDB("school");
        DBCollection students = db.getCollection("students");
        DBCursor cursor = students.find(new BasicDBObject());
        while (cursor.hasNext()) {
            DBObject student = cursor.next();
            ArrayList scores = (BasicDBList) student.get("scores");
            DBObject minScoreHomework = new BasicDBObject("score", Double.MAX_VALUE);
            for (Object o : scores) {
                DBObject assessment = (DBObject) o;
                if (assessment.get("type").equals("homework")) {
                    if ((Double) assessment.get("score") < (Double) minScoreHomework.get("score")) {
                        minScoreHomework = assessment;
                    }
                }
            }
            students.update(new BasicDBObject("_id", student.get("_id")),
                    new BasicDBObject("$pull", new BasicDBObject("scores", minScoreHomework)),
                    false, false, WriteConcern.SAFE);
        }
    }
}
