package com.makers

import com.makers.model._
import com.makers.util._


case class User(
  val info: UserInfo,
  val playlists: List[Playlist]
) extends Jsonable

object User {
  def apply(api: SpotifyApi, info: UserInfo): User = {
    val request = () => { api.get.getPlaylistsForUser(info.id).limit(50) }
    val playlists = new Paginator(request).toList.flatten

    val cleaned = playlists
      .filter(_.getId != "null")
      .filter(_.getOwner.getId != "spotify")
      .map { x =>
        try {
          Some(Playlist(api, info, x))
        } catch {
          case _: Throwable => None
        }
      }
    User(info, cleaned.flatten)
  }
}
