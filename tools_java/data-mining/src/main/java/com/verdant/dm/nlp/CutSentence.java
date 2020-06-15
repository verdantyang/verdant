package com.verdant.dm.nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

/**
 * <p>文件名称：CutSentence.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2018-04-08</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class CutSentence {
    public static void main(String[] args) {
        String line = "测试一下";
        Segment segment = HanLP.newSegment();
        List<Term> termList = segment.seg(line);
        for (Term term : termList) {
            System.out.println(term.toString());
        }
    }
}
