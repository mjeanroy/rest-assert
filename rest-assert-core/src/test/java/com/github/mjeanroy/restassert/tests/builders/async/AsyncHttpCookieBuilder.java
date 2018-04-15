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

package com.github.mjeanroy.restassert.tests.builders.async;

import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;

/**
 * Build mock instance of {@link Cookie} class.
 */
public class AsyncHttpCookieBuilder {

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
	private boolean secure;

	/**
	 * Cookie max-age value.
	 */
	private long maxAge;

	/**
	 * Create builder with default values.
	 */
	public AsyncHttpCookieBuilder() {
		this.name = "foo";
		this.value = "bar";
	}

	/**
	 * Set {@link #secure}.
	 *
	 * @param secure New {@link #secure}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setSecure(boolean secure) {
		this.secure = secure;
		return this;
	}

	/**
	 * Set {@link #domain}.
	 *
	 * @param domain New {@link #domain}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	/**
	 * Set {@link #name}.
	 *
	 * @param name New {@link #name}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Set {@link #value}.
	 *
	 * @param value New {@link #value}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setValue(String value) {
		this.value = value;
		return this;
	}

	/**
	 * Set {@link #path}.
	 *
	 * @param path New {@link #path}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	/**
	 * Set {@link #httpOnly}.
	 *
	 * @param httpOnly New {@link #httpOnly}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setHttpOnly(boolean httpOnly) {
		this.httpOnly = httpOnly;
		return this;
	}

	/**
	 * Set {@link #maxAge}.
	 *
	 * @param maxAge New {@link #maxAge}.
	 * @return Current builder.
	 */
	public AsyncHttpCookieBuilder setMaxAge(long maxAge) {
		this.maxAge = maxAge;
		return this;
	}

	/**
	 * Create mock instance of {@link com.ning.http.client.cookie.Cookie}.
	 *
	 * @return Mock instance.
	 */
	public Cookie build() {
		DefaultCookie cookie = new DefaultCookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setSecure(secure);
		cookie.setHttpOnly(httpOnly);
		return cookie;
	}
}
