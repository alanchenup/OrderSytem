package com.ordersystem.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	 private static String EMPTY=" ";

	/**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * <p>Joins the elements of the provided array into a single String
     * containing the provided list of elements.</p>
     *
     * <p>No delimiter is added before or after the list.
     * A <code>null</code> separator is the same as an empty String ("").
     * Null objects or empty strings within the array are represented by
     * empty strings.</p>
     *
     * <pre>
     * StringUtils.join(null, *)                = null
     * StringUtils.join([], *)                  = ""
     * StringUtils.join([null], *)              = ""
     * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
     * StringUtils.join(["a", "b", "c"], null)  = "abc"
     * StringUtils.join(["a", "b", "c"], "")    = "abc"
     * StringUtils.join([null, "", "a"], ',')   = ",,a"
     * </pre>
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use, null treated as ""
     * @param startIndex the first index to start joining from.  It is
     * an error to pass in an end index past the end of the array
     * @param endIndex the index to stop joining from (exclusive). It is
     * an error to pass in an end index past the end of the array
     * @return the joined String, <code>null</code> if null array input
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = EMPTY;
        }

        // endIndex - startIndex > 0:   Len = NofStrings *(len(firstString) + len(separator))
        //           (Assuming that all Strings are roughly equally long)
        int bufSize = (endIndex - startIndex);
        if (bufSize <= 0) {
            return EMPTY;
        }

        bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length())
                        + separator.length());

        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String[] split(String text, int[] pos) {
        String[] strs = new String[pos.length / 2];
        for(int i = 0; i < pos.length; i += 2) {
            int s = pos[i];
            int e = pos[i + 1];
            if(text.length() < s) strs[i / 2] = "";
            else if(text.length() < e) strs[i / 2] = text.substring(s, text.length()).trim();
            else strs[i / 2] = text.substring(s, e).trim();
        }
        return strs;
    }
    
    /**
     * 不带正则表达式的字符串切割，用于切割的符号可以是复杂符号也可以是简单符号；
     *
     * @param strinput
     * @param sp       切割的字符串数组，如果切割失败则返回空数组, 如果传入的字符串为 null 返回 null;
     * @return
     */
    public static String[] Split(String strinput, String sp) {
        String tmp = "";
        int strlen = 0, splen = 0;
        int found = 0;
        String[] rt = new String[]{};
        try {
            if(strinput == null || sp == null || strinput.length() == 0 || sp.length() == 0)
                return null;

            // 初始化一个数组列表（当做动态数组）
            ArrayList<String> tmp3 = new ArrayList<String>();

            strlen = strinput.length();
            splen = sp.length();
            for(int i = 0; i < strlen; i++) {
                // 查找分隔符
                found = strinput.indexOf(sp, i);
                if(found >= 0) {
                    tmp = "";
                    // 取分隔符前的字符串
                    tmp = strinput.substring(i, found);
                    // 添加到数组列表
                    tmp3.add(tmp);
                    i = found + splen - 1;
                } else {
                    String tmp2 = "";
                    // 取最后的字符串
                    tmp2 = strinput.substring(i);
                    if(tmp2 != "")
                        tmp3.add(tmp2);
                    break;
                }
            }

            // 将动态数组的维数设置成实际存在的元素个数，因为数组列表是以16的倍数递增维数的
            tmp3.trimToSize();

            // 转换数组列表为字符串数组，并返回。
            rt = (String[]) tmp3.toArray(new String[0]);
            tmp3.clear();
        } catch(Exception e) {
        	
        }

        return rt;
    }

}
