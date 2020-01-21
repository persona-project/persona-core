package org.persona.core.offline.job

import java.util

import org.apache.spark.sql.{Column, DataFrame, Dataset, Row, SaveMode}
import org.persona.core.offline.util.{DatasetUtil, GroupUtil}

object Counting {
  private val userField: Array[String] = Array("sex", "birth", "area")
  private val replyField: Array[String] = Array("anonymous", "countVote", "countComment", "replyTime", "deleted", "tagAgree", "tagTop", "activeFlag")

  def countForUser(): Unit = {
    for (group <- GroupUtil.map.keys; column <- userField)
      Counting.count(
        DatasetUtil.userDataset.toDF,
        group, "user", column
      )
  }

  def countForReply(): Unit = {
    for (group <- GroupUtil.map.keys; column <- replyField) {
      try {
        Counting.count(
          DatasetUtil.replyWithUser,
          group, "reply", column
        )
      } catch {
        case e: Exception => {
          e.printStackTrace()
          println(s"$group - $column")
          System.exit(1)
        }
      }
    }
  }

  def countAsDataFrame(dataFrame: DataFrame, group: String, column: String): DataFrame = dataFrame
    .filter(GroupUtil.getGroupFilter(group))
    .groupBy(column)
    .count()
    .na.fill("unknown")
    .na.drop

  def count(dataFrame: DataFrame, group: String, table: String, column: String): Unit = dataFrame
    .filter(GroupUtil.getGroupFilter(group))
    .groupBy(column)
    .count()
    .na.fill("unknown")
    .na.drop
    .write
    .format("org.apache.spark.sql.redis")
    .option("table", s"$group:$table:$column")
    .option("key.column", column)
    .mode(SaveMode.Overwrite)
    .save()

}
