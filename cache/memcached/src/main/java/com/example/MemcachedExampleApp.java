package com.example;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static net.spy.memcached.ConnectionFactoryBuilder.Locator.ARRAY_MOD;
import static net.spy.memcached.ConnectionFactoryBuilder.Locator.CONSISTENT;
import static net.spy.memcached.ConnectionFactoryBuilder.Protocol.TEXT;

@SpringBootApplication
@EnableAsync
public class MemcachedExampleApp {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MemcachedExampleApp.class, args);
	}
}

@Configuration
class WebConfig extends WebMvcConfigurationSupport {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new StringHttpMessageConverter());
		converters.add(new MappingJackson2HttpMessageConverter());
	}
}

@Configuration
class SpyMemcachedConfiguration {

	private static final List servers = new ArrayList<String>() {{
		add("127.0.0.1:27001");
		add("127.0.0.1:27002");
		add("127.0.0.1:27003");
	}};

	@Bean
	public FactoryBean spyMemcachedArrayModHashFactory() {
		return createMemcachedClientFactoryBean(ARRAY_MOD);
	}

	@Bean
	public FactoryBean spyMemcachedConsistentHashFactory() {
		MemcachedClientFactoryBean memcachedClientFactoryBean = createMemcachedClientFactoryBean(CONSISTENT);
		memcachedClientFactoryBean.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
		return memcachedClientFactoryBean;
	}

	@Bean
	public MemcachedClient spyMemcachedArrayModeHashClient() throws Exception {
		return (MemcachedClient) spyMemcachedArrayModHashFactory().getObject();
	}

	@Bean
	public MemcachedClient spyMemcachedConsistentHashClient() throws Exception {
		return (MemcachedClient) spyMemcachedConsistentHashFactory().getObject();
	}

	private MemcachedClientFactoryBean createMemcachedClientFactoryBean(ConnectionFactoryBuilder.Locator locator) {
		MemcachedClientFactoryBean spyMemcachedFactory = new MemcachedClientFactoryBean();
		spyMemcachedFactory.setProtocol(TEXT);
		spyMemcachedFactory.setLocatorType(locator);
		spyMemcachedFactory.setServers(StringUtils.join(servers, ' '));
		return spyMemcachedFactory;
	}
}

@RestController
class TestController {

	@Autowired
	private MemcachedClient spyMemcachedArrayModeHashClient;

	@Autowired
	private MemcachedClient spyMemcachedConsistentHashClient;

	@GetMapping("spy/array-mod/{key}")
	public String getSpyArrayModValue(@PathVariable String key) {
		return (String) spyMemcachedArrayModeHashClient.get(key);
	}

	@PutMapping("spy/array-mod/{key}")
	public Boolean setSpyArrayModValue(@PathVariable String key, @RequestBody String value)
		throws ExecutionException, InterruptedException {
		return spyMemcachedArrayModeHashClient.set(key, 0, value).get();
	}

	@DeleteMapping("spy/array-mod/{key}")
	public Boolean deleteSpyArrayModValue(@PathVariable String key)
		throws ExecutionException, InterruptedException {
		return spyMemcachedArrayModeHashClient.delete(key).get();
	}

	@GetMapping("spy/consistent/{key}")
	public String getSpyConsistentValue(@PathVariable String key) {
		return (String) spyMemcachedConsistentHashClient.get(key);
	}

	@PutMapping("spy/consistent/{key}")
	public Boolean setSpyConsistentValue(@PathVariable String key, @RequestBody String value)
		throws ExecutionException, InterruptedException {
		return spyMemcachedConsistentHashClient.set(key, 0, value).get();
	}

	@DeleteMapping("spy/consistent/{key}")
	public Boolean deleteSpyConsistentValue(@PathVariable String key)
		throws ExecutionException, InterruptedException {
		return spyMemcachedConsistentHashClient.delete(key).get();
	}

	@PostMapping("flush")
	public Boolean flush() throws ExecutionException, InterruptedException {
		return spyMemcachedArrayModeHashClient.flush().get();
	}
}