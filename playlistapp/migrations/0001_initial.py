# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Artist',
            fields=[
                ('id', models.AutoField(auto_created=True, serialize=False, primary_key=True, verbose_name='ID')),
                ('spotify_id', models.TextField()),
                ('name', models.TextField()),
                ('popularity', models.IntegerField()),
            ],
        ),
        migrations.CreateModel(
            name='Playlist',
            fields=[
                ('id', models.AutoField(auto_created=True, serialize=False, primary_key=True, verbose_name='ID')),
                ('spotify_id', models.TextField()),
                ('title', models.TextField()),
            ],
        ),
        migrations.CreateModel(
            name='PlaylistSong',
            fields=[
                ('id', models.AutoField(auto_created=True, serialize=False, primary_key=True, verbose_name='ID')),
                ('playlist', models.ForeignKey(to='playlistapp.Playlist', related_name='playlists')),
            ],
        ),
        migrations.CreateModel(
            name='Song',
            fields=[
                ('id', models.AutoField(auto_created=True, serialize=False, primary_key=True, verbose_name='ID')),
                ('spotify_id', models.TextField()),
                ('title', models.TextField()),
                ('popularity', models.IntegerField()),
                ('genre', models.TextField()),
                ('year', models.IntegerField()),
                ('artist', models.ForeignKey(to='playlistapp.Artist', related_name='artists')),
            ],
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(auto_created=True, serialize=False, primary_key=True, verbose_name='ID')),
                ('spotify_id', models.TextField()),
                ('first_name', models.TextField()),
                ('profile_pic', models.TextField()),
                ('facebook_id', models.TextField()),
            ],
        ),
        migrations.AddField(
            model_name='playlistsong',
            name='song',
            field=models.ForeignKey(to='playlistapp.Song', related_name='songs'),
        ),
        migrations.AddField(
            model_name='playlist',
            name='user',
            field=models.ForeignKey(to='playlistapp.User', related_name='users'),
        ),
    ]
