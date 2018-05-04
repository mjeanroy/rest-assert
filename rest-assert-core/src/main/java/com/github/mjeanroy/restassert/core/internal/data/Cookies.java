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

package com.github.mjeanroy.restassert.core.internal.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.mjeanroy.restassert.core.internal.common.Numbers.toLong;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.isGreaterThan;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.isInRange;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * Cookies utilities.
 */
public final class Cookies {

	/**
	 * UTC Time Zone.
	 */
	private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

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
	 * @param expires Cookies expires value.
	 * @return The cookie.
	 * @throws NullPointerException If {@code name} or {@code value} are null.
	 * @throws IllegalArgumentException If {@code name} is empty or blank.
	 */
	public static Cookie newCookie(String name, String value, String domain, String path, boolean secure, boolean httpOnly, Long maxAge, Date expires) {
		return new DefaultCookie(
				notBlank(name, "Cookie name must be defined"),
				notNull(value, "Cookie value must not be null"),
				domain, path, secure, httpOnly, maxAge, expires
		);
	}

	/**
	 * Create new builder.
	 *
	 * @param name Cookie name, must not be blank.
	 * @param value Cookie value, must not be null.
	 * @return The builder.
	 */
	public static Builder builder(String name, String value) {
		return new Builder(name, value);
	}

	/**
	 * Check that two {@link Cookie} are equals:
	 *
	 * <ul>
	 *   <li>If both are {@code null}, {@code true} is returned.</li>
	 *   <li>If one is {@code null}, {@code false} is returned.</li>
	 *   <li>Otherwise, each cookie fields are compared</li>
	 * </ul>
	 *
	 * <strong>Note: Cookie name comparison is case-insensitive.</strong>
	 *
	 * @param c1 First cookie.
	 * @param c2 Second cookie.
	 * @return {@code true} if {@code c1} and {@code c2} are equals, {@code false} otherwise.
	 */
	public static boolean equals(Cookie c1, Cookie c2) {
		if (c1 == c2) {
			return true;
		}

		if (c1 == null || c2 == null) {
			return false;
		}

		return Objects.equals(c1.getName(), c2.getName())
				&& Objects.equals(c1.getValue(), c2.getValue())
				&& Objects.equals(c1.getDomain(), c2.getDomain())
				&& Objects.equals(c1.getPath(), c2.getPath())
				&& c1.isSecured() == c2.isSecured()
				&& c1.isHttpOnly() == c2.isHttpOnly()
				&& Objects.equals(c1.getMaxAge(), c2.getMaxAge())
				&& Objects.equals(c1.getExpires(), c2.getExpires());
	}

	/**
	 * Builder class that can be used to build {@link Cookie} instance.
	 */
	public static class Builder {
		/**
		 * Cookie name, mandatory.
		 */
		private final String name;

		/**
		 * Cookie value, mandatory.
		 */
		private final String value;

		/**
		 * Cookie domain, optional (default is {@code null}).
		 */
		private String domain;

		/**
		 * Cookie path, optional (default is {@code null}).
		 */
		private String path;

		/**
		 * Cookie secure flag, optional (default is {@code false}).
		 */
		private boolean secure;

		/**
		 * Cookie httpOnly flag, optional (default is {@code false}).
		 */
		private boolean httpOnly;

		/**
		 * Cookie max-age, optional (default is {@code null}, meaning no max-age).
		 */
		private Long maxAge;

		/**
		 * Cookie expires date, optional (default is {@code null}, meaning no expires value).
		 */
		private Date expires;

		/**
		 * Create builder.
		 * This constructor is private since {@link com.github.mjeanroy.restassert.core.internal.data.Cookies#builder(String, String)} method
		 * should be used.
		 *
		 * @param name Cookie name.
		 * @param value Cookie value.
		 */
		private Builder(String name, String value) {
			this.name = notBlank(name, "Cookie name must not be blank");
			this.value = notNull(value, "Cookie value must not be null");
		}

		/**
		 * Update cookie domain.
		 *
		 * @param domain New domain value.
		 * @return Current builder.
		 */
		public Builder setDomain(String domain) {
			this.domain = domain;
			return this;
		}

		/**
		 * Update cookie path.
		 *
		 * @param path New path value.
		 * @return Current builder.
		 */
		public Builder setPath(String path) {
			this.path = path;
			return this;
		}

		/**
		 * Set secure flag to {@code true}.
		 *
		 * @return Current builder.
		 */
		public Builder setSecure() {
			this.secure = true;
			return this;
		}

		/**
		 * Set httpOnly flag to {@code true}.
		 *
		 * @return Current builder.
		 */
		public Builder setHttpOnly() {
			this.httpOnly = true;
			return this;
		}

		/**
		 * Update max-age value.
		 *
		 * @param maxAge Max-Age value.
		 * @return Current builder.
		 */
		public Builder setMaxAge(long maxAge) {
			this.maxAge = maxAge;
			return this;
		}

		/**
		 * Update expires value.
		 *
		 * @param expires Expires value.
		 * @return Current builder.
		 */
		public Builder setExpires(Date expires) {
			this.expires = expires;
			return this;
		}

		/**
		 * Update expires value.
		 *
		 * @param expires Expires value (timestamp).
		 * @return Current builder.
		 */
		public Builder setExpires(long expires) {
			this.expires = new Date(expires);
			return this;
		}

