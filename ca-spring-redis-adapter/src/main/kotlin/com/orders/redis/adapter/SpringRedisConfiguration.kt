package com.orders.redis.adapter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories
@PropertySource("classpath:ca-orders-redis-adapter.properties")
class SpringRedisConfiguration {

    @Bean
    fun connectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory()
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<*,*> {
        return RedisTemplate<ByteArray, ByteArray>().apply {
            this.connectionFactory = connectionFactory
        }
    }
}