package com.verdant.dm;

import com.miguelfonseca.completely.IndexAdapter;
import com.miguelfonseca.completely.data.ScoredObject;
import com.miguelfonseca.completely.text.index.FuzzyIndex;
import com.miguelfonseca.completely.text.index.PatriciaTrie;
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton;

import java.util.Collection;

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
public class SampleAdapter implements IndexAdapter<SampleRecord> {
    private FuzzyIndex<SampleRecord> index = new PatriciaTrie<>();

    @Override
    public Collection<ScoredObject<SampleRecord>> get(String token) {
        // Set threshold according to the token length
        double threshold = Math.log(Math.max(token.length() - 1, 1));
        return index.getAny(new EditDistanceAutomaton(token, 0));
    }

    @Override
    public boolean put(String token, SampleRecord value) {
        return index.put(token, value);
    }

    @Override
    public boolean remove(SampleRecord value) {
        return index.remove(value);
    }
}
