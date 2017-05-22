package com.zaebst.spark

import org.scalatest.FunSuite
import com.holdenkarau.spark.testing.DataFrameSuiteBase
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SparkSession}
import org.apache.spark.sql.functions._
import SquareDataExample._
import org.apache.spark.sql.types._

class SquareDataExampleSpec extends FunSuite with DataFrameSuiteBase {
  test("simple test") {
    val sparkSession = spark
    val sqlCtx = sqlContext
    import sparkSession.implicits._

    val input = sc.parallelize(List(1, 2, 3)).toDF("numbers")
    val outputRDD = sc.parallelize(List(1, 4, 9))
    val outputRowRDD = outputRDD.map( x => Row(x))

    val fields = Seq(StructField("squared", IntegerType, nullable = true))
    val schema = StructType(fields)

    val output = sparkSession.createDataFrame(outputRowRDD, schema)

    val inputProcessed = input.
      withColumn("squared", squareDataUdf(col("numbers"))).
      select("squared")

    assertDataFrameEquals(inputProcessed, output)
  }
}
