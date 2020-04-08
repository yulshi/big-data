package com.example.loginfaildetect

import java.util

import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.CEP
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * @author yulshi
 * @create 2020/04/06 11:02
 */
object LoginFailWithCEP {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    val resource = getClass.getResource("/login-log.csv")

    // 读取数据流，创建简单事件流
    val loginEventStream = env.readTextFile(resource.getPath)
      .map(line => {
        val fields = line.split(",")
        LoginEvent(fields(0).trim, fields(1).trim, fields(2).trim, fields(3).trim.toLong)
      })
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[LoginEvent](Time.seconds(5)) {
        override def extractTimestamp(element: LoginEvent): Long = element.eventTime * 1000
      })
      .keyBy(_.userId)

    // 定义模式
    val loginFailPattern = Pattern
      .begin[LoginEvent]("begin").where(_.eventType == "fail")
      .next("next").where(_.eventType == "fail")
      .within(Time.seconds(2))

    // 在事件流上应用模式
    val patternStream = CEP.pattern(loginEventStream, loginFailPattern)

    // 从patternStream上应用select function，检出匹配事件
    val loginFailDataStream = patternStream
      .select(new LoginFailMatch())

    loginFailDataStream.print()

    env.execute("login fail with CEP")

  }

}

class LoginFailMatch() extends PatternSelectFunction[LoginEvent, Warning] {

  override def select(map: util.Map[String, util.List[LoginEvent]]): Warning = {
    // 从map中按照名称取出对应的事件
    val firstFail = map.get("begin").iterator().next()
    val lastFail = map.get("next").iterator().next()

    Warning(firstFail.userId, firstFail.eventTime, lastFail.eventTime, "login fail")

  }
}
