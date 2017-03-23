package com.hac.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 3/23/2017.
 */
@ConfigurationProperties(prefix = "application")
public class AppConfiguration {

  String movieLocation;
  String movieQueue;

  public String getMovieLocation() {
    return movieLocation;
  }

  public void setMovieLocation(String movieLocation) {
    this.movieLocation = movieLocation;
  }

  public String getMovieQueue() {
    return movieQueue;
  }

  public void setMovieQueue(String movieQueue) {
    this.movieQueue = movieQueue;
  }
}
