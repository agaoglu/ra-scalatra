organization := "com.example"

name := "ss"

version := "1.0"

scalaVersion := "2.9.0-1"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % "2.3.2",
  "org.scalatra" %% "scalatra" % "2.0.0-SNAPSHOT",
  "org.scalatra" %% "scalatra-scalate" % "2.0.0-SNAPSHOT",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "org.slf4j" % "slf4j-api" % "1.6.1",
  "org.slf4j" % "slf4j-log4j12" % "1.6.1"
)

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Web plugin repo" at "http://siasia.github.com/maven2"
)
