package roon.cache.cachepractice.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import roon.cache.cachepractice.infra.cache.ExplicitCacheEvict;
import roon.cache.cachepractice.service.SampleService;

@RestController
public class SampleController {

	@Autowired
	private SampleService sampleService;

	@Autowired
	private ExplicitCacheEvict cacheEvict;

	@GetMapping("/without-caching")
	public String noCaching(int limit) {
		return sampleService.calculateHeavyWithoutCaching(limit);
	}

	@GetMapping("/with-caching")
	public String caching(int limit) {
		return sampleService.calculateHeavyWithCaching(limit);
	}

	//	@PostMapping("/evict-cache")
	@GetMapping("/clear-cache")
	public void evict(String cacheName) {
		cacheEvict.clear(cacheName);
	}

}
