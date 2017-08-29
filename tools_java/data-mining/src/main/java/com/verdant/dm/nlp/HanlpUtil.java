package com.verdant.dm.nlp;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.common.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件名称：HanlpUtil.java</p>
 * <p>文件描述：</p>
 * <p>其他说明： </p>
 * <p>版权所有： 版权所有(C)2016-2099</p>
 * <p>公   司： 新华智云 </p>
 * <p>完成日期：2017-08-29</p>
 *
 * @author yangcongyu@shuwen.com
 * @version 1.0
 */
public class HanlpUtil {
    public static NShortSegment nShortSegment=new NShortSegment();
    String dictDir;
    String stopDir;
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    @PostConstruct
    public  void insertUserDict() throws IOException {
        logger.info("Insert User Dict to HanLp");
        File[] dictFiles=new File(dictDir).listFiles();
        for(File file:dictFiles)
        {
            List<String> words= Files.readLines(file, Charsets.UTF_8);
            addWords(words);
        }
        File[] stopFiles=new File(stopDir).listFiles();
        for(File file:stopFiles)
        {
            List<String> stopWords= Files.readLines(file,Charsets.UTF_8);
            for(String w:stopWords) {
                CoreStopWordDictionary.add(w.toLowerCase());
            }
        }
    }


    public List<Term> getSegmentTerms(String line) {
        List<Term> terms=new ArrayList<>();
        for(String l:line.split("。"))
        {
            terms.addAll( nShortSegment.seg(l));
        }
        return terms;
    }

    public boolean isStopWord(String word) {
        return CoreStopWordDictionary.contains(word.toLowerCase());
    }

    public List<String> toWordList(List<Term> terms) {
        List<String> ret=new ArrayList<>();
        for(Term t:terms)
        {
            ret.add(t.word);
        }
        return ret;
    }

    public void addWord(String l)
    {
        if(!CustomDictionary.contains(l)&&!CoreDictionary.contains(l))
        {
            // System.out.println("add "+l);
            CustomDictionary.add(l,"nz 10");
        }
    }
    public void addWords(List<String> list)
    {
        for(String l:list)
        {
            addWord(l);
        }
    }
    public List<String> getSegmentWords(String line)
    {
        return toWordList(getSegmentTerms(line));
    }

    public String getDictDir() {
        return dictDir;
    }

    public void setDictDir(String dictDir) {
        this.dictDir = dictDir;
    }

    public String getStopDir() {
        return stopDir;
    }

    public void setStopDir(String stopDir) {
        this.stopDir = stopDir;
    }

    public  String getTonkenizedLine(String line)
    {
        List<String> splt=toWordList(getSegmentTerms(line));
        return joinListWord(splt);

    }
    public static String joinListWord(List<String> words)
    {
        return Joiner.on(" ").join(words);
    }

    public static void main(String[] args) throws IOException {
        //HanlpUtil.insertUserDict();
        String str="整治 丁五一 “丁卫东”、收取“入门费”或团队计酬等涉及传销";
        System.out.println(new HanlpUtil().getSegmentTerms(str));
    }
}
