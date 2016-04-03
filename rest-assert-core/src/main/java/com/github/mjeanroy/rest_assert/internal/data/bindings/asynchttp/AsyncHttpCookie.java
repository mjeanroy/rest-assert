/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.data.bindings.asynchttp;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;

/**
 * Implementation of {@link com.github.mjeanroy.rest_assert.internal.data.Cookie}
 * using Async-Http framework as real implementation.
 */
public class AsyncHttpCookie implements Cookie {

	/**
	 * Create new {@link com.github.mjeanroy.rest_assert.internal.data.Cookie} using instance
	 * of {@link org.apache.http.cookie.Cookie}.
	 *
	 * @param cookie Original cookie object.
	 * @return Cookie that can be used with rest-assert.
	 */
	public static AsyncHttpCookie create(com.ning.http.client.cookie.Cookie cookie) {
		return new AsyncHttpCookie(cookie);
	}

	/**
	 * Original async http cookie.
	 */
	private final com.ning.http.client.cookie.Cookie cookie;

	// Use static factory
	private AsyncHttpCookie(com.ning.http.client.cookie.Cookie cookie) {
		this.cookie = cookie;
	}

	@Override
	public String getName() {
		return cookie.getName();
	}

	@Override
	public String getValue() {
		return cookie.getValue();
	}

	@Override
	public String getDomain() {
		return cookie.getDomain();
	}

	@Override
	public String getPath() {
		return cookie.getPath();
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
	public long getMaxAge() {
		return cookie.getMaxAge();
	}
}
