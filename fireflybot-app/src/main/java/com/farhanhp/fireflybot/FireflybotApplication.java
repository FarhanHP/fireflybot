package com.farhanhp.fireflybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FireflybotApplication {

  public static final String DEFAULT_PORT_NUMBER = "8080";
  private static final String PORT = "PORT";

  public static void main(String[] args) {
    SpringApplication.run(FireflybotApplication.class, args);
  }

  @Bean
  public ReactiveWebServerFactory reactiveWebServerFactory() {
    int port = Integer.parseInt(System.getenv().getOrDefault(PORT, DEFAULT_PORT_NUMBER));

    NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
    factory.addServerCustomizers(builder -> builder.port(port));

    return factory;
  }

}
