package com.github.mjeanroy.rest_assert.internal.json.comparators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.addAll;
import static java.util.Collections.unmodifiableSet;

/**
 * Options that can be given to any
 * json comparator implementations.
 * <p/>
 * Use {@link Builder} to
 * create new options instance.
 */
public class JsonComparatorOptions {

	/**
	 * Get builder instance.
	 *
	 * @return Builder instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Set of keys to ignore during comparison.
	 * If an error occurred on one of theses keys, these errors
	 * will be ignored.
	 */
	private final Set<String> ignoringKeys;

	/**
	 * Create new options.
	 *
	 * @param ignoringKeys Set of keys to ignore.
	 */
	private JsonComparatorOptions(Collection<String> ignoringKeys) {
		this.ignoringKeys = new HashSet<>(ignoringKeys);
	}

	/**
	 * Get keys to ignore.
	 *
	 * @return Keys.
	 */
	public Set<String> getIgnoringKeys() {
		return unmodifiableSet(ignoringKeys);
	}

	// Builder
	public static final class Builder {
		private final Set<String> ignoringKeys;

		private Builder() {
			this.ignoringKeys = new HashSet<>();
		}

		public Builder ignoreKey(String key) {
			ignoringKeys.add(key);
			return this;
		}

		public Builder ignoreKeys(String[] keys) {
			addAll(ignoringKeys, keys);
			return this;
		}

		public Builder ignoreKeys(Iterable<String> keys) {
			for (String key : keys) {
				ignoringKeys.add(key);
			}

			return this;
		}

		public JsonComparatorOptions build() {
			return new JsonComparatorOptions(ignoringKeys);
		}
	}
}
