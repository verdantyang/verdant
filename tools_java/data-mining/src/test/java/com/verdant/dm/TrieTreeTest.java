package com.verdant.dm;

import com.hankcs.hanlp.collection.AhoCorasick.AhoCorasickDoubleArrayTrie;
import com.hankcs.hanlp.collection.trie.DoubleArrayTrie;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

/**
 * <p>文件名称：HanlpDictTest.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2017-08-29</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class TrieTreeTest {
    @Test
    public void trieTree() throws IOException {
        TreeMap<String, String> kvs = new TreeMap<>();

        kvs.put("央视网", "yangshi");
        kvs.put("人民日", "renminwang");
        kvs.put("人民日报", "renminribao");
        System.out.println("字典词条：" + kvs.size());

        TreeMap<String, String> kvb = new TreeMap<>();
        kvb.put("人民", "renmin");
        DoubleArrayTrie<String> dat = new DoubleArrayTrie<>();
        dat.build(kvs);
        dat.build(kvb);

        System.out.println(dat);
        List<Integer> integerList = dat.commonPrefixSearch("人民日报和央视网");
        for (int index : integerList) {
            System.out.println(dat.get(index));
        }
    }

    @Test
    public void ac() {
        TreeMap<String, String> map = new TreeMap<String, String>();
        String[] keyArray = new String[]
                {
                        "hers",
                        "his",
                        "she",
                        "he"
                };
        for (String key : keyArray)
        {
            map.put(key, key);
        }
        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<>();
        acdat.build(map);
        // Test it
        final String text = "uhers";
        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList = acdat.parseText(text);
        System.out.println(wordList);
    }
}
