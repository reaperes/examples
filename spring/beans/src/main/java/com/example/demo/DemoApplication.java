package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

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

	private class Config {}
}

@Configuration
class FailedConfig {

	@Bean
	public Object test(List<Config> configs) {
		return null;
	}

	private class Config {}
}
