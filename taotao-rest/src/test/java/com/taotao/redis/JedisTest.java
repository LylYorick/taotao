package com.taotao.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	/**
	 * 测试jedis连接redis的单机版
	 */
	@Test
	public void testRedisSingle(){
		Jedis jedis = new Jedis("192.168.164.132", 6379);
		jedis.set("test", "hello world");
		String string = jedis.get("test");
		System.out.println(string);
		jedis.close();
	}
	/**
	 * 测试jedis的链接池
	 */
	@Test
	public void testJedisPool(){
		//创建jedispoool对象，在系统中连接池对象应该是单例的。
		JedisPool pool = new JedisPool("192.168.164.132", 6379);
		//从连接池对象中获取jedis对象
		Jedis jedis = pool.getResource();
		//取值
		String string = jedis.get("test");
		System.out.println(string);
		//关闭jedis对象 不关闭的话连接池对象无法回收链接 将会导致异常
		jedis.close();
		//关闭连接池对象 在系统结束时关闭
		pool.close();
	}
	/**
	 * 测试Jedis连接Redis集群版 
	 */
	@Test
	public void testRedisCluster(){
		//创建一个集合 用于存放全部的redis结点
		Set<HostAndPort> nodes  = new HashSet<>();
		nodes.add(new HostAndPort("192.168.164.132", 7001));
		nodes.add(new HostAndPort("192.168.164.132", 7002));
		nodes.add(new HostAndPort("192.168.164.132", 7003));
		nodes.add(new HostAndPort("192.168.164.132", 7004));
		nodes.add(new HostAndPort("192.168.164.132", 7005));
		nodes.add(new HostAndPort("192.168.164.132", 7006));
		//创建JedisCluster对象 在系统中通常是单例的
		//JedisCluster 无需使用连接池因为JedisCluster对象自带连接池
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("test", "100");
		String result = cluster.get("test");
		System.out.println(result);
		//在系统结束时关闭JedisCluster对象
		cluster.close();
	}
	@Test
	public void TestJedisClient(){
		ApplicationContext context  = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
		JedisClient client = context.getBean(JedisClient.class);
		//查看是使用的单机版的jedis客户端 还是 集群版的jedis客户端
		System.out.println(client.getClass());
		client.set("client", "1000");
		String value = client.get("client");
		System.out.println(value);
	}
}
