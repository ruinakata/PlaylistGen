package com.makers

import java.io.{BufferedWriter, FileWriter}

import com.wrapper.spotify.Api
import scala.collection.JavaConverters._



object PlaylistDownloader {
  val api = Api.builder
    .clientId("276a56a316944e23a41d97b6b1895fdf")
    .clientSecret("cf8e711f318f4b2a96c53f37b5310e02")
    .build

  def run(universeFile: String, file: String) {
    val universe = readUniverse(universeFile)
    val request = api.clientCredentialsGrant().build.get()
    api.setAccessToken(request.getAccessToken)
    val users = universe.map { x =>
        println("Fetching playlists for " + x)
        try {
          Some(User(api, UserInfo(api ,x)))
        } catch {
          case _: Throwable => None
        }
      }
      .flatten
      .map(_.toJSON)
      .mkString(",")

    println("Writing output to " + file)
    val w = new BufferedWriter(new FileWriter(file))
    w.write("[" + users + "]")
    w.close
  }

  private def readUniverse(file: String): List[String] =
    io.Source.fromFile(file).mkString.split("\n").toList
}
