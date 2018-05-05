/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.internal.data.HeaderValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.mjeanroy.restassert.core.internal.common.Numbers.toLong;
import static java.util.Collections.unmodifiableMap;

/**
 * Strict-Transport-Security value.
 * Note that this object handle {@code preload} directive, even if
 * it is not officially defined in the spec.
 *
 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
 * @see <a href="https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security">https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security</a>
 */
public final class StrictTransportSecurity implements HeaderValue {

	/**
	 * The parser instance.
	 */
	private static final StrictTransportSecurityParser PARSER = new StrictTransportSecurityParser();

	/**
	 * Get parser for {@link StrictTransportSecurity} instances.
	 *
	 * @return The parser.
	 */
	public static StrictTransportSecurityParser parser() {
		return PARSER;
	}

	/**
	 * Create new builder for {@link StrictTransportSecurity}.
	 *
	 * @param maxAge The max-age value.
	 * @return The builder.
	 */
	public static StrictTransportSecurityBuilder builder(long maxAge) {
		return new StrictTransportSecurityBuilder(maxAge);
	}

	/**
	 * Max-Age value.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc6797#page-16">https://tools.ietf.org/html/rfc6797#page-16</a>
	 */
	private final long maxAge;

	/**
	 * Flag for sub-domains inclusion.
	 *
	 * @see <a href="https://tools.ietf.org/html/rfc6797#section-6.1.2">https://tools.ietf.org/html/rfc6797#section-6.1.2</a>
	 */
	private final boolean includeSubDomains;

	/**
	 * Preload flag.
	 * Not official defined by RFC 6797.
	 *
	 * @see <a href="https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security">https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security</a>
	 */
	private final boolean preload;

	/**
	 * Create value value.
	 *
	 * @param maxAge Max-Age value.
	 * @param includeSubDomains Sub-Domains flag.
	 * @param preload Preload flag.
	 */
	StrictTransportSecurity(long maxAge, boolean includeSubDomains, boolean preload) {
		this.maxAge = maxAge;
		this.includeSubDomains = includeSubDomains;
		this.preload = preload;
	}

	/**
	 * Get {@link #maxAge}
	 *
	 * @return {@link #maxAge}
	 */
	public long getMaxAge() {
		return maxAge;
	}

	/**
	 * Get {@link #preload}
	 *
	 * @return {@link #preload}
	 */
	public boolean isPreload() {
		return preload;
	}

	/**
	 * Get {@link #includeSubDomains}
	 *
	 * @return {@link #includeSubDomains}
	 */
	public boolean isIncludeSubDomains() {
		return includeSubDomains;
	}

	@Override
	public String serializeValue() {
		StringBuilder sb = new StringBuilder();
		sb.append("max-age=").append(maxAge);

		if (includeSubDomains) {
			sb.append("; includeSubDomains");
		}

		if (preload) {
			sb.append("; preload");
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return ToStringBuilder.toStringBuilder(getClass())
			.append("maxAge", maxAge)
			.append("includeSubDomains", includeSubDomains)
			.append("preload", preload)
			.build();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof StrictTransportSecurity) {
			StrictTransportSecurity sts = (StrictTransportSecurity) o;
			return maxAge == sts.maxAge
					&& includeSubDomains == sts.includeSubDomains
					&& preload == sts.preload;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maxAge, includeSubDomains, preload);
	}

	/**
	 * Set of directive that may appear in {@code Strict-Transport-Security} value.
	 */
	enum Directive {
		/**
		 * Max-Age directive.
		 */
		MAX_AGE("max-age") {
			@Override
			void parse(String value, StrictTransportSecurityBuilder builder) {
				String nb = value;

				// Value may be quoted
				if (nb.length() > 2) {
					char firstChar = value.charAt(0);
					char lastChar = value.charAt(value.length() - 1);
					if (firstChar == '"' && firstChar == lastChar) {
						nb = value.substring(1, value.length() - 2);
					}
				}

				builder.maxAge(toLong(nb, String.format("Max-Age '%s' is not a valid number", value)));
			}
		},

		/**
		 * {@code includeSubDomains} directive.
		 */
		INCLUDE_SUB_DOMAINS("includeSubDomains") {
			@Override
			void parse(String value, StrictTransportSecurityBuilder builder) {
				builder.includeSubDomains();
			}
		},

		/**
		 * {@code preload} directive.
		 */
		PRELOAD("preload") {
			@Override
			void parse(String value, StrictTransportSecurityBuilder builder) {
				builder.preload();
			}
		};

		/**
		 * Name of directive.
		 */
		private final String name;

		Directive(String name) {
			this.name = name;
		}

		abstract void parse(String value, StrictTransportSecurityBuilder builder);

		private static final Map<String, Directive> map;

		static {
			Map<String, Directive> index = new HashMap<>();
			for (Directive directive : Directive.values()) {
				index.put(directive.name.toLowerCase(), directive);
			}

			map = unmodifiableMap(index);
		}

		/**
		 * Get directive from its name. As specified by RFC 6797, directive name
		 * is case-insensitive.
		 *
		 * @param name Directive name.
		 * @return Directive object, may be {@code null} if name is not found.
		 */
		static Directive byName(String name) {
			return map.get(name.toLowerCase());
		}
	}

}
