package com.showtime.xijing.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * 对发布的内容进行校验，如果重复度过高则不允许发布
 * <p>
 * Create with IntelliJ IDEA
 * User: Indira
 * Date: 2018/1/12
 * Time: 17:34
 **/
@Slf4j
public class MyLevenshtein {

    /*public static void main(String[] args) {
        //要比较的两个字符串
        String str1 = "今天星期四";
        String str2 = "星期四是今天";
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            levenshtein(str1, str2);
        }
        long t2 = System.currentTimeMillis();
        System.out.println(" 耗费时间： " + (t2 - t1) + "  ms ");
    }*/

    /**
     * DNA分析 　　拼字检查 　　语音辨识 　　抄袭侦测
     *
     * @param str1 字符串1
     * @param str2 字符串2
     */
    public static void levenshtein(String str1, String str2) {
        //计算两个字符串的长度。
        int len1 = str1.length();
        int len2 = str2.length();
        //建立上面说的数组，比字符长度大一个空间
        int[][] dif = new int[len1 + 1][len2 + 1];
        //赋初值，步骤B。
        for (int a = 0; a <= len1; a++) {
            dif[a][0] = a;
        }
        for (int a = 0; a <= len2; a++) {
            dif[0][a] = a;
        }
        //计算两个字符是否一样，计算左上的值
        int temp;
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                //取三个值中最小的
                dif[i][j] = min(dif[i - 1][j - 1] + temp, dif[i][j - 1] + 1, dif[i - 1][j] + 1);
            }
        }
        //取数组右下角的值，同样不同位置代表不同字符串的比较
        log.info("字符串 \"" + str1 + "\" 与 \"" + str2 + "\" 的比较  " + "--> 差异步骤：" + dif[len1][len2]);
        //计算相似度
        float similarity = 1 - (float) dif[len1][len2] / Math.max(str1.length(), str2.length());
        log.info("相似度：" + similarity);
    }

    //得到最小值
    private static int min(int... is) {
        int min = Integer.MAX_VALUE;
        for (int i : is) {
            if (min > i) {
                min = i;
            }
        }
        return min;
    }


}
