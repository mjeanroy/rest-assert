/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2026 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.data;

import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderParser;
import com.github.mjeanroy.restassert.core.internal.data.HttpHeaderValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/// Cache-Control value value as specified
/// by [RFC 7234](https://tools.ietf.org/html/rfc7234).
public final class CacheControl implements HttpHeaderValue {

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
	private static final String DIR_IMMUTABLE = "immutable";

	/// The parser instance.
	private static final CacheControlParser PARSER = new CacheControlParser();

	/// Get parser for [CacheControl] instances.
	///
	/// @return The parser.
	public static HttpHeaderParser<CacheControl> parser() {
		return PARSER;
	}

	/// Create new builder for [CacheControl].
	///
	/// @return The builder.
	public static CacheControlBuilder builder() {
		return new CacheControlBuilder();
	}

	/// Cache-Control visibility.
	public enum Visibility {
		/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.6"):
		///
		/// The `private` response directive indicates that the response message
		/// is intended for a single user and MUST NOT be stored by a shared
		/// cache.
		///
		/// A private cache MAY store the response and reuse it for later
		/// requests, even if the response would normally be non-cacheable.
		PRIVATE,

		/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.5):
		///
		/// The `public` response directive indicates that any cache MAY store
		/// the response, even if the response would normally be non-cacheable or
		/// cacheable only within a private cache.
		PUBLIC
	}

	/// Append `"visibility"` directive.
	///
	/// Currently, two choices:
	/// - `private`
	/// - `public`
	private final Visibility visibility;

	/// Append `no-store` directive to the value value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.3):
	///
	/// The `"no-store"` response directive indicates that a cache MUST NOT
	/// store any part of either the immediate request or response.
	///
	/// This directive applies to both private and shared caches.
	///
	/// "MUST NOT store" in this context means that the cache MUST NOT intentionally
	/// store the information in non-volatile storage, and MUST make a
	/// best-effort attempt to remove the information from volatile storage
	/// as promptly as possible after forwarding it.
	private final boolean noStore;

	/// Append `no-cache` directive to the value value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.2):
	///
	/// The `"no-cache"` response directive indicates that the response MUST
	/// NOT be used to satisfy a subsequent request without successful
	/// validation on the origin server.
	///
	/// This allows an origin server to prevent a cache from using it to
	/// satisfy a request without contacting it, even by caches that have
	/// been configured to send stale responses.
	private final boolean noCache;

	/// Append `max-age` directive to the value value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.8):
	///
	/// The `"max-age"` response directive indicates that the response is to be
	/// considered stale after its age is greater than the specified number
	/// of seconds.
	///
	/// This directive uses the token form of the argument syntax: e.g.,
	/// 'max-age=5' not 'max-age="5"'.
	///
	/// A sender SHOULD NOT generate the quoted-string form.
	private final Long maxAge;

	/// Append `s-maxage` directive to the value value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.9):
	///
	/// The `s-maxage` response directive indicates that, in shared caches,
	/// the maximum age specified by this directive overrides the maximum age
	/// specified by either the max-age directive or the Expires value
	/// field.
	///
	/// The `s-maxage` directive also implies the semantics of the
	/// proxy-revalidate response directive.
	private final Long sMaxAge;

	/// Append `no-transform` directive to the value value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.4):
	///
	/// The `no-transform` response directive indicates that an intermediary
	/// (regardless of whether it implements a cache) MUST NOT transform the
	/// payload.
	private final boolean noTransform;

	/// Append `must-revalidate` directive to the value value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.1):
	///
	/// The `must-revalidate` response directive indicates that once it has
	/// become stale, a cache MUST NOT use the response to satisfy subsequent
	/// requests without successful validation on the origin server.
	private final boolean mustRevalidate;

	/// Append `proxy-revalidate` directive to the header value.
	///
	/// As specified by [RFC 7234](https://tools.ietf.org/html/rfc7234#section-5.2.2.1):
	///
	/// The `proxy-revalidate` response directive has the same meaning as the
	/// `must-revalidate` response directive, except that it does not apply to
	/// private caches.
	private final boolean proxyRevalidate;

	/// Append `immutable` directive to the header value.
	///
	/// As specified by [RFC 8246](https://tools.ietf.org/html/rfc8246):
	///
	/// When present in an HTTP response, the `immutable` `Cache-Control``
	/// extension indicates that the origin server will not update the
	/// representation of that resource during the freshness lifetime of the
	/// response.
	private final boolean immutable;

	CacheControl(
		Visibility visibility,
		boolean noStore,
		boolean noCache,
		boolean noTransform,
		boolean mustRevalidate,
		boolean proxyRevalidate,
		Long maxAge,
		Long sMaxAge,
		boolean immutable
	) {
		this.visibility = visibility;
		this.noCache = noCache;
		this.noStore = noStore;
		this.maxAge = maxAge;
		this.sMaxAge = sMaxAge;
		this.noTransform = noTransform;
		this.mustRevalidate = mustRevalidate;
		this.proxyRevalidate = proxyRevalidate;
		this.immutable = immutable;
	}

	/// Get [#visibility]
	///
	/// @return Returns [#visibility]
	public Visibility getVisibility() {
		return visibility;
	}

	/// Get [#noCache]
	///
	/// @return Returns [#noCache]
	public boolean isNoCache() {
		return noCache;
	}

	/// Get [#noStore]
	///
	/// @return Returns [#noStore]
	public boolean isNoStore() {
		return noStore;
	}

	/// Get [#maxAge]
	///
	/// @return Returns [#maxAge]
	public Long getMaxAge() {
		return maxAge;
	}

	/// Get [#sMaxAge]
	///
	/// @return Returns [#sMaxAge]
	public Long getSMaxAge() {
		return sMaxAge;
	}

	/// Get [#noTransform]
	///
	/// @return Returns [#noTransform]
	public boolean isNoTransform() {
		return noTransform;
	}

	/// Get [#mustRevalidate]
	///
	/// @return Returns [#mustRevalidate]
	public boolean isMustRevalidate() {
		return mustRevalidate;
	}

	/// Get [#proxyRevalidate]
	///
	/// @return Returns [#proxyRevalidate]
	public boolean isProxyRevalidate() {
		return proxyRevalidate;
	}

	/// Get [#immutable]
	///
	/// @return Returns [#immutable]
	public boolean isImmutable() {
		return immutable;
	}

	@Override
	public String serializeValue() {
		List<String> values = new ArrayList<>(10);

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

		if (immutable) {
			values.add(DIR_IMMUTABLE);
		}

		return String.join(SEPARATOR, values);
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
				&& Objects.equals(maxAge, c.maxAge)
				&& Objects.equals(immutable, c.immutable);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(visibility, noStore, noCache, noTransform, mustRevalidate, proxyRevalidate, maxAge, sMaxAge, immutable);
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("visibility", visibility)
			.append("noCache", noCache)
			.append("noStore", noStore)
			.append("noTransform", noTransform)
			.append("mustRevalidate", mustRevalidate)
			.append("proxyRevalidate", proxyRevalidate)
			.append("maxAge", maxAge)
			.append("sMaxAge", sMaxAge)
			.append("immutable", immutable)
			.build();
	}

	enum Directive {
		VISIBILITY {
			@Override
			boolean match(String value) {
				return value.equals(DIR_PRIVATE) || value.equals(DIR_PUBLIC);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
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
			void setValue(String value, CacheControlBuilder builder) {
				builder.noCache();
			}
		},

		NO_STORE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_NO_STORE);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				builder.noStore();
			}
		},

		NO_TRANSFORM {
			@Override
			boolean match(String value) {
				return value.equals(DIR_NO_TRANSFORM);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				builder.noTransform();
			}
		},

		MUST_REVALIDATE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_MUST_REVALIDATE);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				builder.mustRevalidate();
			}
		},

		PROXY_REVALIDATE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_PROXY_REVALIDATE);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				builder.proxyRevalidate();
			}
		},

		MAX_AGE {
			@Override
			boolean match(String value) {
				return value.startsWith(DIR_MAX_AGE);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				int maxAge = Integer.parseInt(value.split("=")[1].trim());
				builder.maxAge(maxAge);
			}
		},

		S_MAX_AGE {
			@Override
			boolean match(String value) {
				return value.startsWith(DIR_S_MAX_AGE);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				int maxAge = Integer.parseInt(value.split("=")[1].trim());
				builder.sMaxAge(maxAge);
			}
		},

		IMMUTABLE {
			@Override
			boolean match(String value) {
				return value.equals(DIR_IMMUTABLE);
			}

			@Override
			void setValue(String value, CacheControlBuilder builder) {
				builder.immutable();
			}
		};

		/// Check if raw value match given directive definition.
		///
		/// @param value Raw value.
		/// @return `true` if `value` is a value defining directive, `false` otherwise.
		abstract boolean match(String value);

		/// Parse and update current value in [CacheControlBuilder].
		///
		/// @param value The value to parse and set.
		/// @param builder The builder.
		abstract void setValue(String value, CacheControlBuilder builder);
	}
}
