package com.makers.util

import java.util.concurrent._

import com.wrapper.spotify.Api


class SpotifyApi(clientId: String, clientSecret: String) {
  private val ex = new ScheduledThreadPoolExecutor(1)
  @volatile private var _api = Api.builder
    .clientId(clientId)
    .clientSecret(clientSecret)
    .build

  private val dt = refreshToken()
  private val task = new Runnable {
    def run() = refreshToken()
  }
  private val running = ex.scheduleAtFixedRate(task, (dt*0.75).toInt, dt, TimeUnit.SECONDS)

  def refreshToken() = {
    val (token, expiration) = SpotifyApi.getAccessToken(_api)
    _api.setAccessToken(token)
    expiration
  }

  var count = 0
  def get = {
    count += 1
    _api
  }

  def shutdown() {
    running.cancel(false)
    ex.shutdownNow()
  }
}

private object SpotifyApi {
  def getAccessToken(api: Api) = {
    val req = api.clientCredentialsGrant.build.get
    (req.getAccessToken, req.getExpiresIn)
  }
}
