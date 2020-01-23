package org.persona.core.offline.job

import java.util

import org.apache.spark.sql.{Column, DataFrame, Dataset, Row, SaveMode}
import org.persona.core.offline.util.{DatasetUtil, GroupUtil}
import org.apache.spark.sql.functions._

object Counting {
  private val userFields: Array[String] =
    Array("sex", "birth", "area")
  private val replyFields: Array[String] =
    Array("anonymous", "countVote", "countComment", "replyTime", "deleted", "tagAgree", "tagTop", "activeFlag")
  private val postFields: Array[String] =
    Array("anonymous", "countVote", "countReply", "countBrowse", "postTime","lastReplyTime",  "deleted", "tagSolve",
      "tagAgree", "tagLector", "tagTop", "activeFlag")
  private val commentFields: Array[String] =
    Array("anonymous", "countVote", "commentTime", "deleted", "tagAgree", "tagTop", "activeFlag")

  def countForUser(): Unit =
    for (group <- GroupUtil.map.keys; column <- userFields)
      Counting.count(
        DatasetUtil.userDataset.toDF,
        group, "user", column
      )

  def countForReply(): Unit =
    for (group <- GroupUtil.map.keys; column <- replyFields)
      Counting.count(
        DatasetUtil.replyWithUser,
        group, "reply", column
      )

  def countForPost(): Unit =
    for (group <- GroupUtil.map.keys; column <- postFields)
      Counting.count(
        DatasetUtil.postWithUser,
        group, "post", column
      )

  def countForComment(): Unit =
    for (group <- GroupUtil.map.keys; column <- commentFields)
      Counting.count(
        DatasetUtil.commentWithUser,
        group, "comment", column
      )

  def countForAgeField(): Unit =
    for (group <- GroupUtil.map.keys)
      DatasetUtil.userDataFrame
        .filter(GroupUtil.getGroupFilter(group))
        .groupBy(floor(months_between(current_date, col("birth")).divide(12)).alias("age"))
        .count
        .na.fill("unknown")
        .na.drop
        .write
        .format("org.apache.spark.sql.redis")
        .option("table", s"$group:user:age")
        .option("key.column", "age")
        .mode(SaveMode.Overwrite)
        .save

  private def count(dataFrame: DataFrame, group: String, table: String, column: String): Unit = dataFrame
    .filter(GroupUtil.getGroupFilter(group))
    .groupBy(column)
    .count
    .na.fill("unknown")
    .na.drop
    .write
    .format("org.apache.spark.sql.redis")
    .option("table", s"$group:$table:$column")
    .option("key.column", column)
    .mode(SaveMode.Overwrite)
    .save

}
