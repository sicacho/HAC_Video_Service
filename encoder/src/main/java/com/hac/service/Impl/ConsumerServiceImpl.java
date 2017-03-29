package com.hac.service.Impl;

import com.hac.configuration.AppConfiguration;
import com.hac.configuration.RabbitMqConfiguration;
import com.hac.rabbitMQ.RabbitConsumer;
import com.hac.service.ConsumerService;
import com.rabbitmq.client.*;
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

  @Override
  public void consume() throws Exception {
    init();
    Consumer video = new DefaultConsumer(rabbitConsumer.getChannel()) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
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
