package io.fdlessard.codebites.caching;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;

import static java.util.concurrent.TimeUnit.MINUTES;


public class CustomerJCacheManagerCustomizer implements JCacheManagerCustomizer {
    @Override
    public void customize(CacheManager cacheManager) {
        cacheManager.createCache("Customer", new MutableConfiguration<>()
                .setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(MINUTES, 2)))
                .setStoreByValue(false)
                .setStatisticsEnabled(true));
    }
}

