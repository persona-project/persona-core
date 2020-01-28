package org.persona.core.realtime

import org.persona.core.realtime.job.LogCounting
import org.persona.core.realtime.util.JedisUtil
import org.persona.core.realtime.util.StreamingUtil.streamingContext
import redis.clients.jedis.Jedis

object StreamingRunner extends App {

  LogCounting.countingHourly()
  LogCounting.countingSemidiurnal()
  LogCounting.countingDaily()

  streamingContext.start()
  streamingContext.awaitTermination()
}
