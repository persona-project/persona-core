package org.persona.core.realtime.job

import org.apache.spark.streaming.Duration
import org.apache.spark.streaming.dstream.DStream
import org.persona.core.realtime.entity.Log
import org.persona.core.realtime.util.{JedisUtil, StreamingUtil}
import redis.clients.jedis.Jedis

object LogCounting {

  private val baseStream: DStream[Log] = StreamingUtil.inputStream.map(flumeEvent =>
    Log.fromLine(new String(flumeEvent.event.getBody.array(), "utf-8"))
  )

  def countingHourly(): Unit = count(StreamingUtil.HOURLY, "hourly")
  def countingSemidiurnal(): Unit = count(StreamingUtil.SEMIDIURNAL, "semidiurnal")
  def countingDaily(): Unit = count(StreamingUtil.DAILY, "daily")

  private def count(duration: Duration, tag: String): Unit = {
    val stream: DStream[Log] = baseStream.window(duration, StreamingUtil.PERIOD)

    stream
      .flatMap(Log.getFieldPair)
      .map(t => (s"$tag:log:${t._1}:${t._2}", 1))
      .reduceByKey(_+_)
      .foreachRDD(rdd => {
        rdd.foreach {
          case (key, count) => {
            println(key, count)

            val jedis: Jedis = JedisUtil.jedisPool.getResource
            jedis.hsetnx(key, "count", count.toString)
            jedis.expire(key, JedisUtil.EXPIRE)
            jedis.close()
          }
        }
      })
  }

}
