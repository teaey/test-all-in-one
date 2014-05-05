import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

/**
 * Created by wxf on 14-5-4.
 */
public class Main {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        Config conf = new Config();
        conf.setNumWorkers(6);
        conf.setMaxSpoutPending(5000);
        TopologyBuilder tb = new TopologyBuilder();
        //StormSubmitter.submitTopology("t-1", conf, tb.createTopology());
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("t-1", conf, tb.createTopology());
    }
}
