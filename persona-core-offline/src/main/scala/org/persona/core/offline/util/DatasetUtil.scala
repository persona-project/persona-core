package org.persona.core.offline.util

import com.google.common.base.CaseFormat
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, DataFrameReader, Dataset, SparkSession}
import org.persona.core.offline.entity.{Comment, Post, Reply, User}

object DatasetUtil extends App {
  /** jdbc configuration */
  var url: String = "jdbc:mysql://localhost:3306/persona"
  var username: String = "root"
  var password: String = "1234"

  /** spark session */
  private val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persona")
  private val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  private val dataFrameReader: DataFrameReader = session.read.format("jdbc")
    .option("url", url)
    .option("user", username)
    .option("password", password)

  /** Dataset loading */
  import session.implicits._
  val userDataset: Dataset[User] = camelDataFrame("User").as[User]
  // val replyDataset: Dataset[Reply] = camelDataFrame("Reply").as[Reply]
  // val postDataset: Dataset[Post] = camelDataFrame("Post").as[Post]
  // val commentDataset: Dataset[Comment] = camelDataFrame("Comment").as[Comment]

  /**
   * Load the table using camel field name.
   * @param table
   * @return
   */
  private def camelDataFrame(table: String): DataFrame = {
    def underscoresToCamel(str: String): String =
      CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str)

    var df: DataFrame = dataFrameReader.option("dbtable", table).load()
    for (name <- df.columns)
      df = df.withColumnRenamed(name, underscoresToCamel(name))
    df
  }

  userDataset
    .filter($"id" < 464060)
    .show()
}
