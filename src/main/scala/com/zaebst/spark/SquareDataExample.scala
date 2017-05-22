package com.zaebst.spark
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._

object SquareDataExample {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf()
      .setAppName("SquareData")
    val sc = new SparkContext()

    val spark = SparkSession
      .builder()
      .appName("SquareData")
      .getOrCreate()

    import spark.implicits._

    val distDF = sc.parallelize(List(1, 2, 3)).toDF("numbers")


    val squaredData = distDF.withColumn("squared", squareDataUdf(col("number")))
    squaredData.show()
  }
  val squareData = (numToSq: Int) => { numToSq * numToSq }
  val squareDataUdf = udf { squareData }
}
