package org.persona.core.offline

import java.time.{Duration, Instant}

import org.persona.core.offline.job.Counting


object Runner {

  def main(args: Array[String]): Unit = {
    // Total: 11326
    val start: Instant = Instant.now
    Counting.countForUser()     // 9268
    Counting.countForAgeField() // 250
    Counting.countForReply()    // 495
    Counting.countForPost()     // 1115
    Counting.countForComment()  // 198

    println("Time cost:" + Duration.between(start, Instant.now).getSeconds + "s")
    // about 116s
  }
}
