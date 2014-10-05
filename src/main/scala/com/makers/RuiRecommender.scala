package com.makers

import com.makers.model._
import breeze.linalg._
import scalaz._
import Scalaz._

class RuiRecommender(val users: Set[User]) {
// loop thru all playlxists and pick out ones that have at least on of the artists in it
// make a hash of the songs with the count
// also make a hash of the artists with the count
    def recommend(artists: List[Artist]) = {
        val related = collection.mutable.Map[Song, Int]()
        val universe = users.flatMap(_.playlists).filter(_.uniqueArtists.size > 4)
        // considered is a List of Maps
        val considered = universe.toList.flatMap { playlist => 
            val listofsongs = playlist.songs
            val artistsinplaylist = listofsongs.map { song =>
                song.artist.name
            }
            val numintersect = (artistsinplaylist.toSet).intersect(artists.map(_.name).toSet)
            if (numintersect.size > 0) {
                Some(playlist.songCount())
            } else None
        }
        val overallresult = considered.foldLeft(Map[Song, Int]()) { case (acc, x) => acc |+| x }.toList.sortBy(-_._2)
        overallresult.take(50).foreach(x => println(s"${x._1.artist.name}, ${x._1.name} : ${x._2}"))
    }

    // Helpers

    def weighbypopu = {

    }

    def filterbysimilarpopu(artists: List[Artist]) = {

    }


}

