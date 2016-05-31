package com.verdant.demo.algo.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * Author: verdant
 * Create: 2016/5/30
 * Desc:   一致性哈希算法实现
 * S类封装了机器节点的信息 ，如name、password、ip、port等
 */
public class ConsistHash<S> {
    private static final Logger log = LoggerFactory.getLogger(ConsistHash.class);

    private TreeMap<Long, S> vnodes = new TreeMap<>();   //虚拟节点
    private Map<S, Integer> pnodes = new HashMap<>();   //物理节点

    private static volatile int shardNo = 0;

    private static final int VIRTUAL_NUM = 100; //每个物理节点关联的虚拟节点个数
    private static final String NODE_NAME = "SHARD-%s-NODE-%s"; //节点名称

    public ConsistHash(List<S> shards) {
        init(shards);
    }

    /**
     * 初始化一致性hash环
     */
    private void init(List<S> shards) {
        for (S shard : shards) {
            pnodes.put(shard, shardNo);
            for (int n = 0; n < VIRTUAL_NUM; n++)
                vnodes.put(MurmurHash.hash(String.format(NODE_NAME, this.shardNo, n)), shard);
            shardNo++;
        }
    }

    /**
     * 添加物理节点
     *
     * @param shard
     */
    public void add(S shard) {
        if (pnodes.get(shard) != null)
            return;
        pnodes.put(shard, shardNo);
        for (int n = 0; n < VIRTUAL_NUM; n++)
            vnodes.put(MurmurHash.hash(String.format(NODE_NAME, shardNo, n)), shard);
        shardNo++;
    }

    /**
     * 删除物理节点
     *
     * @param shard
     */
    public void delete(S shard) {
        Integer id = pnodes.get(shard);
        if (id == null)
            return;
        pnodes.remove(shard);
        for (int n = 0; n < VIRTUAL_NUM; n++)
            vnodes.remove(MurmurHash.hash(String.format(NODE_NAME, id, n)));
    }

    /**
     * 根据key的hash值获取物理结点
     *
     * @param key
     * @return
     */
    public S getShardInfo(String key) {
        Long hashKey = MurmurHash.hash(key);
        SortedMap<Long, S> tail = vnodes.tailMap(hashKey); //沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            hashKey = vnodes.firstKey();
        } else
            hashKey = tail.firstKey();
        return tail.get(hashKey); //返回该虚拟节点对应的真实机器节点信息
    }

    public static <T> Map<T, Integer> groupCountList(List<T> list) {
        Map<T, Integer> result = new HashMap<>();
        for (T elem : list) {
            if (result.containsKey(elem))
                result.put(elem, Integer.valueOf(result.get(elem)) + 1);
            else
                result.put(elem, 1);
        }
        return result;
    }

    private static void outputResult(List<String> result) {
        System.out.println(result.getClass().getName());
        Map<String, Integer> map = groupCountList(result);
//        Map<String, Long> map = (Map<String, Long>) result.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
//        map.forEach((k, v) -> System.out.println(k + ": " + v));
        for (String elem : map.keySet()) {
            System.out.println(elem + ": " + map.get(elem));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void main(String[] args) {
        String format = "%s   %s   %s"; //节点名称
        List<Object> shards = new ArrayList();
        shards.add("192.168.0.0-服务器0");
        shards.add("192.168.0.1-服务器1");
        shards.add("192.168.0.2-服务器2");
        shards.add("192.168.0.3-服务器3");
        shards.add("192.168.0.4-服务器4");
        Random ran = new Random();
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        List result3 = new ArrayList();
        ConsistHash<Object> ch = new ConsistHash<Object>(shards);

        for (int i = 0; i < 50; i++) {
            result1.add(ch.getShardInfo(String.valueOf(i)));
        }

        ch.add("192.168.0.5-服务器5");
        for (int i = 0; i < 50; i++) {
            result2.add(ch.getShardInfo(String.valueOf(i)));
        }

        ch.delete("192.168.0.4-服务器4");
        for (int i = 0; i < 50; i++) {
            result3.add(ch.getShardInfo(String.valueOf(i)));
        }
        for (int i = 0; i < 50; i++) {
            System.out.println(String.format(format, result1.get(i), result2.get(i), result3.get(i)));
        }
        outputResult(result1);
        outputResult(result2);
        outputResult(result3);
    }
}
