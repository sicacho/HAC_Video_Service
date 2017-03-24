package com.hac.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hac.configuration.AppConfiguration;
import com.hac.configuration.RabbitMqConfiguration;
import com.hac.domain.Video;
import com.hac.rabbitMQ.RabbitProducer;
import com.hac.service.VideoService;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 3/23/2017.
 */
@Service
public class VideoServiceImpl implements VideoService{

  RabbitProducer rabbitProducer;

  @Autowired
  RabbitMqConfiguration rabbitMqConfiguration;

  @Autowired
  AppConfiguration appConfiguration;

  ObjectMapper mapper = new ObjectMapper();

  public void initProducer() throws IOException, TimeoutException {
    if(rabbitProducer==null) {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(rabbitMqConfiguration.getHost());
      factory.setPort(rabbitMqConfiguration.getPort());
      factory.setUsername(rabbitMqConfiguration.getUsername());
      factory.setPassword(rabbitMqConfiguration.getPassword());
      Connection connection = factory.newConnection();
      rabbitProducer = new RabbitProducer(connection,appConfiguration.getMovieQueue());
    }
  }

  @Override
  public void sendVideoToEncoder(Video video) throws Exception {
    initProducer();
    rabbitProducer.send(mapper.writeValueAsString(video));
  }
}
