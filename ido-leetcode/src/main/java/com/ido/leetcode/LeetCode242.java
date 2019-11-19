package com.ido.leetcode;

/**
 * 给定两个字符串，编写一个函数判断这两个字符串是否是字母异位词
 * <p>
 * <p>
 * 字母异位词的意思就是 字符是不是都相等
 *
 * @author xu.qiang
 * @date 19/11/15
 */
public class LeetCode242 {

    public static void main(String[] args) {

        System.out.println((int) 'a');//97
        System.out.println((int) 'A');//65

        //一共26个大小写字母


        System.out.println(isTrue("anagram", "nagaram"));
        System.out.println(isTrue("nameABCDEFG", "anmeGFEDCBA"));


    }


    public static boolean isTrue(String s0, String s1) {

        char[] so_chars = s0.toCharArray();
        char[] s1_chars = s1.toCharArray();


        if (so_chars.length != s1_chars.length) {
            return false;
        }

        //26个小写 26个大写
        char[] total = new char[26 * 2];


        //存在的字符位置+1
        for (char so_char : so_chars) {
            total[(int) so_char - 65] += 1;
        }


        //存在的字符位置-1
        for (char s1_char : s1_chars) {
            total[(int) s1_char - 65] -= 1;
        }

        for (char c : total) {
            if ((int) c != 0) {
                return false;
            }
        }

        return true;

    }
}
