package com.ycy.demo;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.ycy.demo.spout.RandomSentenceSpout;
import com.ycy.demo.bolt.WordCount;
import com.ycy.demo.bolt.SplitSentenceBolt;

public class WordCountTopology {
         
    public static void main(String[] args) throws Exception {
        
        TopologyBuilder builder = new TopologyBuilder();
        
        builder.setSpout("spout", new RandomSentenceSpout(), 5);
        
        builder.setBolt("split", new SplitSentenceBolt(), 8)
                 .shuffleGrouping("spout");
        builder.setBolt("count", new WordCount(), 12)
                 .fieldsGrouping("split", new Fields("word"));

        Config conf = new Config();
        conf.setDebug(true);
        
        if(args!=null && args.length > 0) {
            conf.setNumWorkers(3);     
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {        
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());     
            Thread.sleep(10000);
            cluster.shutdown();
        }
    }
}
