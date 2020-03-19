package org.example.test;

import org.apache.hadoop.util.StringUtils;

/**
 * @author yulshi
 * @create 2020/02/12 10:16
 */
public class TestString {

    public static void main(String[] args) {

        String str = "hello,wolrd\\,!!!";
        StringBuilder sb = new StringBuilder();
        int next = StringUtils.findNext(str, ',', '\\', 6, sb);
        System.out.println(sb.toString());
    }
}
