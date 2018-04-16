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

package com.github.mjeanroy.restassert.core.internal.data.bindings.apache;

import com.github.mjeanroy.restassert.core.internal.data.Cookie;

import java.util.Date;

/**
 * Implementation of {@link Cookie}
 * using Apache HttpClient framework as real implementation.
 */
public class ApacheHttpCookie implements Cookie {

	/**
	 * Create new {@link Cookie} using instance
	 * of {@link org.apache.http.cookie.Cookie}.
	 *
	 * @param cookie Original cookie object.
	 * @return Cookie that can be used with rest-assert.
	 */
	public static ApacheHttpCookie create(org.apache.http.cookie.Cookie cookie) {
		return new ApacheHttpCookie(cookie);
	}

	/**
	 * Original apache http cookie.
	 */
	private final org.apache.http.cookie.Cookie cookie;

	// Use static factory
	private ApacheHttpCookie(org.apache.http.cookie.Cookie cookie) {
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
		throw new UnsupportedOperationException("org.apache.http.cookie.Cookie does not support #isHttpOnly().");
	}

	@Override
	public Long getMaxAge() {
		throw new UnsupportedOperationException("org.apache.http.cookie.Cookie does not support #getMaxAge(), please use #getExpires() instead.");
	}

	@Override
	public Date getExpires() {
		return cookie.getExpiryDate();
	}
}
