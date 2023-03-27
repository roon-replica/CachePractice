package roon.cache.cachepractice.infra.cache;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CacheKey implements Serializable {

	private String strKey;
	private Long longKey;

	public static CacheKey newOne(String key) {
		return new CacheKey(key);
	}

	public static CacheKey newOne(Long key) {
		return new CacheKey(key);
	}

	private CacheKey(String key) {
		this.strKey = key;
	}

	private CacheKey(Long key) {
		this.longKey = key;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(strKey + (longKey == null ? "" : longKey.toString()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CacheKey otherKey = (CacheKey) obj;
		return otherKey.longKey.equals(this.longKey) && otherKey.strKey.equals(this.strKey);
	}

	@Override
	public String toString() {
		return strKey + " " + longKey;
	}
}
