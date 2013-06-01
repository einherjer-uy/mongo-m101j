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

package course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * sample document:
 *   {
 *       "_id" : ObjectId("513d396da0ee6e58987bae74"),
 *       "author" : "andrew",
 *       "body" : "Representatives from the planet Mars announced today that the planet would adopt MongoDB as a planetary standard. Head Martian Flipblip said that MongoDB was the perfect tool to store the diversity of life that exists on Mars.",
 *       "comments" : [
 *           {
 *               "author" : "Larry Ellison",
 *               "body" : "While I am deeply disappointed that Mars won't be standardizing on a relational database, I understand their desire to adopt a more modern technology for the red planet.",
 *               "email" : "larry@oracle.com"
 *           },
 *           {
 *               "author" : "Salvatore Sanfilippo",
 *               "body" : "This make no sense to me. Redis would have worked fine."
 *           }
 *       ],
 *       "date" : ISODate("2013-03-11T01:54:53.692Z"),
 *       "permalink" : "martians_to_use_mongodb",
 *       "tags" : [
 *           "martians",
 *           "seti",
 *           "nosql",
 *           "worlddomination"
 *       ],
 *       "title" : "Martians to use MongoDB"
 *   }
 *
 */
public class BlogPostDAO {
    DBCollection postsCollection;

    public BlogPostDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public DBObject findByPermalink(String permalink) {
        return postsCollection.findOne(new BasicDBObject("permalink", permalink));
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<DBObject> findByDateDescending(int limit) {
        List<DBObject> posts = new ArrayList<DBObject>();
        DBCursor c = postsCollection.find(new BasicDBObject()).sort(new BasicDBObject("date", -1)).limit(limit);
        while (c.hasNext()) {
            posts.add(c.next());
        }
        return posts;
    }

    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();

        BasicDBObject post = new BasicDBObject();
        post.put("title", title);
        post.put("author", username);
        post.put("body", body);
        post.put("date", new Date());
        post.put("permalink", permalink);
        post.put("comments", new BasicDBList());
        post.put("tags", tags);
        postsCollection.insert(post);
        
        return permalink;
    }

    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

        DBObject comment = new BasicDBObject("author", name).append("body", body);
        if (email != null) {
            comment.put("email", email);
        }

        postsCollection.update(new BasicDBObject("permalink", permalink),
                new BasicDBObject("$push", new BasicDBObject("comments", comment)));
    }

}
