organization := "com.example"

name := "ss"

version := "1.0"

scalaVersion := "2.9.0"

seq(webSettings :_*)

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % "2.0.0-SNAPSHOT",
  "org.scalatra" %% "scalatra-scalate" % "2.0.0-SNAPSHOT",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "jetty",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "log4j" % "log4j" % "1.2.16",
  "org.jdom" % "jdom" % "1.1",
  "com.google.protobuf" % "protobuf-java" % "2.3.0",
  "com.google.collections" % "google-collections" % "1.0"
)

resolvers ++= Seq(
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Web plugin repo" at "http://siasia.github.com/maven2"
)

unmanagedBase <<= baseDirectory( _ /"custom_lib" )
