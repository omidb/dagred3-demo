import SonatypeKeys._

// Turn this project into a Scala.js project by importing these settings
enablePlugins(ScalaJSPlugin)

organization := "omidb.com"

name := "dagred3-js"

version := "0.3.2"

scalaVersion := "2.11.5"

resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"),
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "bintray/non" at "http://dl.bintray.com/non/maven",
  Resolver.url("scala-js-releases",
    url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(
      Resolver.ivyStylePatterns))



persistLauncher in Compile := true

persistLauncher in Test := false

testFrameworks += new TestFramework("utest.runner.Framework")

jsDependencies ++= Seq(
  "org.webjars" % "dagre-d3" % "0.3.2" / "dagre-d3.js",
  "org.webjars" % "react" % "0.12.1" / "react-with-addons.js" commonJSName "React"
  )

//
libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "com.github.japgolly.scalajs-react" %%% "core" % "0.8.1",
  "fr.iscpif" %%% "scaladget" % "0.5.0-SNAPSHOT",
  "com.github.japgolly.scalajs-react" %%% "ext-scalaz71" % "0.8.4",
  "com.github.japgolly.fork.scalaz" %%% "scalaz-core" % "7.1.1-2",
  "eu.unicredit" %%% "paths-scala-js" % "0.3.2"
)
//

skip in packageJSDependencies := false

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-language:reflectiveCalls"
)

publishMavenStyle := true

pomIncludeRepository := { x => false }
