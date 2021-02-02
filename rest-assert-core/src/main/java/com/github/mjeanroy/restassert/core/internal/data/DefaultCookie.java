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
import com.github.mjeanroy.restassert.core.internal.common.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * Default cookie representation.
 */
final class DefaultCookie implements Cookie {
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
	DefaultCookie(String name, String value, String domain, String path, boolean secure, boolean httpOnly, Long maxAge, Date expires) {
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
		return ToStringBuilder.toStringBuilder(getClass())
				.append("name", name)
				.append("value", value)
				.append("domain", domain)
				.append("path", path)
				.append("secure", secure)
				.append("httpOnly", httpOnly)
				.append("maxAge", maxAge)
				.append("expires", expires)
				.build();
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
