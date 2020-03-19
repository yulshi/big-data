package com.example.sparkcore.partition

import org.apache.spark.Partitioner

/**
 * @author yulshi
 * @create 2020/03/11 21:26
 */
class MyPartitioner(partitions: Int) extends Partitioner {

  override def numPartitions: Int = partitions

  override def getPartition(key: Any): Int = 0
}
