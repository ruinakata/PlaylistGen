package makers

import org.json4s._
import org.json4s.jackson.JsonMethods._


object Loader {
  def load(file: String) = {
    val raw = io.Source.fromFile(file).mkString
    implicit val formats = DefaultFormats
    parse(raw).extract[List[User]]
  }
}
