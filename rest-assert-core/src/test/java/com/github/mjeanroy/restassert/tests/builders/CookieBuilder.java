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

package com.github.mjeanroy.restassert.tests.builders;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Create mock instance of {@link Cookie} class.
 */
public class CookieBuilder {

	/**
	 * Cookie name.
	 */
	private String name;

	/**
	 * Cookie value.
	 */
	private String value;

	/**
	 * Cookie domain.
	 */
	private String domain;

	/**
	 * Cookie path.
	 */
	private String path;

	/**
	 * Cookie "http-only" flag.
	 */
	private boolean httpOnly;

	/**
	 * Cookie "secure" flag.
	 */
	private boolean secured;

	/**
	 * Cookie max-age value.
	 */
	private long maxAge;

	/**
	 * Set {@link #name}.
	 *
	 * @param name New {@link #name}.
	 * @return Current builder.
	 */
	public CookieBuilder setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set {@link #value}.
	 *
	 * @param value New {@link #value}.
	 * @return Current builder.
	 */
	public CookieBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Set {@link #domain}.
	 *
	 * @param domain New {@link #domain}.
	 * @return Current builder.
	 */
	public CookieBuilder setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * Set {@link #path}.
	 *
	 * @param path New {@link #path}.
	 * @return Current builder.
	 */
	public CookieBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Set {@link #httpOnly}.
	 *
	 * @param httpOnly New {@link #httpOnly}.
	 * @return Current builder.
	 */
	public CookieBuilder setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
		return this;
	}

	/**
	 * Set {@link #secured}.
	 *
	 * @param secured New {@link #secured}.
	 * @return Current builder.
	 */
	public CookieBuilder setSecured(boolean secured) {
		this.secured = secured;
		return this;
	}

	/**
	 * Set {@link #maxAge}.
	 *
	 * @param maxAge New {@link #maxAge}.
	 * @return Current builder.
	 */
	public CookieBuilder setMaxAge(long maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Build mock of {@link Cookie} class.
	 *
	 * @return Mock instance.
	 */
	public Cookie build() {
		Cookie cookie = mock(Cookie.class, String.format("Cookie{name: %s}", name));
		when(cookie.getName()).thenReturn(name);
		when(cookie.getValue()).thenReturn(value);
		when(cookie.getDomain()).thenReturn(domain);
		when(cookie.getPath()).thenReturn(path);
		when(cookie.isHttpOnly()).thenReturn(httpOnly);
		when(cookie.isSecured()).thenReturn(secured);
		when(cookie.getMaxAge()).thenReturn(maxAge);
		return cookie;
	}
}
