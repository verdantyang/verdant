package com.verdant.dm;

import com.miguelfonseca.completely.data.Indexable;

import java.util.Arrays;
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
public class SampleRecord implements Indexable {
    private final String name;

    public SampleRecord(String name) {
        this.name = name;
    }

    @Override
    public List<String> getFields() {
        return Arrays.asList(name);
    }

    public String getName() {
        return name;
    }
}