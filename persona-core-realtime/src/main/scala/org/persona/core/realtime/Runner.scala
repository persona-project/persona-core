package org.persona.core.realtime

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume._

object Runner extends App {

  val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persona")
  val streamingContext: StreamingContext = new StreamingContext(sparkConf, Seconds(5))

  val inputStream: ReceiverInputDStream[SparkFlumeEvent] = FlumeUtils.createPollingStream(streamingContext, "localhost", 8888)

  val stream: DStream[String] = inputStream.flatMap(flumeEvent =>
    new String(flumeEvent.event.getBody.array(), "utf-8").split(" ")
  )

  stream.print

  streamingContext.start()
  streamingContext.awaitTermination()
}
