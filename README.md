# Spring Boot Redis Caching (Single Class – Manual Caching)

This project demonstrates how to use Redis with Spring Boot for manual caching using RedisTemplate. The entire caching logic, Redis configuration, and REST APIs are implemented in a single class for simplicity and learning purposes. Spring cache annotations such as @Cacheable, @CacheEvict, and @CachePut are not used.

The goal of this project is to understand how Redis works internally with Spring Boot, how data is stored and retrieved from Redis, and how TTL (Time-To-Live) helps in automatic cache expiration to improve application performance.

Technologies used in this project include Java, Spring Boot, Redis, Lettuce Redis client, RedisTemplate, and REST APIs.

To run this project, make sure Redis is running on localhost at port 6379. Redis can be started using Docker with the command:
docker run -d -p 6379:6379 redis

The project uses the spring-boot-starter-data-redis dependency to connect and interact with Redis.

All logic is implemented in a single class as shown below:

@SpringBootApplication
@RestController
@RequestMapping("/cache")
public class RedisCachingApplication {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(
                new Jackson2JsonRedisSerializer<>(Object.class)
        );
        return template;
    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/save")
    public String save(@RequestParam String key,
                       @RequestParam String value,
                       @RequestParam long ttl) {
        redisTemplate.opsForValue()
                .set(key, value, ttl, TimeUnit.SECONDS);
        return "Saved to cache";
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @DeleteMapping("/remove")
    public String remove(@RequestParam String key) {
        redisTemplate.delete(key);
        return "Removed from cache";
    }

    @GetMapping("/exists")
    public boolean exists(@RequestParam String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisCachingApplication.class, args);
    }
}

The following REST endpoints can be used to test the cache:
POST /cache/save?key=test&value=hello&ttl=60  
GET  /cache/get?key=test  
DELETE /cache/remove?key=test  
GET  /cache/exists?key=test  

Best practices followed in this project include using meaningful cache keys, setting TTL for all cache entries, avoiding sensitive data in cache, and keeping cache logic simple and clear.

This project is intended for learning, demos, and interview preparation. In real production systems, Redis configuration, services, and controllers should be separated into different classes and packages.
