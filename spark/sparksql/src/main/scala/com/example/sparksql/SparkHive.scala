package com.example.sparksql

import org.apache.spark.sql.SparkSession

/**
 * @author yulshi
 * @create 2020/03/13 16:00
 */
object SparkHive {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("SparkHive")
      .enableHiveSupport()
      .getOrCreate()

    spark.sql("show tables").show()

    spark.close()

  }

}
