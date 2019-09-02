package org.opens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@EnableJpaRepositories(basePackages = "org.opens.jpa.dao")
@EntityScan(basePackages = "org.opens.jpa.pojo")
@SpringBootApplication
@EnableTransactionManagement
public class SpringbootTestApplication {

    @Autowired
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTestApplication.class, args);
    }

    /**
     * 简介:
     *      使用spring boot项目时它会自动帮你构建RedisConnectionFactory对象以及RedisTempleate对象, 但是并不会帮我们
     *      自动配置序列化其, 依旧使用的Jdk的那个序列化器, 此时就需要我们使用后初始化方法自己进行设置序列化器.
     */
    @PostConstruct
    public void init() {
        initRedisTemplate();
    }

    public void initRedisTemplate() {

        //配置序列化其实key变为常规字符串
        if(redisTemplate != null) {
            RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
            redisTemplate.setKeySerializer(stringRedisSerializer);
            redisTemplate.setHashKeySerializer(stringRedisSerializer);
            redisTemplate.setHashValueSerializer(stringRedisSerializer);
            System.out.println("redisTempleate初始化成功" + redisTemplate);
        }

    }

}
