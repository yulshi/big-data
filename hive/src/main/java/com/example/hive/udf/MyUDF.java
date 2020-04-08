package com.example.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 @author yulshi
 @create 2020/03/23 18:21
 */
public class MyUDF extends UDF {

  public String evaluate(String in) {

    if (in == null) return null;

    return in.toLowerCase();

  }
}
