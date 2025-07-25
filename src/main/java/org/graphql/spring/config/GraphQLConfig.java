package org.graphql.spring.config;

import java.time.Duration;
import java.util.Map;

import org.graphql.spring.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
public class GraphQLConfig {
	
	@Value("${spring.datasource.url}")
	String databaseUrl;
	
	@Value("${spring.datasource.username}")
	String databaseUserName;
	
	@Value("${spring.datasource.password}")
	String databasePassword;
	
	@Value("${spring.datasource.DriverClassName}")
	String driverClassName;
	
	@Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;
		
	private static final Logger log = LoggerFactory.getLogger(GraphQLConfig.class);
	
	@Bean(name="dataSource")
	public DriverManagerDataSource dataSource() {
//		log.info("databaseUrl:: "+databaseUrl);
//		log.info("databaseUserName:: "+databaseUserName);
//		log.info("databasePassword:: "+databasePassword);
//		log.info("driverClassName:: "+driverClassName);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUserName);
		dataSource.setPassword(databasePassword);
		return dataSource;
	}
	
	@Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
//          log.info("redisHost:: "+redisHost);
//  		log.info("redisPort:: "+redisPort);
//  		log.info("redisPassword:: "+redisPassword);
      	config.setHostName(redisHost);
        config.setPort(redisPort);
        if (!redisPassword.isEmpty()) {
            config.setPassword(RedisPassword.of(redisPassword));
        }
        return new LettuceConnectionFactory(config);
    }
	
	@Bean
	@Primary
    public ObjectMapper redisObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
	 
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory,
														ObjectMapper redisObjectMapper) {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(redisObjectMapper));
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
    
	 @Bean
     public CacheManager cacheManager(LettuceConnectionFactory redisConnectionFactory,
    		 														ObjectMapper redisObjectMapper) {
        try {
            redisConnectionFactory.getConnection().ping();
            log.info("Redis server is available, using RedisCacheManager");
            
            GenericJackson2JsonRedisSerializer genericSerializer =
                    new GenericJackson2JsonRedisSerializer(redisObjectMapper);
            
            RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.
                		fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.
                		fromSerializer(genericSerializer));
            
            Jackson2JsonRedisSerializer<CustomerDto> customerSerializer =
                    new Jackson2JsonRedisSerializer<>(redisObjectMapper, CustomerDto.class);
            
            RedisCacheConfiguration customerCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(15))
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.
                    		fromSerializer(new StringRedisSerializer()))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.
                    		fromSerializer(customerSerializer));
            
            return RedisCacheManager.builder(redisConnectionFactory)
                    .cacheDefaults(defaultConfig)
                    .withInitialCacheConfigurations(Map.of("customer", customerCacheConfig))
                    .transactionAware()
                    .build();
        } catch (Exception ex) {
            log.error("Redis server unavailable, falling back to No Cache Manager: {}", ex.getMessage());
            return new NoOpCacheManager();
        }
	 }

	
    @Bean
    public CacheErrorHandler cacheErrorHandler() {
        return new CacheErrorHandler() {
			@Override
			public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
				log.warn("Cache get error for key: {}", key, exception);
			}
			@Override
			public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
				log.warn("Cache put error for key: {}", key, exception);
			}
			@Override
			public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
				log.warn("Cache evic error for key: {}", key, exception);
			}
			@Override
			public void handleCacheClearError(RuntimeException exception, Cache cache) {
				log.warn("Cache clear error", exception);
			}
        };
    }
    
}
