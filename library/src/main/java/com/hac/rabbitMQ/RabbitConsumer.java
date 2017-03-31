package com.hac.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 3/23/2017.
 */
public class RabbitConsumer {
  private Connection connection;
  private String queueName;
  private Channel channel;

  public RabbitConsumer(Connection connection, String queueName) throws IOException {
    this.connection = connection;
    this.queueName = queueName;
    channel = connection.createChannel();
    channel.queueDeclare(queueName, true, false, false, null);
  }

  public void consume(Consumer consumer) throws Exception {
    channel.basicConsume(queueName, true, consumer);
  }

  public void destroy() throws IOException, TimeoutException {
      channel.close();
      connection.close();
  }

  public Channel getChannel() {
    return channel;
  }
}
