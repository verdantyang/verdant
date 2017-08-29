package com.verdant.bigdata.spark.wc

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Counts words in UTF8 encoded, '\n' delimited text received from the network every second.
  *
  * Usage: NetworkWordCount <hostname> <port>
  * <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive data.
  *
  * To run this on your local machine, you need to first run a Netcat server
  * `$ nc -lk 9999`
  * and then run the example
  * `$ bin/run-example org.apache.spark.examples.streaming.NetworkWordCount localhost 9999`
  */
object NetworkWordCount {
  def main(args: Array[String]) {
    if (args.length < 3) {
      System.err.println("Usage: NetworkWordCount <hostname> <port> <output>")
      System.exit(1)
    }
    val Array(master, port, output) = args.take(3)
    //    StreamingExamples.setStreamingLogLevels()

    val sparkConf = new SparkConf().setAppName("NetworkWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(10))

    val lines = ssc.socketTextStream(master, port.toInt, StorageLevel.MEMORY_AND_DISK_SER)
    val words = lines.flatMap(_.split(" "))
    val wc = words.map(x => (x, 1)).reduceByKey(_ + _)

    wc.saveAsTextFiles(output)
    wc.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
