name := "example-spark-sbt"
organization := "com.zaebst.spark"
version := "0.0.1"

scalaVersion := "2.11.8"
val sparkVersion = "2.0.1"
val hadoopVersion = "2.7.2"

fork := true
javaOptions in Test := Seq("-Dcom.zaebst.env=test")
parallelExecution in Test := false

publishMavenStyle := true

mainClass in Compile := Some("com.zaebst.spark.SquareDataExample")

resolvers ++= Seq(
  "Local Maven Repository" at "file:///" + Path.userHome + "/.m2/repository",
  "Maven central" at "https://repo1.maven.org/maven2/"
)

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.holdenkarau" %% "spark-testing-base" % "0.6.0" % "test",

  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided"
)

assemblyOption in assembly :=
  (assemblyOption in assembly).value.copy(includeScala = false)
