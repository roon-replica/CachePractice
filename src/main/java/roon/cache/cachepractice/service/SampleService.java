package roon.cache.cachepractice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleService {

	@Cacheable(cacheNames = "sampleCache", key = "#limit")
	public String calculateHeavyWithCaching(int limit) {
		log.info("this log means not returning cached data.");

		long sum = 0;
		for (int i = 0; i < limit; i++) {
			sum += (i % 2 == 0 ? -i : i);
		}

		return String.valueOf(sum);
	}

	public String calculateHeavyWithoutCaching(int limit) {
		long sum = 0;
		for (int i = 0; i < limit; i++) {
			sum += (i % 2 == 0 ? -i : i);
		}

		return String.valueOf(sum);
	}
}
