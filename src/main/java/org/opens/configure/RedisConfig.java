package org.opens.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

//@Configuration
public class RedisConfig {

    private RedisConnectionFactory redisConnectionFactory;

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory initRedisConnectionFactory() throws Exception {
        if(redisConnectionFactory != null) {
            return redisConnectionFactory;
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大空闲数
        jedisPoolConfig.setMaxIdle(30);
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(50);
        //设置最大等待毫秒数
        jedisPoolConfig.setMaxWaitMillis(2000);
        //创建jedis连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        //获取单机Redis配置
        RedisStandaloneConfiguration standaloneConfiguration = connectionFactory.getStandaloneConfiguration();
        if(standaloneConfiguration != null) {
            standaloneConfiguration.setHostName("127.0.0.1");
            standaloneConfiguration.setPort(6379);
            standaloneConfiguration.setPassword("");
            this.redisConnectionFactory = connectionFactory;
            return connectionFactory;
        } else {
            throw new Exception("redis配置信息出错, 请检查redis配置信息");
        }
    }

    //RedisTemplate可以想象是Rruid数据库连接池, 会在使用完之后自动关闭数据库连接, 真正作用的还是redisConnectionFactory
    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> initRedisTemplate() throws Exception {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        //配置序列化其实key变为常规字符串
        RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

}
