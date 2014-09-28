package makers

import scala.collection.JavaConversions._

import org.json4s._
import org.json4s.jackson.Serialization
import com.wrapper.spotify.Api
import com.wrapper.spotify.models.{SimpleAlbum, SimpleArtist, SimplePlaylist, Track, User => SUser}


trait SpotifyEntity {
  def id: String
  def href: String
  def name: String
}

trait Jsonable {
  implicit val formats = DefaultFormats
  def toJSON(): String = Serialization.write(this)
}

case class Playlist(
  override val id: String,
  override val href: String,
  override val name: String,
  val description: String,
  val followerCount: Int,
  val owner: UserInfo,
  val songs: List[Song]
) extends SpotifyEntity with Jsonable

case class Song(
  override val id: String,
  override val href: String,
  override val name: String,
  val album: Album,
  val artist: Artist,
  val featured: List[Artist],
  val duration: Int,
  val popularity: Int
) extends SpotifyEntity with Jsonable

case class Album(
  override val id: String,
  override val href: String,
  override val name: String
) extends SpotifyEntity with Jsonable

case class UserInfo(
  override val id: String,
  override val href: String,
  override val name: String
) extends SpotifyEntity with Jsonable

case class User(
  val info: UserInfo,
  val playlists: List[Playlist]
) extends Jsonable

case class Artist(
  override val id: String,
  override val href: String,
  override val name: String
) extends SpotifyEntity with Jsonable

object Playlist {
  def apply(api: Api, user: UserInfo, sp: SimplePlaylist): Playlist = {
    //println("user: " + user + " sp: " + sp.getName + " " + sp.getOwner.getId + " " + sp.getId)
    val playlist = api.getPlaylist(sp.getOwner.getId, sp.getId).build.get
    val songs = playlist.getTracks.getItems.map(_.getTrack).map(Song(_))
    Playlist(
      sp.getId,
      sp.getHref,
      sp.getName,
      playlist.getDescription,
      playlist.getFollowers.getTotal,
      user,
      songs.toList)
  }
}

object Song {
  def apply(t: Track): Song = {
    val album = Album(t.getAlbum)
    val artist = Artist(t.getArtists.head)
    val featured = t.getArtists.tail.map(Artist(_)) // Need to check for order
    Song(t.getId,
      t.getHref,
      t.getName,
      album,
      artist,
      featured.toList,
      t.getDuration,
      t.getPopularity
      )
  }
}

object Album {
  def apply(a: SimpleAlbum): Album = Album(a.getId, a.getHref, a.getName)
}

object Artist {
  def apply(a: SimpleArtist): Artist = Artist(a.getId, a.getHref, a.getName)
}

object UserInfo {
  def apply(u: SUser): UserInfo = UserInfo(u.getId, u.getHref, u.getDisplayName)
}

object User {
  def apply(api: Api, u: String): User = {
    val response = api.getPlaylistsForUser(u).build.get
    //println(response)
    //println(response.getItems.head.getOwner)
    if(response.getItems.length == 0) throw new Exception("fuck")
    val info = UserInfo(response.getItems.head.getOwner) // Need to check for 0 playlists
    val playlists = response.getItems.toList
      .filter(_.getId != "null")
      .filter(_.getOwner.getId != "spotify")
      .map { x =>
        try {
          Some(Playlist(api, info, x))
        } catch {
          case e: Exception => None
        }
      }
    User(info, playlists.flatten)
  }
}

