package org.einherjer.week2.homework;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteConcern;

public class CrudHomeworkFunctionalStyle {

    /**
     * assumes the existence of a DB "students" with a collection "grades",
     * each document with student_id, type, score
     * (can be imported from grades.json file in src/main/resources/week2/homework
     * by running mongoimport -d students -c grades < grades.json)
     */
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo("localhost:27017");
        DB db = mongo.getDB("students");
        DBCollection grades = db.getCollection("grades");
        DBCursor cursor = grades.find(QueryBuilder.start("type").is("homework").get())
                .sort(new BasicDBObject("student_id", 1));
        Collection<DBObject> toRemove = apply(cursor,
            new Function2Args() {
                @Override
                public DBObject apply(DBObject partial, DBObject curr){
                    if(partial==null){
                        return curr;
                    }
                    else{
                    if ((Double) curr.get("score") < (Double) partial.get("score")) {
                            return curr;
                        }
                    }
                    return partial;
                }
            },
            new Function2Args() {
                @Override
                public DBObject apply(DBObject partial, DBObject curr){
                    if(partial==null){
                        return null;
                    }
                    else{
                        if(!curr.get("student_id").equals(partial.get("student_id"))){
                            return partial;
                        }
                    }
                    return null;
                }
            }
        );
        for (DBObject o : toRemove) {
            grades.remove(new BasicDBObject("_id", o.get("_id")), WriteConcern.SAFE);
        }
    }

    private static void printCursor(final DBCursor cursor) {
        apply(cursor, new Function1ArgVoid() {
            @Override
            public void apply(DBObject o) {
                System.out.println(o);
            }
        });
    }

    private static Collection<DBObject> apply(final DBCursor cursor, final Function2Args compare, final Function2Args cut) {
        try {
            Collection<DBObject> result = new ArrayList<DBObject>();
            DBObject partial = null;
            while (cursor.hasNext()) {
                DBObject curr = cursor.next();
                DBObject out = cut.apply(partial, curr);
                if (out != null) {
                    result.add(out);
                    partial = curr;
                }
                else {
                    partial = compare.apply(partial, curr);
                }
            }
            if(partial!=null){
                result.add(partial);
            }
            return result;
        }
        finally {
            cursor.close();
        }
    }
    
    private static void apply(final DBCursor cursor, final Function1ArgVoid function) {
        try {
            while (cursor.hasNext()) {
                function.apply(cursor.next());
            }
        }
        finally {
            cursor.close();
        }
    }

    private static interface Function1ArgVoid {
        void apply(DBObject param);
    }
    
    private static interface Function2Args {
        DBObject apply(DBObject param1, DBObject param2);
    }
    
}
