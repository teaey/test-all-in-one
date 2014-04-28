package mongodb;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * @author xiaofei.wxf
 */
public class Db {
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("test");

        //db.person.insert({"name":"xiaofei","age":26})
//        DBObject v1 = new BasicDBObject();
//        v1.put("name","xiaofei");
//        v1.put("age", 26);
//        db.getCollection("person").insert(v1);

        //db.person.find()
        DBCollection users = db.getCollection("person");
        DBCursor cur = users.find();
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }

        //db.person.find({"name":"xiaofei"})
        cur = users.find(new BasicDBObject("name", "xiaofei"));
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }

        //db.person.update({"name":"xiaofei"},{"name":"xiaofei1""})
        users.update(new BasicDBObject("name", "wxf"), new BasicDBObject("name", "wxf1"));

        users.remove(new BasicDBObject("name", "wxf1") );
    }
}
