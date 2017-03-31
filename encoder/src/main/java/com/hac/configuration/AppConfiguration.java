package com.hac.configuration;

import lombok.Data;
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
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfiguration {
  String movieEncodedLocation;
  String movieQueue;
  String queueFail;
  String ffmpegLocation;
  String ffprobeLocation;

}
