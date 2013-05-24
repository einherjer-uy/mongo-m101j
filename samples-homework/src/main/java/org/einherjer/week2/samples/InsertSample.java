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

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class InsertSample {
    public static void main(String[] args) throws UnknownHostException {
        Mongo client = new Mongo();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("insertSample");

        collection.drop();

        DBObject doc = new BasicDBObject().append("x", 1);
        System.out.println(doc);
        collection.insert(doc);
        System.out.println(doc); //_id field gets generated

        DBObject doc2 = new BasicDBObject("_id", new ObjectId()).append("x", 1);
        System.out.println(doc2);
        collection.insert(doc2);
        System.out.println(doc2); //same as doc1

        DBObject doc3 = new BasicDBObject("_id", "123").append("x", 1);
        System.out.println(doc3);
        collection.insert(doc3);
        System.out.println(doc3); //_id field set by to a type different than ObjectId

        collection.insert(doc); //duplicate exception

    }
}
