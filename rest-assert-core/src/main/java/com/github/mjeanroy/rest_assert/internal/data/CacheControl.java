/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.rest_assert.internal.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.github.mjeanroy.rest_assert.utils.Utils.join;

/**
 * Cache-Control header value as specified by RFC 7234 (https://tools.ietf.org/html/rfc7234).
 */
public class CacheControl implements HeaderValue {

	private static final String SEPARATOR = ", ";
	private static final String DIR_NO_CACHE = "no-cache";
	private static final String DIR_NO_STORE = "no-store";
	private static final String DIR_NO_TRANSFORM = "no-transform";
	private static final String DIR_MUST_REVALIDATE = "must-revalidate";
	private static final String DIR_PROXY_REVALIDATE = "proxy-revalidate";
	private static final String DIR_MAX_AGE = "max-age=";
	private static final String DIR_S_MAX_AGE = "s-maxage=";
	private static final String DIR_PRIVATE = "private";
	private static final String DIR_PUBLIC = "public";

	public enum Visibility {
		/**
		 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.5):
		 *
		 * The "private" response directive indicates that the response message
		 * is intended for a single user and MUST NOT be stored by a shared
		 * cache.  A private cache MAY store the response and reuse it for later
		 * requests, even if the response would normally be non-cacheable.
		 */
		PRIVATE,

		/**
		 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.6):
		 *
		 * The "public" response directive indicates that any cache MAY store
		 * the response, even if the response would normally be non-cacheable or
		 * cacheable only within a private cache.
		 */
		PUBLIC
	}

	/**
	 * Append visibility directive.
	 * Currently, two choices: {@code private} or {@code public}.
	 */
	private final Visibility visibility;

	/**
	 * Append {@code no-store} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.3):
	 *
	 * The "no-store" response directive indicates that a cache MUST NOT
	 * store any part of either the immediate request or response.  This
	 * directive applies to both private and shared caches.  "MUST NOT
	 * store" in this context means that the cache MUST NOT intentionally
	 * store the information in non-volatile storage, and MUST make a
	 * best-effort attempt to remove the information from volatile storage
	 * as promptly as possible after forwarding it.
	 */
	private final boolean noStore;

	/**
	 * Append {@code no-cache} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.2):
	 *
	 * The "no-cache" response directive indicates that the response MUST
	 * NOT be used to satisfy a subsequent request without successful
	 * validation on the origin server.  This allows an origin server to
	 * prevent a cache from using it to satisfy a request without contacting
	 * it, even by caches that have been configured to send stale responses.
	 */
	private final boolean noCache;

	/**
	 * Append {@code max-age} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.8):
	 *
	 * The "max-age" response directive indicates that the response is to be
	 * considered stale after its age is greater than the specified number
	 * of seconds.
	 * This directive uses the token form of the argument syntax: e.g.,
	 * 'max-age=5' not 'max-age="5"'.  A sender SHOULD NOT generate the
	 * quoted-string form.
	 */
	private final Long maxAge;

	/**
	 * Append {@code s-maxage} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.9):
	 *
	 * The "s-maxage" response directive indicates that, in shared caches,
	 * the maximum age specified by this directive overrides the maximum age
	 * specified by either the max-age directive or the Expires header
	 * field.  The s-maxage directive also implies the semantics of the
	 * proxy-revalidate response directive.
	 */
	private final Long sMaxAge;

	/**
	 * Append {@code no-transform} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.4):
	 *
	 * The "no-transform" response directive indicates that an intermediary
	 * (regardless of whether it implements a cache) MUST NOT transform the
	 * payload.
	 */
	private final boolean noTransform;

	/**
	 * Append {@code must-revalidate} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.1):
	 *
	 * The "must-revalidate" response directive indicates that once it has
	 * become stale, a cache MUST NOT use the response to satisfy subsequent
	 * requests without successful validation on the origin server.
	 */
	private final boolean mustRevalidate;

