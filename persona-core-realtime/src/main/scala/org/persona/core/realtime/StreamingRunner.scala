package org.persona.core.realtime

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume._
import org.persona.core.realtime.entity.Log
import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

object StreamingRunner extends App {

  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persona")
  val session: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
  val sparkContext: SparkContext = session.sparkContext
  val streamingContext = new StreamingContext(sparkContext, Seconds(2))

  val inputStream: ReceiverInputDStream[SparkFlumeEvent] = FlumeUtils.createPollingStream(streamingContext, "localhost", 8888)
  val stream: DStream[Log] = inputStream.map(flumeEvent =>
    Log.fromLine(new String(flumeEvent.event.getBody.array(), "utf-8"))
  ).window(Seconds(6), Seconds(2))

  val jedisPoolConfig = new JedisPoolConfig()
  val jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 2000, "redis")
  val jedis: Jedis = jedisPool.getResource

  stream
    .flatMap(Log.getFieldPair)
    .map(t => (s"all:log:${t._1}:${t._2}", 1))
    .reduceByKey(_+_)
    .foreachRDD(rdd => rdd.foreach{
      case (key, count) => {
        jedis.hsetnx(key, "count", count.toString)
        jedis.expire(key, 6)
        println(key, count)
      }
    })

  streamingContext.start()
  streamingContext.awaitTermination()
}
