package com.verdant.storm.window;


import com.verdant.storm.window.bolt.SumUpBolt;
import com.verdant.storm.window.spout.NumberSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WindowTopology {
    private static final TopologyBuilder builder = new TopologyBuilder();

    public WindowTopology() throws InterruptedException {
        String spoutId = "numberGenerator";
        String sumUp = "sumUp";
        builder.setSpout(spoutId, new NumberSpout(), 2);
        builder.setBolt(sumUp, new SumUpBolt(6, 2), 1)
                .fieldsGrouping(spoutId, new Fields("number"));
    }

    public static void main(String[] args) throws Exception {
        String topologyName = "slidingWindowCounts";
        Config conf = new Config();
        conf.setDebug(true);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology(topologyName, conf, builder.createTopology());
            Thread.sleep(10000);
//            cluster.shutdown();
//            System.exit(0);
        }
    }
}