	/**
	 * Append {@code prox-revalidate} directive to the header value.
	 *
	 * As specified by RFC 7234 (https://tools.ietf.org/html/rfc7234#section-5.2.2.1):
	 *
	 * The "proxy-revalidate" response directive has the same meaning as the
	 * must-revalidate response directive, except that it does not apply to
	 * private caches.
	 */
	private final boolean proxyRevalidate;

	private CacheControl(
		Visibility visibility, boolean noStore, boolean noCache, boolean noTransform,
		boolean mustRevalidate, boolean proxyRevalidate, Long maxAge, Long sMaxAge) {

		this.visibility = visibility;
		this.noCache = noCache;
		this.noStore = noStore;
		this.maxAge = maxAge;
		this.sMaxAge = sMaxAge;
		this.noTransform = noTransform;
		this.mustRevalidate = mustRevalidate;
		this.proxyRevalidate = proxyRevalidate;
	}

	@Override
	public String value() {
		return toString();
	}

	@Override
	public boolean match(String actualValue) {
		String[] parts = actualValue.split(",");

		Builder builder = new Builder();
		for (String part : parts) {
			String value = part.trim();
			for (Directive directive : Directive.values()) {
				if (directive.match(value)) {
					directive.setValue(value, builder);
					break;
				}
			}
		}

		return this.equals(builder.build());
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof CacheControl) {
			CacheControl c = (CacheControl) o;
			return Objects.equals(visibility, c.visibility)
				&& Objects.equals(noStore, c.noStore)
				&& Objects.equals(noCache, c.noCache)
				&& Objects.equals(noTransform, c.noTransform)
				&& Objects.equals(mustRevalidate, c.mustRevalidate)
				&& Objects.equals(proxyRevalidate, c.proxyRevalidate)
				&& Objects.equals(sMaxAge, c.sMaxAge)
				&& Objects.equals(maxAge, c.maxAge);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(visibility, noStore, noCache, noTransform, mustRevalidate, proxyRevalidate, maxAge, sMaxAge);
	}

	@Override
	public String toString() {
		List<String> values = new LinkedList<>();

		if (visibility != null) {
			values.add(visibility.name().toLowerCase());
		}

		if (noCache) {
			values.add(DIR_NO_CACHE);
		}

		if (noStore) {
			values.add(DIR_NO_STORE);
		}

		if (noTransform) {
			values.add(DIR_NO_TRANSFORM);
		}

		if (mustRevalidate) {
			values.add(DIR_MUST_REVALIDATE);
		}

		if (proxyRevalidate) {
			values.add(DIR_PROXY_REVALIDATE);
		}

		if (maxAge != null) {
			values.add(DIR_MAX_AGE + maxAge);
		}

		if (sMaxAge != null) {
			values.add(DIR_S_MAX_AGE + sMaxAge);
		}

		return join(values, SEPARATOR);
	}

	/**
	 * Build {@link CacheControl} instance.
	 */
	public static class Builder {
		/**
		 * Visibility directive (i.e {@code public} or {@code private}.
		 */
		private Visibility visibility;

		/**
		 * Flag for {@code no-store} directive.
		 */
		private boolean noStore;

		/**
		 * Flag for {@code no-cache} directive.
		 */
		private boolean noCache;

		/**
		 * Flag for {@code no-transform} directive.
		 */
		private boolean noTransform;

		/**
		 * Flag for {@code must-revalidate} directive.
		 */
		private boolean mustRevalidate;

		/**
		 * Flag for {@code proxy-revalidate} directive.
		 */
		private boolean proxyRevalidate;

		/**
		 * Flag for {@code max-age} directive.
		 */
		private Long maxAge;

		/**
		 * Flag for {@code s-maxage} directive.
		 */
		private Long sMaxAge;

		public Builder() {
			this.noCache = false;
			this.noStore = false;
			this.noTransform = false;
			this.mustRevalidate = false;
			this.proxyRevalidate = false;
		}

