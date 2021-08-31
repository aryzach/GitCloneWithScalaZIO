
val zioVersion = "2.0.0-M2"

lazy val root = (project in file(".")).
settings(
				inThisBuild(List(
								organization := "com.example",
								scalaVersion := "2.13.6"
								)),
				name := "gitcloneinscalazio"
				)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
libraryDependencies += "dev.zio" %% "zio" % zioVersion

// both needed for zio test
libraryDependencies ++= Seq(
  "dev.zio" %% "zio-test"          % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt"      % zioVersion % "test",
  "dev.zio" %% "zio-test-magnolia" % zioVersion % "test" // optional
)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

//libraryDependencies +=
//  "com.typesafe.akka" %% "akka-actor" % "2.3.16"

val AkkaVersion = "2.6.16"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
