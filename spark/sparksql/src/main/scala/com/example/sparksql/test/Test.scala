package com.example.sparksql.test

import org.apache.spark.sql.SparkSession

/**
 * @author yulshi
 * @create 2020/03/13 08:51
 */
object Test {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("TestSQL")
      .getOrCreate()

    import spark.implicits._

    spark.udf.register("prefix", (x: String) => "name: " + x)

    val df = spark.read.json("sparksql/data/people.json")

    // DSL风格
    df.filter($"age" > 20).show()

    // SQL风格
    df.createTempView("people")

    spark.sql("select * from people where age > 0").show()

    spark.close()

  }

}
