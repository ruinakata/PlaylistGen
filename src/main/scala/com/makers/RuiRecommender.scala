package com.makers

import com.makers.model._

import breeze.linalg._

class Recommender(val users: List[User]) {

  def recommend(artists: List[Artist]) = {
    val uniques = uniqueArtists.toList.zipWithIndex
    val uniqueCount = uniques.length
    val inputV = toSV(uniqueCount, indexUniques(uniques, artists))
    val universe = users.flatMap(_.playlists).filter(_.uniqueArtists.size > 4)

    val similar = universe.map { pl =>
      val plV = toSV(uniqueCount, indexUniques(uniques, pl.songs.map(_.artist).toSet.toList))
      (pl, (inputV dot plV) / (inputV.norm() * plV.norm() ))
    }

    val sorted = similar.sortWith(_._2 > _._2).take(10)
    sorted.foreach(x => {
      println("Similarity Score: " + x._2 + " Artists: " + x._1.songs.map(_.artist.name).mkString(", "))
    })
    similar
  }

class RuiRecommender(val users: List[User]) {
// loop thru all playlists and pick out ones that have at least on of the artists in it
// make a hash of the songs with the count
// also make a hash of the artists with the count
    def recommend(artists: List[Artist]) = {
        val hashofrelated = collection.mutable.Map[String, Int]
        val universe = users.flatMap(._playlists).filter(_.uniqueArtists.size > 4)
        val considered = universe.map { playlist => 
            val listofsongs = playlist.songs
            listofsongs.map { song => 
                val artistofsong = song.artist 
                if (artists contains artistofsong) {
                    hashofrelated(song) = hashofrelated.getOrElse(key, 0) 
                }
            }
         }
         println(hashofrelated)
    }
}

1. loop thru all playlists and pick out ones that have at least one of the artists in it 
2. make a hash of the artists in the playlist with their count 