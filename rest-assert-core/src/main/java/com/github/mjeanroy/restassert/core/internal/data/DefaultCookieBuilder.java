/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

import com.github.mjeanroy.restassert.core.internal.data.Cookie.SameSite;

import java.util.Date;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notBlank;
import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;

/**
 * DefaultCookieBuilder class that can be used to build {@link Cookie} instance.
 */
public class DefaultCookieBuilder {
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
	 * Cookie SameSite flag, optional (default is {@code "Lax"}).
	 */
	private SameSite sameSite;

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
	 * This constructor is private since {@link Cookies#builder(String, String)} method
	 * should be used.
	 *
	 * @param name Cookie name.
	 * @param value Cookie value.
	 */
	DefaultCookieBuilder(String name, String value) {
		this.name = notBlank(name, "Cookie name must not be blank");
		this.value = notNull(value, "Cookie value must not be null");
		this.sameSite = SameSite.LAX;
	}

	/**
	 * Update cookie domain.
	 *
	 * @param domain New domain value.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * Update cookie path.
	 *
	 * @param path New path value.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Set secure flag to {@code true}.
	 *
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setSecure() {
		return setSecure(true);
	}

	/**
	 * Set secure flag..
	 *
	 * @param secure Secure flag.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setSecure(boolean secure) {
		this.secure = secure;
		return this;
	}

	/**
	 * Set httpOnly flag to {@code true}.
	 *
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setHttpOnly() {
		return setHttpOnly(true);
	}

	/**
	 * Set httpOnly flag.
	 *
	 * @param httpOnly The HttpOnly flag.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
		return this;
	}

	/**
	 * Update max-age value.
	 *
	 * @param maxAge Max-Age value.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setMaxAge(Long maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Update expires value.
	 *
	 * @param expires Expires value.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setExpires(Date expires) {
		this.expires = expires;
		return this;
	}

	/**
	 * Update expires value.
	 *
	 * @param expires Expires value (timestamp).
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setExpires(long expires) {
		this.expires = new Date(expires);
		return this;
	}

	/**
	 * Update SameSite value.
	 *
	 * @param sameSite SameSite value.
	 * @return Current builder.
	 */
	public DefaultCookieBuilder setSameSite(SameSite sameSite) {
		this.sameSite = sameSite;
		return this;
	}

	/**
	 * Update SameSite value.
	 *
	 * @param sameSite SameSite value.
	 * @return Current builder.
	 * @throws IllegalArgumentException If sameSite is not equals (ignoring case) to one of: {@code "Lax"}, {@code "Strict"}, {@code "None"}.
	 */
	public DefaultCookieBuilder setSameSite(String sameSite) {
		this.sameSite = SameSite.parse(sameSite);
		return this;
	}

	/**
	 * Create cookie.
	 *
	 * @return Cookie.
	 */
	public Cookie build() {
		return new DefaultCookie(
			name,
			value,
			domain,
			path,
			secure,
			httpOnly,
			sameSite,
			maxAge,
			expires
		);
	}
}
