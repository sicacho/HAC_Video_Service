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
@ConfigurationProperties(prefix = "rabbit")
@Data
public class RabbitMqConfiguration {
  String username;
  String password;
  String host;
  int port;
}
