package com.makers

import com.makers.model._
import com.makers.util.SpotifyApi

import java.io.{BufferedWriter, FileWriter}


object PlaylistDownloader {
  val api = new SpotifyApi("276a56a316944e23a41d97b6b1895fdf", "cf8e711f318f4b2a96c53f37b5310e02")
  def run(universeFile: String, file: String) {
    val universe = readUniverse(universeFile)

    val w = new BufferedWriter(new FileWriter(file))
    println("Writing output to " + file)
    w.write("[")

    val users = universe.zipWithIndex.foreach { case (x, idx) =>
      println("Fetching playlists for " + x)
      try {
        val user = User(api, UserInfo(api ,x))
        w.write(user.toJSON)
        if(idx < universe.length - 2) w.write(",")
      } catch {
          case e: Throwable => {
            System.err.println("User: " + x + " failed to fetch");
            None
          }
        }
      }
      //.flatten
      //.map(_.toJSON)
      //.mkString(",")

    //val w = new BufferedWriter(new FileWriter(file))
    //w.write("[" + users + "]")
    w.write("]")
    w.close
    println("Total requests: " + api.count)
  }


  private def readUniverse(file: String): List[String] =
    io.Source.fromFile(file).mkString.split("\n").toList
}
