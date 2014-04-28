package mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

/**
 * @author xiaofei.wxf
 */
public class BM {
    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo();
        DB db = mongo.getDB("test");
        final DBCollection users = db.getCollection("p");
        Benchmark.singelThread("insert 1000000", new Runnable() {
            @Override public void run() {
                for(int i=0;i<10000000;i++){
                    users.insert(new BasicDBObject("user:" + i, "value:" + i));
                }
            }
        });
        mongo.close();
    }
}
