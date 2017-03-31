package com.hac;

import com.hac.service.ConsumerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by tnkhang on 3/24/2017.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class EncoderApplication  {

  public static void main(String[] args) {
    ConfigurableApplicationContext applicationContext = SpringApplication.run(EncoderApplication.class, args);
    ConsumerService consumerService = applicationContext.getBean(ConsumerService.class);
    try {
      consumerService.start();
    } catch (Exception e) {
      e.printStackTrace();
      consumerService.stop();
    }
  }
}
