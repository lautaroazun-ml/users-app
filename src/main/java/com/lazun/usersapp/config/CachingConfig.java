package com.lazun.usersapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CachingConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(CachingConfig.class);

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager("fee");
  }

  @CacheEvict(value = "fee", allEntries = true)
  @Scheduled(fixedRateString = "${caching.feeTTL}")
  public void emptyHotelsCache() {
    LOGGER.info("emptying fee cache");
  }
}
