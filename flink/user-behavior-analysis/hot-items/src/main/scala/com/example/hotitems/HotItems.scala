package com.example.hotitems

import java.sql.Timestamp
import java.util.Properties

import org.apache.flink.api.common.functions.AggregateFunction
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.state.{ListState, ListStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.util.Collector

import scala.collection.mutable.ListBuffer

/**
 * @author yulshi
 * @create 2020/04/04 08:45
 */
object HotItems {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

    //    val sourceStream = env.readTextFile("/Users/yushi/work/bigdata/flink/user-behavior-analysis/hot-items/src/main/resources/user-behavior.csv")
    val props: Properties = new Properties
    props.put("bootstrap.servers", "localhost:9092")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("group.id", "consumer-group")
    props.put("auto.offset.reset", "latest")

    val sourceStream = env.addSource(new FlinkKafkaConsumer[String]("hot-items", new SimpleStringSchema(), props))

    val dataStream = sourceStream.map(line => {
      val fields = line.split(",")
      UserBehavior(fields(0).trim.toLong, fields(1).trim.toLong, fields(2).trim.toInt, fields(3).trim, fields(4).trim.toLong)
    })
      .assignAscendingTimestamps(_.timestamp * 1000L)

    // 处理数据
    val processeStream = dataStream.filter(_.behavior == "pv")
      .keyBy(_.itemId)
      .timeWindow(Time.hours(1), Time.minutes(5))
      .aggregate(new CountAgg(), new WindowResult())
      .keyBy(_.windowEnd)
      .process(new TopNHotItmes(5))

    processeStream.print()

    env.execute("hot items job")
  }

}

// 定义输入数据的样例类
case class UserBehavior(userId: Long, itemId: Long, categoryId: Int, behavior: String, timestamp: Long)

// 定义窗口聚合样例类
case class ItemViewCount(itemId: Long, windowEnd: Long, count: Long)

class CountAgg() extends AggregateFunction[UserBehavior, Long, Long] {
  override def createAccumulator(): Long = 0

  override def add(value: UserBehavior, accumulator: Long): Long = accumulator + 1

  override def getResult(accumulator: Long): Long = accumulator

  override def merge(a: Long, b: Long): Long = a + b
}

class WindowResult() extends WindowFunction[Long, ItemViewCount, Long, TimeWindow] {
  override def apply(key: Long, window: TimeWindow, input: Iterable[Long], out: Collector[ItemViewCount]): Unit = {
    out.collect(ItemViewCount(key, window.getEnd, input.iterator.next()))
  }
}

class TopNHotItmes(hotCount: Int) extends KeyedProcessFunction[Long, ItemViewCount, String] {

  private var itemsState: ListState[ItemViewCount] = _

  override def open(parameters: Configuration): Unit = {
    itemsState = getRuntimeContext.getListState(new ListStateDescriptor[ItemViewCount]("items-state", classOf[ItemViewCount]))
  }

  override def processElement(value: ItemViewCount,
                              ctx: KeyedProcessFunction[Long, ItemViewCount, String]#Context,
                              out: Collector[String]): Unit = {

    itemsState.add(value)
    ctx.timerService().registerEventTimeTimer(value.windowEnd + 1)

  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[Long, ItemViewCount, String]#OnTimerContext, out: Collector[String]): Unit = {

    val allItems = new ListBuffer[ItemViewCount]

    import scala.collection.JavaConversions._
    for (itemViewCount <- itemsState.get()) {
      allItems += itemViewCount
    }

    itemsState.clear()

    //val topItems = allItems.sortWith((a, b) => a.windowEnd > b.windowEnd).take(hotCount)
    val topItems = allItems.sortBy(_.count)(Ordering.Long.reverse).take(hotCount)

    val stringBuf = new StringBuilder()
    stringBuf.append("Time-End: ").append(new Timestamp(timestamp - 1)).append("\n")
    for (i: Int <- topItems.indices) {
      stringBuf.append("------ " + (i + 1) + " -------\n")
      stringBuf.append(" item id\t").append(topItems(i).itemId)
      stringBuf.append(" count\t").append(topItems(i).count).append("\n")
    }
    stringBuf.append("=================").append("\n")

    Thread.sleep(1000)

    out.collect(stringBuf.toString())

  }
}

// 自定义处理函数topN

