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

public class SplitSentenceBolt implements IRichBolt {

	private static final long serialVersionUID = -715744563539599620L;
	private OutputCollector collector;
    Map<String, Integer> counts = new HashMap<String, Integer>();
    
    @SuppressWarnings("rawtypes")
	@Override
    public void prepare(Map stormConf, TopologyContext context,
    		OutputCollector collector) {
    	this.collector = collector;
    }      
    
	@Override
	public void execute(Tuple input) {
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for(String word : words){
			word = word.trim();
			if(!word.isEmpty()){
				word = word.toLowerCase();
				//Emit the word
				collector.emit(new Values(word));
			}
		}
	}
	
	@Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
	
    @Override
    public void cleanup() {
    }
	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
