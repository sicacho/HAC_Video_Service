package com.hac.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 3/23/2017.
 */
public class RabbitProducer {
  private Connection connection;
  private String queueName;
  private Channel channel;

  public RabbitProducer(Connection connection, String queueName) {
    this.connection = connection;
    this.queueName = queueName;

  }

  public void send(String message) throws IOException {
    if(channel==null) {
      channel = connection.createChannel();
    }
    channel.queueDeclare(queueName, true, false, false, null);
    channel.basicPublish("", queueName, null, message.getBytes());
  }

  public void destroy() throws IOException, TimeoutException {
    channel.close();
    connection.close();
  }


}
