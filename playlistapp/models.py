from django.db import models

class User(models.Model):
    spotify_id = models.TextField()
    first_name = models.TextField()
    profile_pic = models.TextField()
    facebook_id = models.TextField()

class Artist(models.Model):
    spotify_id = models.TextField()
    name = models.TextField()
    popularity = models.IntegerField()

class Song(models.Model):
    spotify_id = models.TextField()
    title = models.TextField()
    artist = models.ForeignKey(Artist, related_name='artists')
    popularity = models.IntegerField()
    genre = models.TextField()
    year = models.IntegerField()

class Playlist(models.Model):
    spotify_id = models.TextField()
    title = models.TextField()
    user = models.ForeignKey(User, related_name='users')

class PlaylistSong(models.Model):
    playlist = models.ForeignKey(Playlist, related_name='playlists')
    song = models.ForeignKey(Song, related_name='songs')


