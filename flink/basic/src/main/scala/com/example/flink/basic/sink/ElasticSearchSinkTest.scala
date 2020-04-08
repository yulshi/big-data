package com.example.flink.basic.sink

import java.util

import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.apache.http.HttpHost
import org.elasticsearch.client.Requests

/**
 * @author yulshi
 * @create 2020/04/01 16:21
 */
object ElasticSearchSinkTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputStream = env.readTextFile("/Users/yushi/work/bigdata/flink/basic/src/main/resources/sensor.txt")

    val dataStream = inputStream.map(line => {
      val fields = line.split(",")
      SensorRecording(fields(0).trim, fields(1).trim.toLong, fields(2).trim.toDouble)
    })

    val httpHosts = new util.ArrayList[HttpHost]()
    httpHosts.add(new HttpHost("localhost", 9200))

    val esSinkBuilder = new ElasticsearchSink.Builder[SensorRecording](httpHosts, new ElasticsearchSinkFunction[SensorRecording] {
      override def process(element: SensorRecording,
                           runtimeContext: RuntimeContext,
                           requestIndexer: RequestIndexer): Unit = {
        println("Saving data element: " + element)
        // 包装成一个map或者json object
        val json = new util.HashMap[String, String]()
        json.put("sensor-id", element.id)
        json.put("temperature", element.temperature.toString)
        json.put("timestamp", element.timestamp.toString)
        // 创建index request，准备发送数据
        val indexRequest = Requests.indexRequest()
          .index("sensor")
          .source(json)
        //利用requestIndexer发送请求
        requestIndexer.add(indexRequest)
        println("data saved")
      }
    })

    dataStream.addSink(esSinkBuilder.build())

    env.execute("sink-es")
  }


}
