package com.verdant.bigdata.spark.streaming

case class ApacheAccessLog(
                            ipAddress: String,
                            clientIdentd: String,
                            userId: String,
                            dataTime: String,
                            method: String,
                            endPoint: String,
                            protocol: String,
                            responseCode: Int,
                            contentSize: Long
                          ) {

}

object ApacheAccessLog {
  // regex
  // 64.242.88.10 - - [   07/Mar/2004:16:05:49 -0800       ]
  // "GET /twiki/bin/edit/Main/Double_bounce_sender?topicparent=Main.ConfigurationVariables HTTP/1.1"
  // 401 12846
  val PARTTERN =
  """^(\S+) (\S+) (\S+) \[([\w:/]+\s[+|-]\d{4})\] "(\S+) (\S+) (\S+)" (\d{3}) (\d+)""".r

  def isValidatelogLine(log: String): Boolean = {
    val res = PARTTERN.findFirstMatchIn(log)
    if (res.isEmpty) {
      false
    } else {
      true
    }

  }

  def parseLogLine(log: String): ApacheAccessLog = {
    val res = PARTTERN.findFirstMatchIn(log)
    if (res.isEmpty) {
      throw new RuntimeException("Cannot parse log line: " + log)
    }
    val m = res.get

    ApacheAccessLog(
      m.group(1),
      m.group(2),
      m.group(3),
      m.group(4),
      m.group(5),
      m.group(6),
      m.group(7),
      m.group(8).toInt,
      m.group(9).toLong
    )
  }
}
