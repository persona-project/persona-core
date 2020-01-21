package org.persona.core.offline

import java.time.{Duration, Instant}

import org.persona.core.offline.job.Counting


object Runner extends App {

  // Total: 11076
  val start: Instant = Instant.now
  Counting.countForUser()     // 9268
  Counting.countForReply()    // 495
  Counting.countForPost()     // 1115
  Counting.countForComment()  // 198

  println("Time cost:" + Duration.between(start, Instant.now).getSeconds + "s")
  // about 108s
}
