package com.verdant.dm;

import com.miguelfonseca.completely.AutocompleteEngine;
import com.miguelfonseca.completely.text.analyze.tokenize.WordTokenizer;
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer;
import org.junit.Test;

import java.util.List;

/**
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2020-05-13</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class CompletelyTest {
    @Test
    public void searchDict() {
        AutocompleteEngine<SampleRecord> engine = new AutocompleteEngine.Builder<SampleRecord>()
                .setIndex(new SampleAdapter())
                .setAnalyzers(new LowerCaseTransformer(), new WordTokenizer())
                .build();

        engine.add(new SampleRecord("2018汶川"));
        engine.add(new SampleRecord("2019昌平"));
        engine.add(new SampleRecord("2019昌川"));
        List<SampleRecord> recordList = engine.search("昌");
        System.out.println(recordList);
    }
}
