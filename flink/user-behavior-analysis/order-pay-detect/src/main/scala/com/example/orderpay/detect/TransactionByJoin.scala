package com.example.orderpay.detect

import com.example.orderpay.detect.OrderTimeout.OrderEvent
import com.example.orderpay.detect.TransactionMatchDetect.getClass
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/07 15:37
 */
object TransactionByJoin {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    // 读取订单数据
    val resource = getClass.getResource("/order-log.csv")
    val orderEventStream = env.readTextFile(resource.getPath)
      .map(line => {
        val fields = line.split(",")
        OrderEvent(fields(0).trim.toLong, fields(1).trim, fields(2).trim, fields(3).trim.toLong)
      })
      .filter(_.transId != "")
      .assignAscendingTimestamps(_.eventTime * 1000)
      .keyBy(_.transId)

    // 支付到账事件流
    val receiptResource = getClass.getResource("/receipt-log.csv")
    val receiptEventStream = env.readTextFile(receiptResource.getPath)
      .map(line => {
        val fields = line.split(",")
        ReceiptEvent(fields(0).trim, fields(1).trim, fields(2).trim.toLong)
      })
      .assignAscendingTimestamps(_.eventTime * 1000)
      .keyBy(_.txId)

    // interval join
    // interal join 不能做侧输出流
    val processedStream = orderEventStream
      .intervalJoin(receiptEventStream)
      .between(Time.seconds(-5), Time.seconds(5))
      .process(new TranactionPayMatchByJoin())

    processedStream.print()

    env.execute("transaction pay match by join")

  }

}

class TranactionPayMatchByJoin() extends ProcessJoinFunction[OrderEvent, ReceiptEvent, (OrderEvent, ReceiptEvent)] {
  override def processElement(left: OrderEvent,
                              right: ReceiptEvent,
                              ctx: ProcessJoinFunction[OrderEvent, ReceiptEvent, (OrderEvent, ReceiptEvent)]#Context,
                              out: Collector[(OrderEvent, ReceiptEvent)]): Unit = {

    out.collect((left, right))

  }
}
