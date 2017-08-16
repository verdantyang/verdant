package com.verdant.bigdata.spark.wc

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by verdant on 2017/8/15.
  */
object FileWordCount {

  def main(args: Array[String]): Unit = {
    System.setProperty("spark.executor.memory", "512m")
    val conf = new SparkConf().setAppName("FileWordCount")
      //            .setMaster("local")
      .setMaster("spark://127.0.0.1:7077")
    val sc = new SparkContext(conf)
    sc.addJar("/Users/verdant/develop/dev_myself/verdant/tools_java/demo-bigdata/demo-spark/target/demo-spark-0.0.1.jar");

    val input = args(0)
    val texts = sc.textFile(input).map(line => line.split(" "))
      .flatMap(words => words.map(word => (word.replaceAll("[^A-Za-z]", ""), 1)))
    val counts = texts.reduceByKey(_ + _)

    counts.collect.foreach {
      case (word, num) =>
        println(word + " " + num.toString)
    }
  }
}
