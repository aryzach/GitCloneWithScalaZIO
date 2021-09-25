
//val zioVersion = "2.0.0-M2"
val zioVersion = "1.0.8"

lazy val root = (project in file(".")).
settings(
				inThisBuild(List(
								organization := "com.example",
								scalaVersion := "3.0.0"
								)),
				name := "gitcloneinscalazio"
				)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
libraryDependencies += "dev.zio" %% "zio" % zioVersion

// both needed for zio test
libraryDependencies ++= Seq(
  "dev.zio" %% "zio-test"          % zioVersion % "test",
  "dev.zio" %% "zio-test-sbt"      % zioVersion % "test",
  "dev.zio" %% "zio-test-magnolia" % zioVersion % "test", // optional
	"dev.zio" %% "zio-nio" % "1.0.0-RC11" // for ZIO socket https://zio.github.io/zio-nio/docs/essentials/essentials_index

)
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

//libraryDependencies +=
//  "com.typesafe.akka" %% "akka-actor" % "2.3.16"

//val AkkaVersion = "2.6.16"
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % AkkaVersion
