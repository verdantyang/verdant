package com.verdant.bigdata.spark.streaming

import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import org.apache.hadoop.mapred.{SequenceFileInputFormat, SequenceFileOutputFormat}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by verdant on 2017/8/20.
  */
object StreamingLogStat {
  def main(args: Array[String]) {
  }

  //  def noState(accessLogsDStream: DStream[String], lines: ReceiverInputDStream[String]) = {
  //    //无状态操作：解析日志并根据IP进行计数
  //    val accessLogDStream = lines.map(line => ApacheAccessLog.parseLogLine(line))
  //    val ipDStream = accessLogsDStream.map(entry => (entry.getIpAddress(), 1))
  //    val ipCountsDStream = ipDStream.reduceByKey((x, y) => x + y)
  //
  //    //无状态操作：流量计数
  //    val ipBytesDStream = accessLogsDStream.map(entry => (entry.getIpAddress(), entry.getContentSize()))
  //    val ipBytesSumDStream = ipBytesDStream.reduceByKey((x, y) => x + y)
  //    val ipBytesRequestCountDStream = ipCountsDStream.join(ipBytesSumDStream)
  //
  //    //无状态操作：RDD transform
  //    val outlierDStream = accessLogsDStream.transform { rdd => processLines(rdd) }
  //  }
  //
  //  def windowState(accessLogsDStream: DStream[String]) = {
  //    //有状态操作：window窗口计数
  //    val accessLogsWindow = accessLogsDStream.window(Seconds(30), Seconds(10))
  //    val windowCounts = accessLogsWindow.count()
  //
  //    //有状态操作：窗口计数
  //    val ipDStream = accessLogsDStream.map { entry => entry.getIpAddress() }
  //    val ipAddressRequestCount = ipDStream.countByValueAndWindow(Seconds(30), Seconds(10))
  //    val requestCount = accessLogsDStream.countByWindow(Seconds(30), Seconds(10))
  //
  //    //有状态操作：每个IP的访问量计数
  //    val ipDStream = accessLogsDStream.map(logEntry => (logEntry.getIpAddress(), 1))
  //    val ipCountDStream = ipDStream.reduceByKeyAndWindow(
  //      { (x, y) => x + y }, // 加上新进入窗口的批次中的元素
  //      { (x, y) => x - y }, // 移除离开窗口的老批次中的元素
  //      Seconds(30), // 窗口时长
  //      Seconds(10)) // 滑动步长
  //
  //    //有状态操作：updateStateByKey跟踪日志消息中各HTTP 响应码的计数
  //    val responseCodeDStream = accessLogsDStream.map(log => (log.getResponseCode(), 1L))
  //    val responseCodeCountDStream = responseCodeDStream.updateStateByKey(updateRunningSum _)
  //  }

  def updateRunningSum(values: Seq[Long], state: Option[Long]) = {
    Some(state.getOrElse(0L) + values.size)
  }

  def processLines(lines: DStream[String]) = {
    // Filter our DStream for lines with "error"
    lines.filter(_.contains("error"))
  }

  //  def input(ssc: StreamingContext, inputDirectory: String) = {
  //    //文本输入源
  //    val logData = ssc.textFileStream(inputDirectory)
  //
  //    //SequenceFile输入源
  //    ssc.fileStream[LongWritable, IntWritable,
  //      SequenceFileInputFormat[LongWritable, IntWritable]](inputDirectory).map {
  //      case (x, y) => (x.get(), y.get())
  //    }
  //  }

  //  def save(ipAddressRequestCount: DStream[String]) = {
  //    //保存为文件
  //    ipAddressRequestCount.saveAsTextFiles("outputDir", "txt")
  //
  //    //保存为SequenceFile
  //    val writableIpAddressRequestCount = ipAddressRequestCount.map {
  //      (ip, count) => (new Text(ip), new LongWritable(count))
  //    }
  //    writableIpAddressRequestCount.saveAsHadoopFiles[
  //      SequenceFileOutputFormat[Text, LongWritable]]("outputDir", "txt")
  //
  //    //将数据存储到外部系统中
  //    ipAddressRequestCount.foreachRDD { rdd =>
  //      rdd.foreachPartition { partition =>
  //        // 打开到存储系统的连接（比如一个数据库的连接）
  //        partition.foreach { item =>
  //          // 使用连接把item存到系统中
  //        }
  //        // 关闭连接
  //      }
  //    }
  //  }
  //
  //驱动程序容错
  def failover(conf: SparkConf, checkpointPath: String) = {
    def createStreamingContext(): StreamingContext = {
      val sc = new SparkContext(conf)
      val ssc = new StreamingContext(sc, Seconds(1))
      ssc.checkpoint(checkpointPath)
      ssc
    }

    val ssc = StreamingContext.getOrCreate(checkpointPath, createStreamingContext)
  }
}
