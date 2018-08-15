package com.cy.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Slf4j
@Configuration
public class RedisConfig {

	@Bean(name = "jedis.pool")
	@Autowired
	public JedisPool jedisPool(
			@Qualifier("jedis.pool.config") JedisPoolConfig config,
			@Value("${jedis.pool.host}") String host,
			@Value("${jedis.pool.port}") int port,
			@Value("${jedis.pool.password}") String password) {
		log.info("缓存服务器的地址：" + host + ":" + port);
		return new JedisPool(config, host, port, Protocol.DEFAULT_TIMEOUT,
				password);
	}

	@Bean(name = "jedis.pool.config")
	public JedisPoolConfig jedisPoolConfig(
			@Value("${jedis.pool.config.maxTotal}") int maxTotal,
			@Value("${jedis.pool.config.maxIdle}") int maxIdle,
			@Value("${jedis.pool.config.maxWaitMillis}") int maxWaitMillis) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		return config;
	}
}
