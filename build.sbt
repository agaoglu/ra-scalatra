organization := "com.example"

name := "ss"

version := "1.0"

scalaVersion := "2.9.0"

seq(WebPlugin.webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalaquery" %% "scalaquery" % "0.9.4",
  "org.scalatra" %% "scalatra" % "2.0.0-SNAPSHOT",
  "org.scalatra" %% "scalatra-scalate" % "2.0.0-SNAPSHOT",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
  "mysql" % "mysql-connector-java" % "5.1.16",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default"
)

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Web plugin repo" at "http://siasia.github.com/maven2"
)