		/**
		 * Update {@code public} or {@code private} directive.
		 *
		 * @param visibility New visibility.
		 * @return Current builder.
		 */
		public Builder visibility(Visibility visibility) {
			this.visibility = visibility;
			return this;
		}

		/**
		 * Enable {@code no-cache} directive.
		 *
		 * @return Current builder.
		 */
		public Builder noCache() {
			this.noCache = true;
			return this;
		}

		/**
		 * Enable {@code no-transform} directive.
		 *
		 * @return Current builder.
		 */
		public Builder noTransform() {
			this.noTransform = true;
			return this;
		}

		/**
		 * Enable {@code must-revalidate} directive.
		 *
		 * @return Current builder.
		 */
		public Builder mustRevalidate() {
			this.mustRevalidate = true;
			return this;
		}

		/**
		 * Enable {@code proxy-revalidate} directive.
		 *
		 * @return Current builder.
		 */
		public Builder proxyRevalidate() {
			this.proxyRevalidate = true;
			return this;
		}

		/**
		 * Enable {@code no-store} directive.
		 *
		 * @return Current builder.
		 */
		public Builder noStore() {
			this.noStore = true;
			return this;
		}

		/**
		 * Update {@code max-age} directive.
		 *
		 * @param maxAge Enable or disable {@code max-age} directive.
		 * @return Current builder.
		 */
		public Builder maxAge(long maxAge) {
			this.maxAge = maxAge;
			return this;
		}

		/**
		 * Update {@code s-maxage} directive.
		 *
		 * @param sMaxAge Enable or disable {@code s-maxage} directive.
		 * @return Current builder.
		 */
		public Builder sMaxAge(long sMaxAge) {
			this.sMaxAge = sMaxAge;
			return this;
		}

		/**
		 * Create new Cache-Control header object.
		 *
		 * @return Cache-Control value.
		 */
		public CacheControl build() {
			return new CacheControl(visibility, noStore, noCache, noTransform, mustRevalidate, proxyRevalidate, maxAge, sMaxAge);
		}
	}

	private enum Directive {
		VISIBILITY {
			@Override
			boolean match(String value) {
				return value.equals(DIR_PRIVATE) || value.equals(DIR_PUBLIC);
			}

			@Override
			void setValue(String value, Builder builder) {
				if (value.equals(DIR_PRIVATE)) {
					builder.visibility(Visibility.PRIVATE);
				}
				else {
					builder.visibility(Visibility.PUBLIC);
				}
			}
		},

		NO_CACHE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_NO_CACHE);
			}

			@Override
			void setValue(String value, Builder builder) {
				builder.noCache();
			}
		},

		NO_STORE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_NO_STORE);
			}

			@Override
			void setValue(String value, Builder builder) {
				builder.noStore();
			}
		},

		NO_TRANSFORM {
			@Override
			boolean match(String value) {
				return value.equals(DIR_NO_TRANSFORM);
			}

			@Override
			void setValue(String value, Builder builder) {
				builder.noTransform();
			}
		},

		MUST_REVALIDATE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_MUST_REVALIDATE);
			}

			@Override
			void setValue(String value, Builder builder) {
				builder.mustRevalidate();
			}
		},

		PROXY_REVALIDATE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_PROXY_REVALIDATE);
			}

			@Override
			void setValue(String value, Builder builder) {
				builder.proxyRevalidate();
			}
		},

		MAX_AGE {
			@Override
			boolean match(String value) {
				return value.startsWith(DIR_MAX_AGE);
			}

			@Override
			void setValue(String value, Builder builder) {
				int maxAge = Integer.valueOf(value.split("=")[1].trim());
				builder.maxAge(maxAge);
			}
		},

		S_MAX_AGE {
			@Override
			boolean match(String value) {
				return value.startsWith(DIR_S_MAX_AGE);
			}

			@Override
			void setValue(String value, Builder builder) {
				int maxAge = Integer.valueOf(value.split("=")[1].trim());
				builder.sMaxAge(maxAge);
			}
		};

		abstract boolean match(String value);

		abstract void setValue(String value, Builder builder);
	}
}
