/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

import java.util.Objects;

import static com.github.mjeanroy.rest_assert.utils.Utils.notBlank;
import static com.github.mjeanroy.rest_assert.utils.Utils.notNull;
import static com.github.mjeanroy.rest_assert.utils.Utils.toLong;

/**
 * Cookies utilities.
 */
public final class Cookies {

	// Ensure non instantiation.
	private Cookies() {
	}

	/**
	 * Create new cookie.
	 *
	 * @param name Cookie name (must not be blank).
	 * @param value Cookie value (must not be null).
	 * @param domain Cookie domain.
	 * @param path Cookie path.
	 * @param secure Secure flag.
	 * @param httpOnly Http-Only flag.
	 * @param maxAge Cookie max-age.
	 * @return The cookie.
	 * @throws NullPointerException If {@code name} or {@code value} are null.
	 * @throws IllegalArgumentException If {@code name} is empty or blank.
	 */
	public static Cookie newCookie(String name, String value, String domain, String path, boolean secure, boolean httpOnly, Long maxAge) {
		return new DefaultCookie(
			notBlank(name, "Cookie name must be defined"),
			notNull(value, "Cookie value must not be null"),
			domain, path, secure, httpOnly, maxAge
		);
	}

	/**
	 * Parse Set-Cookie header field and produce a valid Cookie object.
	 * Specifications: https://tools.ietf.org/html/rfc6265#page-18
	 *
	 * @param setCookie Header field.
	 * @return Cookie.
	 */
	public static Cookie parse(String setCookie) {
		notBlank(setCookie, "Header Set-Cookie must be defined");

		String[] parts = splitHeader(setCookie.trim());

		// Parse name-value-pair.
		String[] nameValuePair = parseNameValue(parts[0].trim());
		final String name = nameValuePair[0];
		final String value = nameValuePair[1];

		// Parse metadata
		String domain = null;
		String path = null;
		boolean secure = false;
		boolean httpOnly = false;
		Long maxAge = null;

		char[] unparsedAttributes = parts[1].trim().toCharArray();
		int length = unparsedAttributes.length;
		int pos = 0;
		while (pos < length) {
			MetaDataAttribute next = parseNextPart(unparsedAttributes, pos);

			// Increment new position
			pos += next.field.length() + 1;

			String attrName = next.name;
			String attrValue = next.value;

			if (attrName.equalsIgnoreCase("domain")) {
				domain = parseDomain(attrValue);
			} else if (attrName.equalsIgnoreCase("path")) {
				path = parsePath(attrValue);
			} else if (attrName.equalsIgnoreCase("secure")) {
				secure = parseSecure();
			} else if (attrName.equalsIgnoreCase("httponly")) {
				httpOnly = parseHttpOnly();
			} else if (attrName.equalsIgnoreCase("max-age")) {
				maxAge = parseMaxAge(attrValue);
			}
		}

		return newCookie(name, value, domain, path, secure, httpOnly, maxAge);
	}

	/**
	 * Default cookie representation.
	 */
	private static class DefaultCookie implements Cookie {
		/**
		 * Cookie name.
		 */
		private final String name;

		/**
		 * Cookie value.
		 */
		private final String value;

		/**
		 * Cookie domain.
		 */
		private final String domain;

		/**
		 * Cookie path.
		 */
		private final String path;

		/**
		 * Secure flag.
		 */
		private boolean secure;

		/**
		 * HTTP-Only flag.
		 */
		private boolean httpOnly;

		/**
		 * Cookie max-age value.
		 */
		private Long maxAge;

		/**
		 * Create cookie.
		 *
		 * @param name Cookie name, must not be null.
		 * @param value Cookie value, must not be null.
		 * @param domain Cookie domain.
		 * @param path Cookie path.
		 * @param secure Secure flag.
		 * @param httpOnly HTTP-Only flag.
		 * @param maxAge Cookie max-age value.
		 */
		private DefaultCookie(String name, String value, String domain, String path, boolean secure, boolean httpOnly, Long maxAge) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.secure = secure;
			this.httpOnly = httpOnly;
			this.maxAge = maxAge;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public String getDomain() {
			return domain;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public boolean isSecured() {
			return secure;
		}

		@Override
		public boolean isHttpOnly() {
			return httpOnly;
		}

		@Override
		public Long getMaxAge() {
			return maxAge;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name).append("=").append(value);

			if (domain != null) {
				sb.append("; Domain=").append(domain);
			}

			if (path != null) {
				sb.append("; Path=").append(path);
			}

			if (secure) {
				sb.append("; secure");
			}

			if (httpOnly) {
				sb.append("; HttpOnly");
			}

			if (maxAge != null) {
				sb.append("; max-age=").append(maxAge);
			}

			return sb.toString();
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}

