package roon.cache.cachepractice.infra.cache;

import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;

@Getter
public class CacheValue implements Serializable {
	private String value;

	public static CacheValue newOne(String value){
		return new CacheValue(value);
	}

	private CacheValue(String value){
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		CacheValue otherValue = (CacheValue) obj;

		return otherValue.value.equals(value);
 	}

	@Override
	public String toString() {
		return value;
	}
}
