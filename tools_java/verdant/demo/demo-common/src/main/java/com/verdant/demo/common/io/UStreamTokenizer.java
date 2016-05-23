package com.verdant.demo.common.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

/**
 * Author: verdant
 * Desc:   StreamTokenizer 类使用示例
 */
public class UStreamTokenizer {

    /**
     * 统计文件的字符和数字数目
     * @param filename
     */
    public void countWordsAndNumbers(String filename) {

        StreamTokenizer sTokenizer = null;
        int wordCount = 0, numberCount = 0;

        try {
            sTokenizer = new StreamTokenizer(new FileReader(filename));

            while (sTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
                if (sTokenizer.ttype == StreamTokenizer.TT_WORD)
                    wordCount++;
                else if (sTokenizer.ttype == StreamTokenizer.TT_NUMBER)
                    numberCount++;
            }

            System.out.println("Number of words in file: " + wordCount);
            System.out.println("Number of numbers in file: " + numberCount);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UStreamTokenizer().countWordsAndNumbers("myFile.txt");
    }
}