			if (o instanceof DefaultCookie) {
				DefaultCookie c = (DefaultCookie) o;
				return Objects.equals(getName(), c.getName())
					&& Objects.equals(getValue(), c.getValue())
					&& Objects.equals(getDomain(), c.getDomain())
					&& Objects.equals(getPath(), c.getPath())
					&& Objects.equals(isSecured(), c.isSecured())
					&& Objects.equals(isHttpOnly(), c.isHttpOnly())
					&& Objects.equals(getMaxAge(), c.getMaxAge());
			}

			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(getName(), getValue(), getDomain(), getPath(), isSecured(), isHttpOnly(), getMaxAge());
		}
	}

	/**
	 * First step as specified by RFC6265.
	 *
	 * If the set-cookie-string contains a %x3B (";") character:
	 *
	 * The name-value-pair string consists of the characters up to,
	 * but not including, the first %x3B (";"), and the unparsed-
	 * attributes consist of the remainder of the set-cookie-string
	 * (including the %x3B (";") in question).
	 *
	 * Otherwise:
	 *
	 * The name-value-pair string consists of all the characters
	 * contained in the set-cookie-string, and the unparsed-
	 * attributes is the empty string.
	 *
	 * @param setCookie Set-Cookie Header.
	 * @return An array containing the name-value pair as the first element and the unparsed string as the second element.
	 */
	private static String[] splitHeader(String setCookie) {
		StringBuilder nameValuePair = new StringBuilder();
		StringBuilder unparsedAttributes = new StringBuilder();

		boolean foundSemiColon = false;
		StringBuilder current = nameValuePair;
		for (char c : setCookie.toCharArray()) {
			if (!foundSemiColon && c == ';') {
				foundSemiColon = true;
				current = unparsedAttributes;
				continue;
			}

			current.append(c);
		}

		return new String[] {
			nameValuePair.toString(),
			unparsedAttributes.toString()
		};
	}

	/**
	 * Parse name-value-pair of Set-Cookie header.
	 *
	 * As specified:
	 *
	 * 2. If the name-value-pair string lacks a %x3D ("=") character,
	 * ignore the set-cookie-string entirely.
	 *
	 * 3. The (possibly empty) name string consists of the characters up
	 * to, but not including, the first %x3D ("=") character, and the
	 * (possibly empty) value string consists of the characters after
	 * the first %x3D ("=") character.
	 *
	 * 4. Remove any leading or trailing WSP characters from the name
	 * string and the value string.
	 *
	 * 5. If the name string is empty, ignore the set-cookie-string
	 * entirely.
	 *
	 * 6. The cookie-name is the name string, and the cookie-value is the
	 * value string.
	 *
	 * @param nameValuePair Name-Value-Pair value.
	 * @return An array containing the cookie name as first element and the value as second element.
	 */
	private static String[] parseNameValue(String nameValuePair) {
		StringBuilder nameBuilder = new StringBuilder();
		StringBuilder valueBuilder = new StringBuilder();

		boolean isName = true;
		StringBuilder current = nameBuilder;
		for (char c : nameValuePair.toCharArray()) {
			if (isName && c == '=') {
				isName = false;
				current = valueBuilder;
				continue;
			}

			current.append(c);
		}

		// If isName == true, then it means that the name-value-pair does not contain the = sign
		if (isName) {
			throw new IllegalArgumentException("Set-Cookie header must have a value");
		}

		String name = nameBuilder.toString().trim();
		String value = valueBuilder.toString().trim();

		if (name.isEmpty()) {
			throw new IllegalArgumentException("Set-Cookie header must have a name");
		}

		return new String[] { name, value };
	}

	private static MetaDataAttribute parseNextPart(char[] attributes, int pos) {
		StringBuilder field = new StringBuilder();
		StringBuilder name = new StringBuilder();
		StringBuilder value = new StringBuilder();

		StringBuilder current = name;
		for (int i = pos; i < attributes.length; ++i) {
			char c = attributes[i];
			field.append(c);

			if (c == ';') {
				break;
			}

			if (c == '=' && current == name) {
				current = value;
				continue;
			}

			current.append(c);
		}

		return new MetaDataAttribute(
			field.toString(),
			name.toString().trim(),
			value.toString().trim()
		);
	}

	/**
	 * Parse "Domain" directive of Set-Cookie header.
	 * Basically, just return the domain as it appears.
	 *
	 * @param domain Domain value.
	 * @return Domain value.
	 */
	private static String parseDomain(String domain) {
		return domain;
	}

	/**
	 * Parse "Path" directive of Set-Cookie header.
	 * Basically, just return the path as it appears.
	 *
	 * @param path Path value.
	 * @return Path value.
	 */
	private static String parsePath(String path) {
		return path;
	}

	/**
	 * Parse "Secure" directive of Set-Cookie header.
	 * Basically, just return {@code true}.
	 *
	 * @return Always {@code true}.
	 */
	private static boolean parseSecure() {
		return true;
	}

	/**
	 * Parse "HttpOnly" directive of Set-Cookie header.
	 * Basically, just return {@code true}.
	 *
	 * @return Always {@code true}.
	 */
	private static boolean parseHttpOnly() {
		return true;
	}

	/**
	 * Parse Max-Age directive of Set-Cookie header.
	 *
	 * @param maxAge Max-Age value.
	 * @return The max-age.
	 */
	private static Long parseMaxAge(String maxAge) {
		return toLong(maxAge, "Max-Age is not a valid number");
	}

	/**
	 * Cookie Metadata Attribute.
	 */
	private static class MetaDataAttribute {
		private final String field;
		private final String name;
		private final String value;

		private MetaDataAttribute(String field, String name, String value) {
			this.field = field;
			this.name = name;
			this.value = value;
		}
	}
}
