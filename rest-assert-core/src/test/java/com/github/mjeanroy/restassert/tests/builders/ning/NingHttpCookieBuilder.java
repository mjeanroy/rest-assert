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

package com.github.mjeanroy.restassert.tests.builders.ning;

import com.github.mjeanroy.restassert.tests.builders.AbstractCookieBuilder;
import com.ning.http.client.cookie.Cookie;

/**
 * Build mock instance of {@link Cookie} class.
 */
public class NingHttpCookieBuilder extends AbstractCookieBuilder<Cookie, NingHttpCookieBuilder> {

	/**
	 * Create builder with default values.
	 */
	public NingHttpCookieBuilder() {
		super();
	}

	@Override
	public Cookie build() {
		return Cookie.newValidCookie(
			getName(),
			getValue(),
			false,
			getDomain(),
			getPath(),
			getMaxAge(),
			isSecure(),
			isHttpOnly()
		);
	}
}
