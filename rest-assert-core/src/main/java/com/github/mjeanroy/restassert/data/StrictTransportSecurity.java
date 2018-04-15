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

package com.github.mjeanroy.restassert.data;

import static com.github.mjeanroy.restassert.utils.Utils.toLong;
import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.github.mjeanroy.restassert.internal.loggers.Logger;
import com.github.mjeanroy.restassert.internal.loggers.Loggers;

/**
 * Strict-Transport-Security header.
 * Note that this object handle {@code preload} directive, even if
 * it is not officially defined in the spec.
 *
 * @see <a href="https://tools.ietf.org/html/rfc6797">https://tools.ietf.org/html/rfc6797</a>
 * @see <a href="https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security">https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security</a>
 */
public final class StrictTransportSecurity implements HeaderValue {

	/**
	 * Class logger.
	 */
	private static final Logger log = Loggers.getLogger(StrictTransportSecurity.class);

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
	 * Create header value.
	 *
	 * @param maxAge Max-Age value.
	 * @param includeSubDomains Sub-Domains flag.
	 * @param preload Preload flag.
	 */
	private StrictTransportSecurity(long maxAge, boolean includeSubDomains, boolean preload) {
		this.maxAge = maxAge;
		this.includeSubDomains = includeSubDomains;
		this.preload = preload;
	}

	@Override
	public String value() {
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
	public boolean match(String actualValue) {
		log.debug("Parsing header: '{}'", actualValue);
		String[] parts = actualValue.split(";");

		Builder builder = new Builder();
		Set<Directive> foundDirectives = new HashSet<>();

		for (String part : parts) {
			String[] nameValue = part.split("=");
			String name = nameValue[0].trim();

			log.debug("-> Parsing part: '{}'", part);
			log.debug("  --> Name: '{}'", name);

			Directive directive = Directive.byName(name);
			if (directive == null) {
				log.error("Directive name '{}' should not appear in Strict-Transport-Security header", name);
				throw new IllegalArgumentException(String.format("Cannot parse Strict-Transport-Security, directive %s is unknown", name));
			}

			if (foundDirectives.contains(directive)) {
				log.warn("  --> Directive '{}' has already been parsed, ignore it", name);
				continue;
			}

			// Mark directive.
			foundDirectives.add(directive);

			String value = nameValue.length == 2 ? nameValue[1].trim() : null;

			log.debug("  --> Parsing value: '{}'", value);
			directive.parse(value, builder);
		}

		return equals(builder.build());
	}

	@Override
	public String toString() {
		return value();
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
	 * Builder used to create instance of {@link StrictTransportSecurity} object.
	 */
	public static class Builder {
		/**
		 * {@code max-age} directive.
		 * The max-age value is required to create header.
		 */
		private long maxAge;

		/**
		 * {@code includeSubDomains} directive.
		 */
		private boolean includeSubDomains;

		/**
		 * {@code preload} directive.
		 */
		private boolean preload;

		/**
		 * Empty constructor, only used internally.
		 */
		private Builder() {
		}

		/**
		 * Create builder.
		 *
		 * @param maxAge Max-Age value.
		 * @see <a href="https://tools.ietf.org/html/rfc6797#page-16">https://tools.ietf.org/html/rfc6797#page-16</a>
		 */
		public Builder(long maxAge) {
			this.maxAge = maxAge;
		}

		/**
		 * Enable {@code includeSubDomains} directive.
		 *
		 * @return Current builder.
		 * @see <a href="https://tools.ietf.org/html/rfc6797#section-6.1.2">https://tools.ietf.org/html/rfc6797#section-6.1.2</a>
		 */
		public Builder includeSubDomains() {
			this.includeSubDomains = true;
			return this;
		}

		/**
		 * Enable {@code preload} directive.
		 *
		 * @return Current builder.
		 * @see <a href="https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security">https://developer.mozilla.org/fr/docs/S%C3%A9curit%C3%A9/HTTP_Strict_Transport_Security</a>
		 */
		public Builder preload() {
			this.preload = true;
			return this;
		}

		/**
		 * Create {@code Strict-Transport-Security} header.
		 *
		 * @return Header.
		 */
		public StrictTransportSecurity build() {
			return new StrictTransportSecurity(maxAge, includeSubDomains, preload);
		}
	}

	/**
	 * Set of directive that may appear in {@code Strict-Transport-Security} header.
	 */
	private enum Directive {
		/**
		 * Max-Age directive.
		 */
		MAX_AGE("max-age") {
			@Override
			void parse(String value, Builder builder) {
				String nb = value;

				// Value may be quoted
				if (nb.length() > 2) {
					char firstChar = value.charAt(0);
					char lastChar = value.charAt(value.length() - 1);
					if (firstChar == '"' && firstChar == lastChar) {
						nb = value.substring(1, value.length() - 2);
					}
				}

				builder.maxAge = toLong(nb, String.format("Max-Age '%s' is not a valid number", value));
			}
		},

		/**
		 * {@code includeSubDomains} directive.
		 */
		INCLUDE_SUB_DOMAINS("includeSubDomains") {
			@Override
			void parse(String value, Builder builder) {
				builder.includeSubDomains();
			}
		},

		/**
		 * {@code preload} directive.
		 */
		PRELOAD("preload") {
			@Override
			void parse(String value, Builder builder) {
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

		abstract void parse(String value, Builder builder);

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
		private static Directive byName(String name) {
			return map.get(name.toLowerCase());
		}
	}
}
