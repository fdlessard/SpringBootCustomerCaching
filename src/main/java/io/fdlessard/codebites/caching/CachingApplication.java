package io.fdlessard.codebites.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class CachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingApplication.class, args);
    }


    @Bean
    public CustomerKeyGenerator customerKeyGenerator() {
        // configure and return an implementation of Spring's KeyGenerator SPI
        return new CustomerKeyGenerator();
    }


    @Bean
    public CustomerJCacheManagerCustomizer cacheManagerCustomizer() {
        return new CustomerJCacheManagerCustomizer();
    }
}





