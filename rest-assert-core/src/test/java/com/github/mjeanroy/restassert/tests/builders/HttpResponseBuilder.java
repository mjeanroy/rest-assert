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

package com.github.mjeanroy.restassert.tests.builders;

import com.github.mjeanroy.restassert.core.data.Cookie;
import com.github.mjeanroy.restassert.test.data.Header;

public interface HttpResponseBuilder<T> {

	/**
	 * Set http response status.
	 *
	 * @param status New status.
	 * @return Current builder.
	 */
	HttpResponseBuilder<T> setStatus(int status);

	/**
	 * Set http response body.
	 *
	 * @param content New content.
	 * @return Current builder.
	 */
	HttpResponseBuilder<T> setContent(String content);

	/**
	 * Add new header.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	HttpResponseBuilder<T> addHeader(String name, String value);

	/**
	 * Add new header.
	 *
	 * @param header Header.
	 * @param other Other, optional, headers.
	 * @return Current builder.
	 */
	HttpResponseBuilder<T> addHeader(Header header, Header... other);

	/**
	 * Add new cookie.
	 *
	 * @param cookie Cookie.
	 * @param other Other, optional, cookies.
	 * @return Current builder.
	 */
	HttpResponseBuilder<T> addCookie(Cookie cookie, Cookie... other);

	/**
	 * Build http response.
	 *
	 * @return Http response.
	 */
	T build();
}
