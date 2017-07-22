package com.example.demo.beans;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
class SimpleConfig {
  @Bean
  public Object simple() {
    return null;
  }
}

//@Configuration
class SuccessConfig {

  @Bean
  public Config config() {
    return new Config();
  }

  @Bean
  public Object test(Config config) {
    return null;
  }

  private class Config {
  }
}

//@Configuration
class FailedConfig {

  @Bean
  public Object test(List<Config> configs) {
    return null;
  }

  private class Config {
  }
}
