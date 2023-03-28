package roon.cache.cachepractice.config.cache.local.ehcache;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.impl.copy.ReadWriteCopier;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Slf4j
@EnableCaching
@Configuration
public class CacheConfig implements CachingConfigurer {

	private static final String CACHE_KEY_DELIMITER = ":";

	@Override
	@Bean
	public CacheManager cacheManager() {
		Map<String, CacheConfiguration<?, ?>> cacheConfigMap = new HashMap<>();

		CacheConfiguration<Object, Object> cacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(
						Object.class,
						Object.class,
						ResourcePoolsBuilder.heap(10))
				.withValueCopier(new CloneCopier<>())
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(1)))
				.withEvictionAdvisor((key, value) -> false)
				.build();

		cacheConfigMap.put("sampleCache", cacheConfiguration);

		EhcacheCachingProvider provider = new EhcacheCachingProvider();
		org.ehcache.config.Configuration configuration = new DefaultConfiguration(cacheConfigMap, provider.getDefaultClassLoader());

		return new JCacheCacheManager(provider.getCacheManager(provider.getDefaultURI(), configuration));
	}

	private class CloneCopier<T> extends ReadWriteCopier<T> {

		@Override
		public T copy(T obj) {
			log.info("[Clone Copier] object name = " + obj.getClass().getSimpleName());
			if (obj instanceof Cloneable) {
				try {
					Method cloneMethod = obj.getClass().getMethod("clone");
					return (T) cloneMethod.invoke(obj);
				} catch (Exception e) {
				}
			}

			return obj;
		}
	}

	// 얘 동작안하는거 같은데..?
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return (target, method, params) -> StringUtils.arrayToDelimitedString(params, CACHE_KEY_DELIMITER);
	}
}
