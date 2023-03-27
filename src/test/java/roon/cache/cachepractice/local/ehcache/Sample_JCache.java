package roon.cache.cachepractice.local.ehcache;


import javax.cache.Cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

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

}
