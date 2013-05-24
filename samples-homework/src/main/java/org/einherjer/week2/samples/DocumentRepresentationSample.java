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

import java.util.Arrays;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class DocumentRepresentationSample {
    public static void main(String[] args) {
        
        //1- using constructor and put
        BasicDBObject doc = new BasicDBObject();
        doc.put("userName", "jyemin");
        doc.put("birthDate", new Date(234832423));
        doc.put("programmer", true);
        doc.put("age", 8);
        //use Arrays.asList is handy for constucting arrays with predefined values
        doc.put("languages", Arrays.asList("Java", "C++"));
        
        //2- using append
        doc.put("address", new BasicDBObject("street", "20 Main")
                .append("town", "Westfield")
                .append("zip", "56789"));
        System.out.println(doc);
        
        //3- using BasicDBObjectBuilder
        DBObject johnSmithDoc = BasicDBObjectBuilder.start().append("firstname", "John").append("lastname","Smith").get();
        System.out.println(johnSmithDoc);
    }
}
