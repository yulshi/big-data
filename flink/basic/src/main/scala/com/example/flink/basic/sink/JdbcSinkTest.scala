package com.example.flink.basic.sink

import java.sql.{Connection, Driver, DriverManager, PreparedStatement}

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
 * @author yulshi
 * @create 2020/04/01 16:48
 */
object JdbcSinkTest {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputStream = env.readTextFile("/Users/yushi/work/bigdata/flink/basic/src/main/resources/sensor.txt")

    val dataStream = inputStream.map(line => {
      val fields = line.split(",")
      SensorRecording(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })


    dataStream.addSink(new RichSinkFunction[SensorRecording]() {
      var conn: Connection = _
      var insertStmt: PreparedStatement = _
      var updateStmt: PreparedStatement = _

      override def open(parameters: Configuration): Unit = {
        conn = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/acme",
          "root",
          "welcome1")
        insertStmt = conn.prepareStatement("INSERT INTO temperatures(sensor, temperature) VALUES ( ?, ? )")
        updateStmt = conn.prepareStatement("UPDATE temperatures SET temperature=? WHERE sensor=?")
      }

      override def invoke(value: SensorRecording, context: SinkFunction.Context[_]): Unit = {
        updateStmt.setDouble(1, value.temperature)
        updateStmt.setString(2, value.id)
        updateStmt.execute()

        if (updateStmt.getUpdateCount == 0) {
          insertStmt.setString(1, value.id)
          insertStmt.setDouble(2, value.temperature)
          insertStmt.execute()
        }
      }

      override def close(): Unit = {
        conn.close()
      }
    })

    env.execute("sink-jdbc")
  }

}

