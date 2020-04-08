package com.example.flink.basic.processfunc

import com.example.flink.basic.window.SensorReading
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
 * 连续两秒温度上升
 *
 * @author yulshi
 * @create 2020/04/02 16:01
 */
object ProcessFunctionTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val sourceStream = env.socketTextStream("localhost", 7777)

    val dataStream = sourceStream.map(line => {
      val fields = line.split(",")
      SensorReading(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })

    // 连续两秒温度上升，就报警
    dataStream.keyBy(_.id)
      .process(new TemperatureContinuousIncreaseAlert())
      .print("processed data")

    // 如果连续两个温度的差值大于10，则报警
    dataStream.keyBy(_.id)
      .flatMapWithState[(String, Double, Double), Double] {
        case (input: SensorReading, None) => (List.empty, Some(input.temperature))
        case (input: SensorReading, lastTemp: Some[Double]) => {
          val diff = (input.temperature - lastTemp.get).abs
          if (diff > 10.0) {
            (List((input.id, lastTemp.get, input.temperature)), Some(input.temperature))
          } else {
            (List.empty, Some(input.temperature))
          }
        }
      }
      .print("sharp-change-alert")
    env.execute("process function")

  }
}

class TemperatureContinuousIncreaseAlert() extends KeyedProcessFunction[String, SensorReading, String] {

  // 定义一个状态，用来保存上一次的温度
  lazy val lastTemp: ValueState[Double] = getRuntimeContext
    .getState(
      new ValueStateDescriptor[Double]("last-temp", classOf[Double]))

  // 定义一个状态，用来保存定时器的时间戳
  lazy val currentTimer: ValueState[Long] = getRuntimeContext
    .getState(
      new ValueStateDescriptor[Long]("currentTimer", classOf[Long]))

  override def processElement(value: SensorReading,
                              ctx: KeyedProcessFunction[String, SensorReading, String]#Context,
                              out: Collector[String]): Unit = {
    val prevTemp = lastTemp.value()
    lastTemp.update(value.temperature)

    val currTimerTs = currentTimer.value()

    // 温度上升，且没有注册定时器
    if (value.temperature > prevTemp && currTimerTs == 0) {
      val timerTs = ctx.timerService().currentProcessingTime() + 1000;
      ctx.timerService().registerProcessingTimeTimer(timerTs)
      currentTimer.update(timerTs)
    } else if (value.temperature < prevTemp || prevTemp == 0.0) {
      ctx.timerService().deleteProcessingTimeTimer(currTimerTs)
      currentTimer.clear()
    }

  }

  override def onTimer(timestamp: Long,
                       ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext,
                       out: Collector[String]): Unit = {
    out.collect(ctx.getCurrentKey + " 温度连续上升")
    currentTimer.clear()
  }

}
