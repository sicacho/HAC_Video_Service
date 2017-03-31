package com.hac.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hac.configuration.AppConfiguration;
import com.hac.configuration.RabbitMqConfiguration;
import com.hac.domain.ErrorDTO;
import com.hac.domain.Video;
import com.hac.rabbitMQ.RabbitConsumer;
import com.hac.rabbitMQ.RabbitProducer;
import com.hac.service.ConsumerService;
import com.rabbitmq.client.*;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

/**
 * Created by khang on 3/29/2017.
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

  RabbitConsumer rabbitConsumer;

  RabbitProducer rabbitProducer;

  @Autowired
  RabbitMqConfiguration rabbitMqConfiguration;

  @Autowired
  AppConfiguration appConfiguration;

  ObjectMapper objectMapper = new ObjectMapper();

  FFmpeg ffmpeg;

  FFprobe ffprobe;

  private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

  @Override
  public void start() throws Exception {
    init();
    Consumer video = new DefaultConsumer(rabbitConsumer.getChannel()) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        Video video = objectMapper.readValue(body,Video.class);
        try {
          String inputLocation = video.getLocation();
          String outputLocation = appConfiguration.getMovieEncodedLocation()+File.separator+video.getName();
          FFmpegBuilder builder = getFFmpegBuilder(inputLocation, outputLocation);
          FFmpegExecutor executor = new FFmpegExecutor(ffmpeg,ffprobe);
          executor.createJob(builder).run();
        } catch (Exception e) {
          logger.error("Error : " + video.getName() + e);
          rabbitProducer.send(objectMapper.writeValueAsString(new ErrorDTO(video,e)));
        }

      }
    };
    rabbitConsumer.consume(video);

  }

  @Override
  public void stop() {
    try {
      rabbitConsumer.destroy();
      rabbitProducer.destroy();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }

  }

  private FFmpegBuilder getFFmpegBuilder(String inputLocation, String outputLocation) {
    return new FFmpegBuilder()
              .setInput(inputLocation)     // Filename, or a FFmpegProbeResult
              .overrideOutputFiles(true) // Override the output if it exists
              .addOutput(outputLocation)   // Filename for the destination
              .setFormat("mp4")        // Format is inferred from filename, or can be set
              .disableSubtitle()       // No subtiles
              .setAudioChannels(1)         // Mono audio
              .setAudioCodec("aac")        // using the aac codec
              .setAudioSampleRate(48_000)  // at 48KHz
              .setAudioBitRate(32768)      // at 32 kbit/s
              .setVideoCodec("libx264")     // Video using x264
              .setVideoFrameRate(30, 1)     // at 30 frames per second
              .setVideoResolution(640, 480) // at 640x480 resolution
              .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
              .done();
  }

  private void init() throws IOException, TimeoutException {
    if(rabbitConsumer==null || rabbitProducer==null) {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(rabbitMqConfiguration.getHost());
      factory.setPort(rabbitMqConfiguration.getPort());
      factory.setUsername(rabbitMqConfiguration.getUsername());
      factory.setPassword(rabbitMqConfiguration.getPassword());
      Connection connection = factory.newConnection();
      rabbitConsumer = new RabbitConsumer(connection,appConfiguration.getMovieQueue());
      rabbitProducer = new RabbitProducer(connection,appConfiguration.getQueueFail());
    }
    if(ffmpeg==null) {
      ffmpeg = new FFmpeg(appConfiguration.getFfmpegLocation());
    }
    if(ffprobe==null) {
      ffprobe = new FFprobe(appConfiguration.getFfprobeLocation());
    }
  }
}
