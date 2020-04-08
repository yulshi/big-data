package com.example.loginfaildetect

import org.apache.flink.api.common.state.ListStateDescriptor
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
 * @author yulshi
 * @create 2020/04/06 08:27
 */
object LoginFail {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val resource = getClass.getResource("/login-log.csv")

    val loginEventStream = env.readTextFile(resource.getPath)
      .map(line => {
        val fields = line.split(",")
        LoginEvent(fields(0).trim, fields(1).trim, fields(2).trim, fields(3).trim.toLong)
      })
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[LoginEvent](Time.seconds(5)) {
        override def extractTimestamp(element: LoginEvent): Long = element.eventTime * 1000
      })

    val warningStream = loginEventStream
      .keyBy(_.userId)
      .process(new LoginWarning(2))

    warningStream.print()

    env.execute("marketing by channel")
  }


}

// 输入
case class LoginEvent(userId: String, ip: String, eventType: String, eventTime: Long)

// 输出
case class Warning(userId: String, firstFailTime: Long, lastFailTime: Long, msg: String)

class LoginWarning(maxCount: Int) extends KeyedProcessFunction[String, LoginEvent, Warning] {

  // 定义状态，保存2秒内所有失败登录的事件
  lazy val loginFailState = getRuntimeContext.getListState[LoginEvent](new ListStateDescriptor[LoginEvent]("login-fail-stage", classOf[LoginEvent]))

  override def processElement(value: LoginEvent,
                              ctx: KeyedProcessFunction[String, LoginEvent, Warning]#Context,
                              out: Collector[Warning]): Unit = {

    //    // 判断类型是否为fail
    //    if (value.eventType == "fail") {
    //
    //      if (!loginFailState.get().iterator().hasNext) {
    //        ctx.timerService().registerEventTimeTimer(value.eventTime * 1000 + 2000)
    //      }
    //      loginFailState.add(value)
    //
    //    } else {
    //      loginFailState.clear()
    //    }

    if (value.eventType == "fail") {
      // 判断之前是否有登录失败事件
      val iter = loginFailState.get().iterator()
      if (iter.hasNext) {
        // 如果已经有登录失败事件，就比较事件时间是否在2秒以内
        val firstFail = iter.next()
        if (value.eventTime < firstFail.eventTime + 2) {
          // 如果两次间隔小于2秒，输出报警
          out.collect(Warning(value.userId, firstFail.eventTime, value.eventTime, "Login failed in 2 seconds"))
        }
        loginFailState.clear()
        loginFailState.add(value)
      } else {
        loginFailState.add(value)
      }
    } else {
      loginFailState.clear()
    }

  }

  //  override def onTimer(timestamp: Long,
  //                       ctx: KeyedProcessFunction[String, LoginEvent, Warning]#OnTimerContext,
  //                       out: Collector[Warning]): Unit = {
  //
  //    import scala.collection.mutable.ListBuffer
  //    val allFailedEvents = new ListBuffer[LoginEvent]()
  //    val iter = loginFailState.get().iterator()
  //    while (iter.hasNext) {
  //      allFailedEvents.append(iter.next())
  //    }
  //
  //    if (allFailedEvents.length >= maxCount) {
  //      out.collect(Warning(ctx.getCurrentKey, allFailedEvents.head.eventTime, allFailedEvents.last.eventTime,
  //        "login failed for " + allFailedEvents.length + " times in 2 seconds"))
  //    }
  //
  //    loginFailState.clear()
  //
  //  }

}
