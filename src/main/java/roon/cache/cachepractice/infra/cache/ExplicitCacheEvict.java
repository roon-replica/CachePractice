package roon.cache.cachepractice.infra.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class ExplicitCacheEvict {
	@Autowired
	private CacheManager cacheManager;

	public void evict(String cacheName, Object key){
		Cache cache = cacheManager.getCache(cacheName);
		assert cache != null;

		cache.evict(key);
	}

	public void clear(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		assert cache != null;

		cache.clear();
	}
}
