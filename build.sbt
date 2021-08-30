
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

