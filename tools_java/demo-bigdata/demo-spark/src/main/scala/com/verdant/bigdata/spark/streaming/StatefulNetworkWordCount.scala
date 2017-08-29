package com.verdant.bigdata.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming._

/**
  * Created by verdant on 2017/8/20.
  */
object StatefulNetworkWordCount {
  val checkpointPath = "/logs/spark"

  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage StatefulNetworkWordCount <master> <output>")
    }
    val Array(master, output) = args.take(2)

    val conf = new SparkConf().setMaster(master).setAppName("StatefulNetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.checkpoint(checkpointPath)
    val lines = ssc.socketTextStream("localhost", 7777)
    val words = lines.flatMap(_.split(" "))
    val wordDstream = words.map(x => (x, 1))

    // Initial state RDD for mapWithState operation
    val initialRDD = ssc.sparkContext.parallelize(List(("hello", 1), ("world", 1)))

    val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
      val sum = one.getOrElse(0) + state.getOption.getOrElse(0)
      val output = (word, sum)
      state.update(sum)
      output
    }

    val stateDstream = wordDstream.mapWithState(
      StateSpec.function(mappingFunc).initialState(initialRDD))
    stateDstream.print()

    println("StatefulNetworkWordCount: sscstart")
    ssc.start()
    println("StatefulNetworkWordCount: awaittermination")
    ssc.awaitTermination()
    println("StatefulNetworkWordCount: done!")
  }
}
