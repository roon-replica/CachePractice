package roon.cache.cachepractice.local.ehcache;


import javax.cache.Cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.junit.jupiter.api.Test;
import roon.cache.cachepractice.infra.cache.CacheKey;
import roon.cache.cachepractice.infra.cache.CacheValue;

import static org.junit.jupiter.api.Assertions.*;

public class Sample_JCache {

	public static void main(String[] args) {
		CachingProvider cachingProvider = Caching.getCachingProvider(); // Retrieves the default CachingProvider implementation from the application’s classpath
		CacheManager cacheManager = cachingProvider.getCacheManager();  // Retrieve the default CacheManager instance using the provider.
		MutableConfiguration<Long, String> configuration = new MutableConfiguration<Long, String>() // Create a cache configuration
				.setTypes(Long.class, String.class)
				.setStoreByValue(false)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));

		Cache<Long, String> cache = cacheManager.createCache("jCache", configuration); // Using the cache manager, create a cache named "jCache"
		cache.put(1L, "one");
		String value = cache.get(1L);
		System.out.println(value);
	}

	@Test
	public void createPreConfiguredEhCacheAndCreateAnotherCache(){
		final String PRE_CONFIGURED_CACHE_NAME = "preConfigured";

		org.ehcache.CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache(PRE_CONFIGURED_CACHE_NAME,
						CacheConfigurationBuilder.newCacheConfigurationBuilder(CacheKey.class, CacheValue.class, ResourcePoolsBuilder.heap(10))
				).build();
		cacheManager.init();
		org.ehcache.Cache<CacheKey, CacheValue> preConfiguredCache = cacheManager.getCache(PRE_CONFIGURED_CACHE_NAME, CacheKey.class, CacheValue.class);

		org.ehcache.Cache<CacheKey, CacheValue> myCache = cacheManager.createCache("myCache",
				CacheConfigurationBuilder.newCacheConfigurationBuilder(CacheKey.class, CacheValue.class, ResourcePoolsBuilder.heap(10))
		);

		CacheKey sampleKey = CacheKey.newOne("sample-key");
		CacheValue sampleValue = CacheValue.newOne("sample-value");
		myCache.put(sampleKey, sampleValue);
		CacheValue retrievedValue = myCache.get(sampleKey);

		System.out.println(retrievedValue+" "+sampleValue);
		assertEquals(retrievedValue, sampleValue);

	}

}
