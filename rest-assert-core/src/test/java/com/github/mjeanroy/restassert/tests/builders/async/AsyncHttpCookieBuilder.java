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

package com.github.mjeanroy.restassert.tests.builders.async;

import com.github.mjeanroy.restassert.tests.builders.AbstractCookieBuilder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;

/**
 * Build mock instance of {@link Cookie} class.
 */
public class AsyncHttpCookieBuilder extends AbstractCookieBuilder<Cookie, AsyncHttpCookieBuilder> {

	/**
	 * Create builder with default values.
	 */
	public AsyncHttpCookieBuilder() {
		super();
	}

	@Override
	public Cookie build() {
		DefaultCookie cookie = new DefaultCookie(getName(), getValue());
		cookie.setDomain(getDomain());
		cookie.setPath(getPath());
		cookie.setMaxAge(getMaxAge());
		cookie.setSecure(isSecure());
		cookie.setHttpOnly(isHttpOnly());
		return cookie;
	}
}
