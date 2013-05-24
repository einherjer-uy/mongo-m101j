/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.einherjer.week2.samples;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class UpdateRemoveSample {
    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection();

        List<String> names = Arrays.asList("alice", "bobby", "cathy", "david", "ethan");
        for (String name : names) {
            collection.insert(new BasicDBObject("_id", name));
        }

        // see scratch method

        printCollection(collection);
    }

    // these are all the statement I used throughout the lecture.
    private static void scratch(DBCollection collection) {
        //whole document replacement, remember the _id field is not replaces so in this case, since _id was the only field, age gets added
        //  if we do this a second time with gender:"F" instead of age:24 the document would end up with _id and gender fields, and age would be gone
        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("age", 24));

        //sets adds the gender field to the existing _id and age fields
        collection.update(new BasicDBObject("_id", "alice"),
                new BasicDBObject("$set", new BasicDBObject("gender", "F")));

        //upsert ("frank" didn't exist originally, so this update actually performs an insert)
        collection.update(new BasicDBObject("_id", "frank"),
                new BasicDBObject("$set", new BasicDBObject("age", 24)), true, false);

        //multi document update
        collection.update(new BasicDBObject(),
                new BasicDBObject("$set", new BasicDBObject("title", "Dr")), false, true);

        collection.remove(new BasicDBObject("_id", "frank"));
    }

    private static DBCollection createCollection() throws UnknownHostException {
        Mongo client = new Mongo();
        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("updateRemoveSample");
        collection.drop();
        return collection;
    }

    private static void printCollection(final DBCollection collection) {
        DBCursor cursor = collection.find().sort(new BasicDBObject("_id", 1));
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }
}
