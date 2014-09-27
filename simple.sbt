import AssemblyKeys._

name := "Playlist Generator"

version := "0.1"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "apache"                 at "http://repo.maven.apache.org/maven2",
  "typesafe"               at "http://repo.typesafe.com/typesafe/releases",
  "artifactory"            at "http://scalasbt.artifactoryonline.com/scalasbt/"
)

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "2.0.5",
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  "mysql" % "mysql-connector-java" % "5.1.32",
  "joda-time" % "joda-time" % "2.4",
  "org.json4s" %% "json4s" % "3.2.10",
  "org.json4s" %% "json4s-ext" % "3.2.10",
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "org.scalatest" %% "scalatest" % "2.2.2"
)

assemblySettings

jarName in assembly := "playlist-generator.jar"

net.virtualvoid.sbt.graph.Plugin.graphSettings
