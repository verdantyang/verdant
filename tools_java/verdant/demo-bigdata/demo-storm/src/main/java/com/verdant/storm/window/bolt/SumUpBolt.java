package com.verdant.storm.window.bolt;

import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SumUpBolt extends WindowedBolt {

    private static final long serialVersionUID = 8849434942882466073L;

    private final Logger logger = LoggerFactory.getLogger(SumUpBolt.class);

    private OutputCollector collector;

    public SumUpBolt(int windowLenInSecs, int emitFrequency) {
        super(windowLenInSecs, emitFrequency);
    }

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sum"));
    }

    @Override
    public void emitCurrentWindowCounts() {
        int sum = 0;
        List<Tuple> windowedTuples = cache.getAndAdvanceWindow();
        Values val = new Values();
        if (windowedTuples != null && windowedTuples.size() != 0) {
            for (Tuple t : windowedTuples) {
                List<Object> objs = t.getValues();
                val.addAll(t.getValues());
                if (objs != null && objs.size() > 0) {
                    for (Object obj : objs) {
                        int tmp = Integer.parseInt(obj.toString());
                        sum += tmp;
                    }
                }
            }
            logger.info("array to sum up:  " + val.toString());
            collector.emit(new Values(sum + ""));
        }
    }
}
