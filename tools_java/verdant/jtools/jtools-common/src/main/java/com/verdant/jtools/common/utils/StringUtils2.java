package com.verdant.jtools.common.utils;


import org.apache.commons.collections.FastHashMap;

import java.io.*;
import java.lang.Character.UnicodeBlock;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串辅助类
 */
public class StringUtils2 {

    /**
     * native character 2 unicode ascill code
     */
    public static String native2ascii(String text) {
        if (text == null)
            return null;

        char[] myBuffer = text.toCharArray();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < myBuffer.length; i++) {
            char c = myBuffer[i];
            UnicodeBlock ub = UnicodeBlock.of(c);

            if (ub == UnicodeBlock.BASIC_LATIN) {
                //英文及数字等
                sb.append(c);
            } else {
                //汉字
                String hexS = Integer.toHexString(c & 0xffff);
                sb.append("\\u");

                if (hexS.length() < 4) {
                    switch (hexS.length()) {
                        case 1:
                            sb.append("000");
                            break;
                        case 2:
                            sb.append("00");
                            break;
                        case 3:
                            sb.append('0');
                    }
                }

                sb.append(hexS.toLowerCase());
            }
        }

        return sb.toString();
    }


    /**
     * 删除字符串中的多余空格。并且把字符串的前后空格删掉。
     * <pre>
     * 例如把"     "变成" "，把制表符'\t'变成" ";
     * </pre>
     */
    public static String squeezeWhiteSpace(String str) {
        if (str == null)
            return null;
        char[] cs = str.toCharArray();

        StringBuffer sb2 = new StringBuffer(cs.length);

        boolean alreadyMeetSpace = true;

        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];

            //whitespace, tab, Chinese whitespace, no-breaking whitespace(ASCII code 160)
            if (c == ' ' || c == '\t' || c == '　' || c == ' ') {
                if (alreadyMeetSpace) {
                    continue;
                } else {
                    sb2.append(' ');
                    alreadyMeetSpace = true;
                }
            } else {
                sb2.append(c);

                alreadyMeetSpace = false;
            }
        }

        //check the last char is not space
        if (alreadyMeetSpace) {
            //perform right-trim()
            if (sb2.length() > 0) {
                sb2.setLength(sb2.length() - 1);
            }
        }

        return sb2.toString();
    }

    public static int toInt(String s, int defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return new Integer(s).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float toFloat(String s, float defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return new Float(s).floatValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 删除数组中相同的元素。<br>
     * 例如数组中元素为a b b c c c,合并重复元素后为a b c
     *
     * @param s 原始数组
     * @return 不含重复元素的数组。
     */
    public static String[] mergeDuplicateArray(String[] s) {
        List list = Arrays.asList(s);
        Set set = new HashSet(list);
        return (String[]) set.toArray(new String[set.size()]);
    }

    /**
     * 检测字符串是否为null，或者trim()以后的长度是否为0。
     */
    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Object obj) {
        if ("String".equals(getType(obj))) {
            return isEmpty(obj.toString());
        }
        if (obj == null) {
            return true;
        }
        return false;

    }

    /**
     * 对象类型判断
     * getType(这里用一句话描述这个方法的作用)
     * (这里描述这个方法适用条件 – 可选)
     *
     * @param t
     * @return String
     * @throws
     * @since 1.0.0
     */
    public static <T> String getType(T t) {
        if (t instanceof String) {
            return "String";
        } else if (t instanceof Integer) {
            return "int";
        } else {
            return " do not know";
        }
    }
//===============================================================


    public static Map readLineFile(String filePath, String fileName, String encoding) {
        Map retMap = new FastHashMap();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath + fileName), encoding));
            String line = "";
            int i = 0;
            while ((line = br.readLine()) != null) {
                retMap.put(i++, line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retMap;
    }



    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */

    public static String byte2hex(byte[] b) {

        String hs = "";

        String stmp = "";

        for (int n = 0; n < b.length; n++) {

            stmp = (Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1)

                hs = hs + "0" + stmp;

            else

                hs = hs + stmp;

        }

        return hs.toUpperCase();

    }


    public static byte[] hex2byte(byte[] b) {

        if ((b.length % 2) != 0)

            throw new IllegalArgumentException("长度不是偶数");

        byte[] b2 = new byte[b.length / 2];

        for (int n = 0; n < b.length; n += 2) {

            String item = new String(b, n, 2);

            b2[n / 2] = (byte) Integer.parseInt(item, 16);

        }

        return b2;
    }

    /***
     * 把一个字符串分离开，并按照key/value形式保存到Map中
     *
     * @param map      字符串中的字符存放在map中
     * @param data     要分离的字符串
     * @param encoding 字符编码
     * @throws UnsupportedEncodingException
     */
    public static void parseParameters(Map map, String data, String encoding)
            throws UnsupportedEncodingException {

        if ((data != null) && (data.length() > 0)) {//将字符串转换为字节数组
            byte[] bytes;
            bytes = data.getBytes();
            parseParameters(map, bytes, encoding);
        }

    }

    /****
     * 将字节数组中的字符分离到map中，该方法支持字符分离的标记:'%',
     * '?','&'
     *
     * @param map      存放分离的字符
     * @param data     分离的字节数组
     * @param encoding 按什么编码方法
     * @throws UnsupportedEncodingException
     */
    public static void parseParameters(Map map, byte[] data, String encoding)
            throws UnsupportedEncodingException {

        if (data != null && data.length > 0) {
            int ix = 0;
            int ox = 0;
            String key = null;
            String value = null;
            while (ix < data.length) {//对分离的字符数组循环
                byte c = data[ix++];
                switch ((char) c) {
                    case '%':
                    case '?':
                    case '&'://当字节数组中的元素，遇到'%','?','&'字符，就创建value字符串
                        value = new String(data, 0, ox, encoding);
                        if (key != null) {
                            putMapEntry(map, key, value);
                            key = null;
                        }
                        ox = 0;
                        break;
                    case '='://当字节数组中的元素，遇到'='字符，那么创建key字符串
                        key = new String(data, 0, ox, encoding);
                        ox = 0;
                        break;
                    case '+'://当字节数组中的元素，遇到'+'，那么就是以' '代替
                        data[ox++] = (byte) ' ';
                        break;
                    default:
                        data[ox++] = c;
                }
            }
            //最后一个截取的字符串，要进行处理
            if (key != null) {
                value = new String(data, 0, ox, encoding);
                putMapEntry(map, key, value);
            }
        }
    }

    /****
     * 把分离的小字符串存放在map中
     *
     * @param map
     * @param name
     * @param value
     */
    private static void putMapEntry(Map map, String name, String value) {
        map.put(name, value);
    }
    //========================================================================

    /**
     * 转换编码 ISO-8859-1到GB2312
     *
     * @param text
     * @return
     */
    public String ISO2GB(String text) {
        String result = "";
        try {
            result = new String(text.getBytes("ISO-8859-1"), "GB2312");
        } catch (UnsupportedEncodingException ex) {
            result = ex.toString();
        }
        return result;
    }

    /**
     * 转换编码 GB2312到ISO-8859-1
     *
     * @param text
     * @return
     */
    public String GB2ISO(String text) {
        String result = "";
        try {
            result = new String(text.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * Utf8URL编码
     *
     * @param s
     * @return
     */
    public String Utf8URLencode(String text) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 0 && c <= 255) {
                result.append(c);
            } else {
                byte[] b = new byte[0];
                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (Exception ex) {
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    result.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return result.toString();
    }

    /**
     * Utf8URL解码
     *
     * @param text
     * @return
     */
    public String Utf8URLdecode(String text) {
        String result = "";
        int p = 0;
        if (text != null && text.length() > 0) {
            text = text.toLowerCase();
            p = text.indexOf("%e");
            if (p == -1) return text;
            while (p != -1) {
                result += text.substring(0, p);
                text = text.substring(p, text.length());
                if (text == "" || text.length() < 9) return result;
                result += CodeToWord(text.substring(0, 9));
                text = text.substring(9, text.length());
                p = text.indexOf("%e");
            }
        }
        return result + text;
    }

    /**
     * utf8URL编码转字符
     *
     * @param text
     * @return
     */
    private String CodeToWord(String text) {
        String result;
        if (Utf8codeCheck(text)) {
            byte[] code = new byte[3];
            code[0] = (byte) (Integer.parseInt(text.substring(1, 3), 16) - 256);
            code[1] = (byte) (Integer.parseInt(text.substring(4, 6), 16) - 256);
            code[2] = (byte) (Integer.parseInt(text.substring(7, 9), 16) - 256);
            try {
                result = new String(code, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result = null;
            }
        } else {
            result = text;
        }
        return result;
    }

    /**
     * 编码是否有效
     *
     * @param text
     * @return
     */
    private boolean Utf8codeCheck(String text) {
        String sign = "";
        if (text.startsWith("%e"))
            for (int i = 0, p = 0; p != -1; i++) {
                p = text.indexOf("%", p);
                if (p != -1)
                    p++;
                sign += p;
            }
        return sign.equals("147-1");
    }

    /**
     * 是否Utf8Url编码
     *
     * @param text
     * @return
     */
    public boolean isUtf8Url(String text) {
        text = text.toLowerCase();
        int p = text.indexOf("%");
        if (p != -1 && text.length() - p > 9) {
            text = text.substring(p, p + 9);
        }
        return Utf8codeCheck(text);
    }


    //========================================================================
    //转换为%E4%BD%A0形式
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    //将%E4%BD%A0转换为汉字
    public static String unescape(String s) {
        StringBuffer sbuf = new StringBuffer();
        int l = s.length();
        int ch = -1;
        int b, sumb = 0;
        for (int i = 0, more = -1; i < l; i++) {
          /* Get next byte b from URL segment s */
            switch (ch = s.charAt(i)) {
                case '%':
                    ch = s.charAt(++i);
                    int hb = (Character.isDigit((char) ch) ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    ch = s.charAt(++i);
                    int lb = (Character.isDigit((char) ch) ? ch - '0'
                            : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
                    b = (hb << 4) | lb;
                    break;
                case '+':
                    b = ' ';
                    break;
                default:
                    b = ch;
            }
          /* Decode byte b as UTF-8, sumb collects incomplete chars */
            if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
                sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
                if (--more == 0)
                    sbuf.append((char) sumb); // Add char to sbuf
            } else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
                sbuf.append((char) b); // Store in sbuf
            } else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
                sumb = b & 0x1f;
                more = 1; // Expect 1 more byte
            } else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
                sumb = b & 0x0f;
                more = 2; // Expect 2 more bytes
            } else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
                sumb = b & 0x07;
                more = 3; // Expect 3 more bytes
            } else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
                sumb = b & 0x03;
                more = 4; // Expect 4 more bytes
            } else /*if ((b & 0xfe) == 0xfc)*/ { // 1111110x (yields 1 bit)
                sumb = b & 0x01;
                more = 5; // Expect 5 more bytes
            }
          /* We don't test if the UTF-8 encoding is well-formed */
        }
        return sbuf.toString();
    }


    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);

        for (i = 0; i < src.length(); i++) {

            j = src.charAt(i);

            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String escape1(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);

        for (i = 0; i < src.length(); i++) {

            j = src.charAt(i);

            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape1(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
}