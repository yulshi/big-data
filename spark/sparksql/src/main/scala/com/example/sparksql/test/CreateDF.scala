package com.example.sparksql.test

import org.apache.spark.sql.types.{IntegerType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import sun.text.normalizer.UCharacter.NumericType

/**
 * @author yulshi
 * @create 2020/03/13 09:05
 */
object CreateDF {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("CreateDF")
      .getOrCreate()

    import spark.implicits._

    // 创建RDD
    val sc = spark.sparkContext
    val rdd = sc.parallelize(1 to 4,2)

    // 转换为RDD[Row]
    val rddRow = rdd.map(Row(_))

    // 获取schema
    val structType = StructType(Array(StructField("id", IntegerType, true)))

    val df = spark.createDataFrame(rddRow, structType)

    df.show()

    spark.stop()

  }

}
