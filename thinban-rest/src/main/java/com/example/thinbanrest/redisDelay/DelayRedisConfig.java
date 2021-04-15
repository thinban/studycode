package com.example.thinbanrest.redisDelay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class DelayRedisConfig {
    /**
     * Redis 消息监听器容器.
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis message listener container
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        return redisMessageListenerContainer;
    }


    /**
     * Redis 定时任务监听器注册为Bean.
     *
     * @param redisMessageListenerContainer the redis message listener container
     * @return the redis event message listener
     */
    @Bean
    public RedisJobEventMessageListener redisEventMessageListener(RedisMessageListenerContainer redisMessageListenerContainer) {
        return new RedisJobEventMessageListener(redisMessageListenerContainer);
    }
}
