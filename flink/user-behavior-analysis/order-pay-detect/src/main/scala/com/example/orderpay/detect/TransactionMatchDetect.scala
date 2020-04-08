package com.example.orderpay.detect

import com.example.orderpay.detect.OrderTimeout.OrderEvent
import org.apache.flink.api.common.state.ValueStateDescriptor
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.co.CoProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/07 14:09
 */
// 双流合并
object TransactionMatchDetect {

  // 定义测数据输出流tag
  // 在订单里面有这个trans，但是在receipt里面没有的时候的tag
  val unmatchedPays = new OutputTag[OrderEvent]("unmatched-pay")
  // 在receipt里面有trans，但是order里面没有时候的tag
  val unmatchedReceipt = new OutputTag[ReceiptEvent]("unmatched-receipts")

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

    // 将两条流连接起来共同处理
    val processedStream = orderEventStream.connect(receiptEventStream)
      .process(new TransactionPayMatch())

    processedStream.print("matched")
    processedStream.getSideOutput(unmatchedPays).print("unmatched pays")
    processedStream.getSideOutput(unmatchedReceipt).print("unmatched receipt")

    env.execute("trans match")


  }

  class TransactionPayMatch() extends CoProcessFunction[OrderEvent, ReceiptEvent, (OrderEvent, ReceiptEvent)] {

    // 定义状态保存已经到达的订单支付事件和到账事件
    lazy val payState = getRuntimeContext.getState[OrderEvent](new ValueStateDescriptor[OrderEvent]("pay-state", classOf[OrderEvent]))
    lazy val receiptState = getRuntimeContext.getState[ReceiptEvent](new ValueStateDescriptor[ReceiptEvent]("receipte-state", classOf[ReceiptEvent]))

    // 订单支付事件的处理
    override def processElement1(pay: OrderEvent,
                                 ctx: CoProcessFunction[OrderEvent, ReceiptEvent, (OrderEvent, ReceiptEvent)]#Context,
                                 out: Collector[(OrderEvent, ReceiptEvent)]): Unit = {

      // 判断是否有到账事件
      if (receiptState.value() != null) {
        // 如果有，则在主流输出匹配信息
        out.collect((pay, receiptState.value()))
        receiptState.clear()
      } else {
        // 如果没有到账事件
        payState.update(pay)
        ctx.timerService().registerEventTimeTimer(pay.eventTime * 1000 + 5000)
      }

    }

    // 到账事件的处理
    override def processElement2(receipt: ReceiptEvent,
                                 ctx: CoProcessFunction[OrderEvent, ReceiptEvent, (OrderEvent, ReceiptEvent)]#Context,
                                 out: Collector[(OrderEvent, ReceiptEvent)]): Unit = {

      if (payState.value() != null) {
        out.collect((payState.value(), receipt))
        payState.clear()
      } else {
        receiptState.update(receipt)
        ctx.timerService().registerEventTimeTimer(receipt.eventTime * 1000 + 5000)
      }

    }

    override def onTimer(timestamp: Long,
                         ctx: CoProcessFunction[OrderEvent, ReceiptEvent, (OrderEvent, ReceiptEvent)]#OnTimerContext,
                         out: Collector[(OrderEvent, ReceiptEvent)]): Unit = {

      if(payState.value() != null) {
        // receipt没来，输出侧输出流
        ctx.output(unmatchedPays, payState.value())
      }

      if(receiptState.value() != null) {
        ctx.output(unmatchedReceipt, receiptState.value())
      }

      payState.clear()
      receiptState.clear()

    }
  }

}

case class ReceiptEvent(txId: String, payChannel: String, eventTime: Long)
