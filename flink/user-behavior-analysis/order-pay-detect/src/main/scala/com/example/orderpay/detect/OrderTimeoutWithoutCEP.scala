package com.example.orderpay.detect

import com.example.orderpay.detect.OrderTimeout.{OrderEvent, OrderResult}
import org.apache.flink.api.common.state.{ListStateDescriptor, ValueStateDescriptor}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/06 13:00
 */
object OrderTimeoutWithoutCEP {

  val orderTimeoutTag = new OutputTag[OrderResult]("order-timeout")

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val source = getClass.getResource("/order-log.csv")

    val orderEventStream = env.readTextFile(source.getPath)
      .map(line => {
        val fields = line.split(",")
        OrderEvent(fields(0).trim.toLong, fields(1).trim, fields(2).trim, fields(3).trim.toLong)
      })
      .assignAscendingTimestamps(_.eventTime * 1000)
      .keyBy(_.orderId)

    val timeoutWarningStream = orderEventStream
      .process(new OrderTimeoutWarning())

    timeoutWarningStream.print()

    env.execute("order timeout without cep")

  }

  class OrderPayMatch() extends KeyedProcessFunction[Long, OrderEvent, OrderResult] {
    override def processElement(value: OrderEvent,
                                ctx: KeyedProcessFunction[Long, OrderEvent, OrderResult]#Context,
                                out: Collector[OrderResult]): Unit = ???
  }

}

class OrderTimeoutWarning() extends KeyedProcessFunction[Long, OrderEvent, OrderResult] {

  //lazy val orderState = getRuntimeContext.getListState[OrderEvent](new ListStateDescriptor[OrderEvent]("order-state", classOf[OrderEvent]))
  lazy val isPayedState = getRuntimeContext.getState[Boolean](new ValueStateDescriptor[Boolean]("ispayed-state", classOf[Boolean]))

  override def processElement(value: OrderEvent,
                              ctx: KeyedProcessFunction[Long, OrderEvent, OrderResult]#Context,
                              out: Collector[OrderResult]): Unit = {
    val payed = isPayedState.value()

    if (value.eventType == "create" && !payed) {
      ctx.timerService().registerEventTimeTimer(value.eventTime * 1000 + (15 * 60 * 1000))
    } else if (value.eventType == "pay") {
      isPayedState.update(true)
    }

  }

  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[Long, OrderEvent, OrderResult]#OnTimerContext,
                       out: Collector[OrderResult]): Unit = {
    if (isPayedState.value()) {
      out.collect(OrderResult(ctx.getCurrentKey, "payed successfully"))
    } else {
      out.collect(OrderResult(ctx.getCurrentKey, "order timeout"))
    }
    isPayedState.clear()
  }
}


