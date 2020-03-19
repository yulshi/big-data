package com.example.streaming.wordcount

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.receiver.Receiver

/**
 * @author yulshi
 * @create 2020/03/15 21:38
 */
object WordCountCustomReceiver {

  def main(args: Array[String]): Unit = {

    // 初始化StreamingContext
    val sparkConf = new SparkConf().setAppName("WordCount").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val lineDStream = ssc.receiverStream(new CustomReceiver("localhost", 9999))

//    lineDStream.saveAsTextFiles()


  }

}

class CustomReceiver(hostname: String, port: Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {

  override def onStart(): Unit = {

    new Thread("CustomReceiver") {
      override def run(): Unit = {
        receive();
      }
    }.start()

    def receive(): Unit = {
      var socket: Socket = null;
      var reader: BufferedReader = null;

      try {

        socket = new Socket(hostname,port)
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
        var line:String = reader.readLine();

        while(line !=null) {
          store(line)
          line = reader.readLine()
        }

      }catch {
        case exception: Exception => {
          reader.close();
          socket.close();
          restart("restarting due to " + exception)
        }
      }
    }
  }

  override def onStop(): Unit = {

  }
}
