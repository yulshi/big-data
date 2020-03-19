package com.interview.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yulshi
 * @create 2019/11/29 22:12
 */
public class StackDemo {

    public static void main(String[] args) {
        System.out.println(isValidParentheses("([)]"));
    }

    /**
     * 20. Valid Parentheses
     * <p>
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     *
     * @param s
     * @return
     */
    public static boolean isValidParentheses(String s) {

        Deque<Character> stack = new ArrayDeque<>();
        Map<Character, Character> parentheses = new HashMap<>();
        parentheses.put('(', ')');
        parentheses.put('{', '}');
        parentheses.put('[', ']');

        for (char c : s.toCharArray()) {
            if (parentheses.containsKey(c)) {
                stack.offerFirst(c);
            } else {
                Character left = stack.pollFirst();
                if(left == null) {
                    return false;
                }
                if (parentheses.get(left) != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
