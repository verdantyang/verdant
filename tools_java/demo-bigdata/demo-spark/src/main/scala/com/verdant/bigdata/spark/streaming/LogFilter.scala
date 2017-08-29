package com.verdant.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * LogFilter：日志过滤器
  */
object LogFilter {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("StreamingLogInput")
    val ssc = new StreamingContext(conf, Seconds(10))

    // Create a DStream from all the input on port 7777
    val lines = ssc.socketTextStream("localhost", 7777)
    val errorLines = processLines(lines)
    errorLines.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

  // Filter our DStream for lines with "error"
  private def processLines(lines: DStream[String]) = {
    lines.filter(_.contains("error"))
  }
}
