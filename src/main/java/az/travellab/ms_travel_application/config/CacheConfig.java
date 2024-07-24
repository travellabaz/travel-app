//package az.travellab.ms_travel_application.config;
//
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.caffeine.CaffeineCache;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Collections;
//
//@Configuration
//@EnableCaching
//public class CacheConfig {
//    @Bean
//    public CaffeineCache caffeineCacheConfig() {
//        return new CaffeineCache("citiesCache", Caffeine.newBuilder()
////                .expireAfterWrite(Duration.ofMinutes(1))
//                .initialCapacity(1)
//                .maximumSize(2000)
//                .build());
//    }
//
//    @Bean
//    public CacheManager caffeineCacheManager(CaffeineCache caffeineCache) {
//        SimpleCacheManager manager = new SimpleCacheManager();
//        manager.setCaches(Collections.singletonList(caffeineCache));
//        return manager;
//    }
//}
