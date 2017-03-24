package com.hac.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 3/23/2017.
 */
@Component
@Configuration
@EnableConfigurationProperties
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
