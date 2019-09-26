package com.shorturl.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableCaching
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
	   JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
	   jedisConFactory.setHostName("localhost");
	   jedisConFactory.setPort(6379);
	   jedisConFactory.setUsePool(true);
	   return jedisConFactory;
	}
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	   RedisTemplate<String, Object> template = new RedisTemplate<>();
	   template.setConnectionFactory(jedisConnectionFactory());
	   template.setKeySerializer(new StringRedisSerializer());
	   template.setHashKeySerializer(new StringRedisSerializer());
	   template.setHashValueSerializer(new StringRedisSerializer());
	   template.setValueSerializer(new StringRedisSerializer());
	   return template;
	}

}
