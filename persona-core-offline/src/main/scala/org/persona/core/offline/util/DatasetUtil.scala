package org.persona.core.offline.util

import com.google.common.base.CaseFormat
import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, DataFrameReader, Dataset, SparkSession}
import org.persona.core.offline.entity.{Comment, Post, Reply, User}

object DatasetUtil {
  /** jdbc configuration */
  var mySqlUrl: String = "jdbc:mysql://localhost:3306/persona"
  var mySqlUsername: String = "root"
  var mySqlPassword: String = "1234"

  /** redis configuration */
  var redisHost: String = "localhost"
  var redisPort: String = "6379"
  var redisAuth: String = "redis"

  /** spark session */
  private val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persona")
    .set("spark.redis.host", redisHost)
    .set("spark.redis.port", redisPort)
    .set("spark.redis.auth", redisAuth)
  val session: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  private val dataFrameReader: DataFrameReader = session.read.format("jdbc")
    .option("url", mySqlUrl)
    .option("user", mySqlUsername)
    .option("password", mySqlPassword)

  /** Dataset loading */
  import session.implicits._
  val userDataset: Dataset[User] = camelDataFrame("User").as[User]
  val replyDataset: Dataset[Reply] = camelDataFrame("Reply").as[Reply]
  val postDataset: Dataset[Post] = camelDataFrame("Post").as[Post]
  val commentDataset: Dataset[Comment] = camelDataFrame("Comment").as[Comment]

  val userDataFrame: DataFrame = userDataset.toDF.withColumnRenamed("id", "uid")
  val replyDataFrame: DataFrame = replyDataset.toDF
  val postDataFrame: DataFrame = postDataset.toDF
  val commentDataFrame: DataFrame = commentDataset.toDF

  val replyWithUser: DataFrame = replyDataFrame
    .join(userDataFrame, replyDataFrame("replyerId") === userDataFrame("uid"))
  val postWithUser: DataFrame = postDataFrame
    .join(userDataFrame, postDataFrame("posterId") === userDataFrame("uid"))
  val commentWithUser: DataFrame = commentDataFrame
    .join(userDataFrame, commentDataFrame("commentorId") === userDataFrame("uid"))


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

}
