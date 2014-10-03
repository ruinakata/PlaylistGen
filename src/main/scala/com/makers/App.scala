package com.makers

object App {

  def main(args: Array[String]) {
    println("Hello Test")
    PlaylistDownloader.run(args(0), args(1))
  }
}
