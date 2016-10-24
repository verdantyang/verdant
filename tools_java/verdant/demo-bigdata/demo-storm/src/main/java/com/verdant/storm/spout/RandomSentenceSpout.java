package com.verdant.storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomSentenceSpout extends BaseRichSpout {

    private static final long serialVersionUID = 1051475047939234586L;
    private static final Logger logger = LoggerFactory.getLogger(RandomSentenceSpout.class);

    SpoutOutputCollector _collector;
    Random _random;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        _collector = collector;
        _random = new Random();
    }

    /**
     * The nextuple it is called continuous,
     * so if we have been readed the file we will wait and then return
     */
    @Override
    public void nextTuple() {
        Utils.sleep(100);
        String[] sentences = new String[]{
                "the cow jumped over the moon",
                "an apple a day keeps the doctor away",
                "four score and seven years ago",
                "snow white and the seven dwarfs",
                "i am at two with nature"};
        String sentence = sentences[_random.nextInt(sentences.length)];
        logger.info("Generate sentence {}", sentence);
        _collector.emit(new Values(sentence));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("Sentence"));
    }

}