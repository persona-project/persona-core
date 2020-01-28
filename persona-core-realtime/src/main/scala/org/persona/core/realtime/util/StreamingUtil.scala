package org.persona.core.realtime.util

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Duration, Minutes, Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.flume.{FlumeUtils, SparkFlumeEvent}

object StreamingUtil {

  val HOURLY: Duration = Minutes(60)
  val SEMIDIURNAL: Duration = Minutes(60*6)
  val DAILY: Duration = Minutes(60*12)
  val PERIOD: Duration = Seconds(10)

  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persona")
  val session: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()
  val sparkContext: SparkContext = session.sparkContext
  val streamingContext = new StreamingContext(sparkContext, PERIOD)

  val inputStream: ReceiverInputDStream[SparkFlumeEvent] =
    FlumeUtils.createPollingStream(streamingContext, "localhost", 8888)

}