		/**
		 * Create cookie.
		 *
		 * @return Cookie.
		 */
		public Cookie build() {
			return newCookie(name, value, domain, path, secure, httpOnly, maxAge, expires);
		}
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
		Date expires = null;

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
			} else if (attrName.equalsIgnoreCase("expires")) {
				expires = parseExpires(attrValue);
			}
		}

		return newCookie(name, value, domain, path, secure, httpOnly, maxAge, expires);
	}

	/**
	 * Default cookie representation.
	 */
	static final class DefaultCookie implements Cookie {
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
		private final boolean secure;

		/**
		 * HTTP-Only flag.
		 */
		private final boolean httpOnly;

		/**
		 * Cookie max-age value.
		 */
		private final Long maxAge;

		/**
		 * Cookie expires date.
		 */
		private final Date expires;

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
		private DefaultCookie(String name, String value, String domain, String path, boolean secure, boolean httpOnly, Long maxAge, Date expires) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.secure = secure;
			this.httpOnly = httpOnly;
			this.maxAge = maxAge;

			// Date class is not immutable, get a clone copy.
			if (expires == null) {
				this.expires = null;
			} else {
				this.expires = new Date(expires.getTime());
			}
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
		public Date getExpires() {
			// Since a date is mutable, return a clone copy to be sure of no side-effect.
			return expires == null ? null : new Date(expires.getTime());
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

			if (expires != null) {
				DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.US);
				df.setTimeZone(UTC);
				sb.append("; expires=").append(df.format(expires));
			}

			return sb.toString();
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}

			if (o instanceof DefaultCookie) {
				return Cookies.equals(this, (DefaultCookie) o);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return Objects.hash(getName(), getValue(), getDomain(), getPath(), isSecured(), isHttpOnly(), getMaxAge(), getExpires());
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

		return new String[]{
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

		return new String[]{name, value};
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

	private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})[^\\d]*");
	private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})[^\\d]*");
	private static final Pattern MONTH_PATTERN = Pattern.compile("(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec).*");
	private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})[^\\d]*");

	/**
	 * Parse Expires directive of Set-Cookie header.
	 *
	 * @param expires Expires value.
	 * @return The expires date.
	 */
	private static Date parseExpires(String expires) {
		int hour = -1;
		int minute = -1;
		int second = -1;
		int dayOfMonth = -1;
		int month = -1;
		int year = -1;

		List<String> tokens = splitTokens(expires);

		// Initialize matcher.
		// This matcher will be re-used with different patters while iterating against tokens
		Matcher matcher = TIME_PATTERN.matcher(expires);

		for (String token : tokens) {
			matcher.reset(token);

			if (hour == -1 && matcher.usePattern(TIME_PATTERN).matches()) {
				hour = Integer.parseInt(matcher.group(1));
				minute = Integer.parseInt(matcher.group(2));
				second = Integer.parseInt(matcher.group(3));
			} else if (dayOfMonth == -1 && matcher.usePattern(DAY_OF_MONTH_PATTERN).matches()) {
				dayOfMonth = Integer.parseInt(matcher.group(1));
			} else if (month == -1 && matcher.usePattern(MONTH_PATTERN).matches()) {
				String monthString = matcher.group(1).toLowerCase(Locale.US);
				month = MONTH_PATTERN.pattern().indexOf(monthString) / 4;
			} else if (year == -1 && matcher.usePattern(YEAR_PATTERN).matches()) {
				year = Integer.parseInt(matcher.group(1));
			}
		}

		if (year >= 70 && year <= 99) {
			year += 1900;
		}

		if (year >= 0 && year <= 69) {
			year += 2000;
		}

		isGreaterThan(year, 1601, "Expires year must be greater than 1601");
		isGreaterThan(month, 0, "Expires month is missing");
		isInRange(dayOfMonth, 1, 31, "Expires day cannot be less than 1 or greater than 31");
		isInRange(hour, 0, 23, "Expires hour cannot be less than 0 or greater than 23");
		isInRange(minute, 0, 59, "Expires minutes cannot be less than 0 or greater than 59");
		isInRange(second, 0, 59, "Expires second cannot be less than 0 or greater than 59");

		Calendar calendar = new GregorianCalendar(UTC);
		calendar.setLenient(false);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}

	/**
	 * Split cookie date into date-tokens (as specified by RFC 6265).
	 *
	 * @param expires Date.
	 * @return All date tokens.
	 */
	private static List<String> splitTokens(String expires) {
		List<String> tokens = new LinkedList<>();

		StringBuilder currentToken = new StringBuilder();
		for (char c : expires.toCharArray()) {
			if (isDelimiter(c)) {
				if (currentToken.length() > 0) {
					tokens.add(currentToken.toString());
					currentToken = new StringBuilder();
				}
			} else {
				currentToken.append(c);
			}
		}

		return tokens;
	}

	private static boolean isNonDelimiter(char c) {
		return (c < ' ' && c != '\t') || (c >= '\u007f')
				|| (c >= '0' && c <= '9')
				|| (c >= 'a' && c <= 'z')
				|| (c >= 'A' && c <= 'Z')
				|| (c == ':');
	}

	private static boolean isDelimiter(char c) {
		return !isNonDelimiter(c);
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
