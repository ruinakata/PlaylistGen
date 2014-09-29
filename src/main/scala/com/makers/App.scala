package makers

object App {

  def main(args: Array[String]) {
    println("Hello")
    PlaylistDownloader.run("universe.small", "playlists2.json")
  }
}
