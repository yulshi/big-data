package com.example.sparksql.udaf

import org.apache.spark.sql.{Row, types}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructField, StructType}

/**
 * @author yulshi
 * @create 2020/03/13 10:31
 */
object CustomUDAF extends UserDefinedAggregateFunction {

  override def inputSchema: StructType = StructType(StructField("input", LongType) :: Nil)

  override def bufferSchema: StructType = StructType(
    StructField("sum", LongType)
      :: StructField("count", LongType)
      :: Nil)

  override def dataType: DataType = DoubleType

  override def deterministic: Boolean = true

  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }

  // 分区内合并数据
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if(!input.isNullAt(0)) {
      buffer(0) = buffer.getLong(0) + input.getLong(0)
    }
    buffer(1) = buffer.getLong(1) + 1L
  }

  // 分区间合并数据
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  // 计算最终数据
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1)
  }

}
