package com.tanmay.buyit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig {

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules(); // handles Java time etc.

            Jackson2JsonRedisSerializer<Object> serializer =
                    new Jackson2JsonRedisSerializer<>(mapper, Object.class);

            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                    .serializeKeysWith(
                            RedisSerializationContext.SerializationPair.fromSerializer(
                                    new StringRedisSerializer()
                            )
                    )
                    .serializeValuesWith(
                            RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                    )
                    .entryTtl(Duration.ofHours(1));

            return RedisCacheManager.builder(connectionFactory)
                    .cacheDefaults(config)
                    .build();
        }
    }
