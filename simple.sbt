import AssemblyKeys._

name := "Playlist Generator"

version := "0.1"

scalaVersion := "2.11.2"

resolvers ++= Seq(
  "apache"                 at "http://repo.maven.apache.org/maven2",
  "typesafe"               at "http://repo.typesafe.com/typesafe/releases",
  "artifactory"            at "http://scalasbt.artifactoryonline.com/scalasbt/"
)

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-jackson" % "3.2.10",
  "org.json4s" %% "json4s-ext" % "3.2.10",
  "org.scalaz" %% "scalaz-core" % "7.0.6",
  "org.scalanlp" %% "breeze" % "0.8.1",
  "org.scalanlp" %% "breeze-natives" % "0.8.1",
  // spotify-java lib dependencies
  "junit" % "junit" % "4.11",
  "com.google.protobuf" % "protobuf-java" % "2.5.0",
  "commons-httpclient" % "commons-httpclient" % "3.1",
  "net.sf.json-lib" % "json-lib" % "2.4" classifier "jdk15",
  "org.mockito" % "mockito-all" % "1.8.4",
  "com.google.guava" % "guava" % "17.0-rc2"
)

initialCommands in console :=
  """
  import collection.JavaConverters._
  import com.makers._
  import com.makers.util._
  import com.makers.model._
  import com.wrapper.spotify.Api
  import scalaz._
  import Scalaz._
  import breeze.linalg._
  val patrickId = "124091297"
  val api = new SpotifyApi("276a56a316944e23a41d97b6b1895fdf", "cf8e711f318f4b2a96c53f37b5310e02")
  val users = Loader.load("playlists-all.json")
  val artists = users.flatMap(_.playlists).flatMap(_.songs).map(_.artist)
  val sbtrkt = artists.find(_.name == "SBTRKT").get
  val disclosure = artists.find(_.name == "Disclosure").get
  val cleanBandit = artists.find(_.name == "Clean Bandit").get
  val flume = artists.find(_.name == "Flume").get
  val rec = new Recommender(users)
  """

unmanagedJars in Compile ++= (file("/Users/patrick/src/patrick/playlistgen/project/lib/") * "*.jar").classpath

assemblySettings

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case _ => MergeStrategy.first
  }
}

jarName in assembly := "playlist-generator.jar"

net.virtualvoid.sbt.graph.Plugin.graphSettings
