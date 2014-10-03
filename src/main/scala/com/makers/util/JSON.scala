package com.makers.util

import org.json4s._
import org.json4s.jackson.Serialization


trait Jsonable {
  implicit val formats = DefaultFormats
  def toJSON(): String = Serialization.write(this)
}
