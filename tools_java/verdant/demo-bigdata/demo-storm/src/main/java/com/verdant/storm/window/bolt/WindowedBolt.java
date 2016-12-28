package com.verdant.storm.window.bolt;

import java.util.HashMap;
import java.util.Map;

import com.verdant.storm.window.SlidingWindowCache;
import org.apache.storm.Config;
import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WindowedBolt extends BaseRichBolt {

    private static final long serialVersionUID = 8849434942882466073L;

    private final Logger logger = LoggerFactory.getLogger(WindowedBolt.class);

    private final static int DEFAULT_WINDOW_LEN_IN_SECS = 12;
    private final static int DEFAULT_WINDOW_EMIT_FREQ = 4;

    private int windowLengthInSeconds;
    private int emitFrequencyInSeconds;

    protected SlidingWindowCache<Tuple> cache;


    public WindowedBolt() {
        this(DEFAULT_WINDOW_LEN_IN_SECS, DEFAULT_WINDOW_EMIT_FREQ);
    }

    public WindowedBolt(int windowLenInSecs, int emitFrequency) {
        if (windowLenInSecs % emitFrequency != 0) {
            logger.warn(String.format("Actual window length(%d) isnot emitFrequency(%d)'s times", windowLenInSecs, emitFrequency));
        }
        this.windowLengthInSeconds = windowLenInSecs;
        this.emitFrequencyInSeconds = emitFrequency;
        cache = new SlidingWindowCache<>(getSlots(this.windowLengthInSeconds, this.emitFrequencyInSeconds));
    }


    @Override
    public void execute(Tuple tuple) {
        if (isTickTuple(tuple)) {
            logger.info("====>Received tick tuple, triggering emit of current window counts");
            emitCurrentWindowCounts();
        } else {
            emitNormal(tuple);
        }
    }

    private void emitNormal(Tuple tuple) {
        cache.add(tuple);
    }

    private int getSlots(int windowLenInSecs, int emitFrequency) {
        return windowLenInSecs / emitFrequency;
    }

    public abstract void prepare(Map stormConf, TopologyContext context, OutputCollector collector);

    public abstract void declareOutputFields(OutputFieldsDeclarer declarer);

    public abstract void emitCurrentWindowCounts();

    private static boolean isTickTuple(Tuple tuple) {
        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
                && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        Map<String, Object> conf = new HashMap<>();
        conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, emitFrequencyInSeconds);
        return conf;
    }

}
