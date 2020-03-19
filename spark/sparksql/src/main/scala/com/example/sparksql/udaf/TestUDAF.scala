package com.example.sparksql.udaf

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * @author yulshi
 * @create 2020/03/13 10:49
 */
object TestUDAF {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("TestSQL")
      .getOrCreate()

    import spark.implicits._

    val df = spark.read.json("sparksql/data/people.json")


    df.createTempView("people")
    spark.udf.register("myavg", CustomUDAF)

    val resultDf = spark.sql("select myavg(age) from people")

    resultDf.write.mode(SaveMode.Overwrite).csv("output")

    spark.close()

  }

}
