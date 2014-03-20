import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.util.Date;

/**
 * @author xiaofei.wxf
 */
public class App1 {
    public static void main(String[] args) throws InterruptedException {
        DB db = DBMaker.newDirectMemoryDB().make();

        // this is wrong, do not do it !!!
        //  Map<String,List<Long>> map

        //correct way is to use composite set, where 'map key' is primary key and 'map value' is secondary value
        HTreeMap<Object, Object> map = db.getHashMap("anger");


        OO o = new OO(1,2,"3", new Date());
        map.put(o, o);
//        Object str = map.get("abc");
//        Object inr = map.get(123);
//        Object dat = map.get(d);
        Object oo = map.get(o);
        System.out.println(map.get(oo));

        Thread.sleep(10000000);
        //db.close();
    }

}
