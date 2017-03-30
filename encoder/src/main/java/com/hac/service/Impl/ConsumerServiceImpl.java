package com.hac.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hac.configuration.AppConfiguration;
import com.hac.configuration.RabbitMqConfiguration;
import com.hac.domain.Video;
import com.hac.rabbitMQ.RabbitConsumer;
import com.hac.service.ConsumerService;
import com.rabbitmq.client.*;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by khang on 3/29/2017.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

  RabbitConsumer rabbitConsumer;

  @Autowired
  RabbitMqConfiguration rabbitMqConfiguration;

  @Autowired
  AppConfiguration appConfiguration;

  ObjectMapper objectMapper = new ObjectMapper();

  FFmpeg ffmpeg;

  FFprobe fFprobe;

  @Override
  public void consume() throws Exception {
    init();
    Consumer video = new DefaultConsumer(rabbitConsumer.getChannel()) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Video video = objectMapper.readValue(body,Video.class);


      }
    };
    rabbitConsumer.consume(video);

  }

  private void init() throws IOException, TimeoutException {
    if(rabbitConsumer==null) {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(rabbitMqConfiguration.getHost());
      factory.setPort(rabbitMqConfiguration.getPort());
      factory.setUsername(rabbitMqConfiguration.getUsername());
      factory.setPassword(rabbitMqConfiguration.getPassword());
      Connection connection = factory.newConnection();
      rabbitConsumer = new RabbitConsumer(connection,appConfiguration.getMovieQueue());
    }
  }
}
