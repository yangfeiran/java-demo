package com.chinacloud.yangfeiran;


import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class Solution {
    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        String sub;
        int indexOf = 0;
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            for (; j < s.length(); j++) {
                sub = s.substring(i, j + 1);
                result = Math.max(sub.length(), result);
                try {
                    indexOf = sub.indexOf(s.charAt(j + 1));
                } catch (Exception e) {
                    break;
                }
                if (indexOf == -1) {
                } else {
                    break;
                }
            }
        }
        if (s.length() == 1) {
            result = 1;
        }
        System.out.print(result);
        return result;
    }


    public static void main(String[] args) {
//        String s = "b";
        String s = "bbbbb";
//        String s = "ua";
//        String s = "abcabcabc";
        lengthOfLongestSubstring(s);
    }
}