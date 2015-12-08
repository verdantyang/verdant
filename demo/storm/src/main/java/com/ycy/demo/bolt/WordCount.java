package com.ycy.demo.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCount implements IRichBolt {

	private static final long serialVersionUID = -3748016050169786320L;
	Integer id = 0;
	String name = "";
	private OutputCollector collector;
    Map<String, Integer> counts = new HashMap<String, Integer>();
    
    @SuppressWarnings("rawtypes")
	@Override
    public void prepare(Map stormConf, TopologyContext context,
    		OutputCollector collector) {
    	this.collector = collector;
    	this.name = context.getThisComponentId();
    	this.id = context.getThisTaskId();
    }      

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word", "count"));
    }
	@Override
	public void execute(Tuple input) {
		String word = input.getString(0);
        Integer count = counts.get(word);
        if(count==null) count = 0;
        count++;
        counts.put(word, count);
        collector.emit(new Values(word, count));	
	}
	
    @Override
    public void cleanup() {
    	System.out.println("-- Word Counter ["+name+"-"+id+"] --");
    	for(Map.Entry<String, Integer> entry : counts.entrySet()) {
    		System.out.println(entry.getKey()+": "+entry.getValue());
    	}
    }
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
