package com.makers.util

import scala.collection.JavaConverters._

import com.makers.model._

import com.wrapper.spotify.models.{Page, SimplePlaylist}
import com.wrapper.spotify.methods.UserPlaylistsRequest


class Paginator(req: () => UserPlaylistsRequest.Builder) extends Iterator[List[SimplePlaylist]] {
  private var offset = 0
  private var haveNext = true

  def hasNext = haveNext
  def next = {
    val res = get(req(), offset)
    haveNext = (res.getNext != null)
    offset = res.getOffset + res.getLimit
    extract(res)
  }

  private def get(req: UserPlaylistsRequest.Builder, off: Int) = req.offset(off).build.get
  private def extract(p: Page[SimplePlaylist]): List[SimplePlaylist] = p.getItems.asScala.toList
}
