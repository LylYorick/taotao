package com.taotao.rest.component.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis客户端单机版实现类
 * @author liyuelong
 *
 */
public class JedisClientSingle implements JedisClient {
	
	@Autowired
	private JedisPool JedisPool; 
	
	@Override
	public String set(String key, String value) {
		Jedis jedis = JedisPool.getResource();
		String result = jedis.set(key, value);
		jedis.close();
		return result;
	}

	@Override
	public String get(String key) {
		Jedis jedis = JedisPool.getResource();
		String value = jedis.get(key);
		jedis.close();
		return value;
	}

	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = JedisPool.getResource();
		Long result = jedis.hset(key, field, value);
		jedis.close();
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis jedis = JedisPool.getResource();
		String value = jedis.hget(key, field);
		jedis.close();
		return value;
	}

	@Override
	public Long hdel(String key, String field) {
		Jedis jedis = JedisPool.getResource();
		Long result = jedis.hdel(key, field);
		jedis.close();
		return result;
	}

	@Override
	public Long incr(String key) {
		Jedis jedis = JedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long decr(String key) {
		Jedis jedis = JedisPool.getResource();
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}

	@Override
	public Long expire(String key, int seconds) {
		Jedis jedis = JedisPool.getResource();
		Long result = jedis.expire(key, seconds);
		jedis.close();
		return result;
	}

	@Override
	public Long ttl(String key) {
		Jedis jedis = JedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

}
