package com.verdant.storm.wordcount;

import com.verdant.storm.wordcount.bolt.SplitSentenceBolt;
import com.verdant.storm.wordcount.bolt.WordCount;
import com.verdant.storm.wordcount.spout.RandomSentenceSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;


public class WordCountTopology {
    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new RandomSentenceSpout(), 5);

        builder.setBolt("split", new SplitSentenceBolt(), 8)
                .shuffleGrouping("spout");

        builder.setBolt("count", new WordCount(), 12)
                .fieldsGrouping("split", new Fields("word"));

        Config conf = new Config();
//        conf.setDebug(true);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
//            Thread.sleep(10000);
//            cluster.shutdown();
//            System.exit(0);
        }

        synchronized (WordCountTopology.class) {
            try {
                WordCountTopology.class.wait();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
