package org.persona.core.realtime.entity

import java.sql.Date
import java.text.SimpleDateFormat


case class Log(time: String, loginType: String, version: String, hostname: String,
               characterSet: String, screenResolution: String, screenColor: String,
               language: String, flashVersion: String, operationSystem: String,
               browserType: String, browserVersion: String, ip: String,
               region: String, searchKeyword: String, searchEngine: String,
               url: String, referer: String)

object Log {

  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd")

  private def convertTimestampToString(timestamp: String) =
    dateFormat.format(new Date(Integer.parseInt(timestamp.substring(0, 9))))

  def getFieldPair(log: Log): Array[(String, String)] = classOf[Log].getDeclaredFields.map{ getter =>
    getter.setAccessible(true)
    (getter.getName, getter.get(log).toString)
  }

  def fromLine(line: String): Log = {
    val info: Array[String] = line.split("\t")

    Log(
      convertTimestampToString(info(0)),
      info(1),
      info(3),
      info(5),
      info(6),
      info(7),
      info(8),
      info(9),
      info(10),
      info(13),
      info(14),
      info(15),
      info(21),
      info(22),
      info(28),
      info(29),
      info(39),
      info(40)
    )
  }

}
