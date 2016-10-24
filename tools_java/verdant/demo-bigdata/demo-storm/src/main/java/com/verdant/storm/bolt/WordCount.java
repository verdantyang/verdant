package com.verdant.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordCount implements IRichBolt {

    private static final Logger logger = LoggerFactory.getLogger(WordCount.class);
    private static final long serialVersionUID = -3748016050169786320L;

    private Integer id = 0;
    private String name = "";
    private OutputCollector collector;
    Map<String, Integer> counts = new HashMap<>();

    @Override
    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        this.collector = collector;
        this.id = context.getThisTaskId();
        this.name = context.getThisComponentId();
    }

    @Override
    public void execute(Tuple input) {
        String word = input.getString(0);
        Integer count = counts.get(word);
        if (count == null) count = 0;
        count++;
        counts.put(word, count);
        logger.info("Word Counter [" + word + ": " + count + "]");
        collector.emit(new Values(word, count));
    }

    @Override
    public void cleanup() {
        logger.info("-- Word Counter [" + name + "-" + id + "] --");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
