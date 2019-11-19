package com.ido.leetcode;

import java.util.Stack;

/**
 *
 *
 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

 有效字符串需满足：

 左括号必须用相同类型的右括号闭合。
 左括号必须以正确的顺序闭合。
 注意空字符串可被认为是有效字符串。



 * @author xu.qiang
 * @date 19/11/15
 */
public class LeetCode20 {

    public static void main(String[] args) {


        System.out.println(isValid("{[]}"));

    }


    public static boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char a : chars) {

            if(stack.isEmpty()){
                stack.push(a);
            }else {

                //和上一个是一对 弹出来

                if(isPair(stack.peek(),a)){
                    stack.pop();
                }else {

                    //压如堆栈
                    stack.push(a);
                }

            }
        }

        return stack.isEmpty();

    }

    private static boolean isPair(char c1, char c2) {
        return (c1 == '(' && c2 == ')') || (c1 == '[' && c2 == ']') || (c1 == '{' && c2 == '}');
    }




}
