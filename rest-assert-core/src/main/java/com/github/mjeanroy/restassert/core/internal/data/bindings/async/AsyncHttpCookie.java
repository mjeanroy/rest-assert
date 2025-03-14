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

package com.github.mjeanroy.restassert.core.internal.data.bindings.async;

import com.github.mjeanroy.restassert.core.data.Cookie;

import java.util.Date;

/**
 * Implementation of {@link Cookie}
 * using Async-Http (version &gt;= 2.0.0) framework as real implementation.
 */
public class AsyncHttpCookie implements Cookie {

	/**
	 * Create new {@link Cookie} using instance
	 * of {@link io.netty.handler.codec.http.cookie.Cookie}.
	 *
	 * @param cookie Original cookie object.
	 * @return Cookie that can be used with rest-assert.
	 */
	public static AsyncHttpCookie create(io.netty.handler.codec.http.cookie.Cookie cookie) {
		return new AsyncHttpCookie(cookie);
	}

	/**
	 * Original async http cookie.
	 */
	private final io.netty.handler.codec.http.cookie.Cookie cookie;

	// Use static factory
	private AsyncHttpCookie(io.netty.handler.codec.http.cookie.Cookie cookie) {
		this.cookie = cookie;
	}

	@Override
	public String getName() {
		return cookie.name();
	}

	@Override
	public String getValue() {
		return cookie.value();
	}

	@Override
	public String getDomain() {
		return cookie.domain();
	}

	@Override
	public String getPath() {
		return cookie.path();
	}

	@Override
	public boolean isSecured() {
		return cookie.isSecure();
	}

	@Override
	public boolean isHttpOnly() {
		return cookie.isHttpOnly();
	}

	@Override
	public SameSite getSameSite() {
		throw new UnsupportedOperationException("org.asynchttpclient.cookie.Cookie does not support #getSameSite()");
	}

	@Override
	public Long getMaxAge() {
		return cookie.maxAge();
	}

	@Override
	public Date getExpires() {
		throw new UnsupportedOperationException("org.asynchttpclient.cookie.Cookie does not support #getExpires(), please use #getMaxAge() instead.");
	}
}
