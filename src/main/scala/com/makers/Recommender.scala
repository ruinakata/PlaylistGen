package com.makers

import com.makers.model._

import breeze.linalg._

class Recommender(val users: List[User]) {

  def recommend(artists: List[Artist]) = {
    val uniques = uniqueArtists.toList.zipWithIndex
    val uniqueCount = uniques.length
    val inputV = toSV(uniqueCount, indexUniques(uniques, artists))

    val similar = users.flatMap(_.playlists).map { pl =>
      val plV = toSV(uniqueCount, indexUniques(uniques, pl.songs.map(_.artist).toSet.toList))
      (pl, (inputV dot plV) / (inputV.norm() * plV.norm() ))
    }

    val sorted = similar.sortWith(_._2 > _._2).take(10)
    sorted.foreach(x => {
      println("Similarity Score: " + x._2 + " Artists: " + x._1.songs.map(_.artist.name).mkString(", "))
    })
    similar
  }

  def toSV(size: Int, is: List[Int]) = {
    val vec = SparseVector.zeros[Int](size)
    is.map(vec(_) = 1)
    vec
  }

  def indexUniques(uniques: List[(String, Int)], input: List[Artist]) =
    for {
      a <- input
      (artist, idx) <- uniques if artist == a.name
    } yield(idx)

  def uniqueArtists() = {
    val artists = for {
      user <- users
      playlist <- user.playlists
      song <- playlist.songs
    } yield song.artist.name
    artists.toSet
  }
}
