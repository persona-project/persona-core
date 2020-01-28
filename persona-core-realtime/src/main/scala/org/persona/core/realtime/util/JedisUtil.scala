package org.persona.core.realtime.util

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

object JedisUtil {

  val EXPIRE: Int = 6

  val jedisPoolConfig = new JedisPoolConfig()
  val jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, 1000, "redis")
  val jedis = jedisPool.getResource

}
