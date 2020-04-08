package com.example.orderpay.detect

import java.util

import com.example.orderpay.detect.OrderTimeout.{OrderEvent, OrderResult}
import org.apache.flink.cep.{PatternSelectFunction, PatternTimeoutFunction}
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author yulshi
 * @create 2020/04/06 12:05
 */
object OrderTimeout {

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
      .assignAscendingTimestamps(_.eventTime * 1000)
      .keyBy(_.orderId)

    // 定义一个pattern
    val orderPayPattern = Pattern
      .begin[OrderEvent]("begin").where(_.eventType == "create")
      .followedBy("follow").where(_.eventType == "pay")
      .within(Time.minutes(15))

    // 把模式应用到stream上，得到一个PatternSTream
    val patternStream = CEP.pattern(orderEventStream, orderPayPattern)

    // 调用select方法，提取事件，超时的事件要做报警提示
    val outputTag = new OutputTag[OrderResult]("order-timeout")

    val resultStream = patternStream.select(outputTag, new OrderTimeoutSelect(), new OrderPaySelect())

    resultStream.print("正常支付")

    resultStream.getSideOutput(outputTag).print("支付超时")

    env.execute("Order timeout job")

  }

  // 输入
  case class OrderEvent(orderId: Long, eventType: String, transId: String, eventTime: Long)

  // 输出
  case class OrderResult(orderId: Long, msg: String)


  class OrderTimeoutSelect() extends PatternTimeoutFunction[OrderEvent, OrderResult] {
    override def timeout(map: util.Map[String, util.List[OrderEvent]], l: Long): OrderResult = {
      val timeoutOrderId = map.get("begin").iterator().next().orderId
      OrderResult(timeoutOrderId, "timeout")
    }
  }

}

class OrderPaySelect() extends PatternSelectFunction[OrderEvent, OrderResult] {
  override def select(map: util.Map[String, util.List[OrderEvent]]): OrderResult = {
    val payedOrderId = map.get("follow").iterator().next().orderId
    OrderResult(payedOrderId, "Payed successfully")
  }
}