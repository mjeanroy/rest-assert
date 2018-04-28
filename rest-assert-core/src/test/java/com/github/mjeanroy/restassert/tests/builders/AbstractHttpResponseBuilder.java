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
import com.github.mjeanroy.restassert.tests.CookieSerializer;
import com.github.mjeanroy.restassert.test.data.Header;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Skeleton builder for implementation of {@link HttpResponseBuilder}.
 *
 * @param <T> Concrete implementation, used for chaining.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHttpResponseBuilder<U, T extends HttpResponseBuilder> implements HttpResponseBuilder<U> {

	/**
	 * Http response status code, default is 200.
	 */
	protected int status;

	/**
	 * Http response body, default is an empty string.
	 */
	protected String content;

	/**
	 * Http response headers, default is not headers.
	 */
	protected final Map<String, List<String>> headers;

	/**
	 * Create builder with default parameters.
	 */
	protected AbstractHttpResponseBuilder() {
		this.status = 200;
		this.content = "";
		this.headers = new LinkedHashMap<>();
	}

	@Override
	public T setStatus(int status) {
		this.status = status;
		return (T) this;
	}

	@Override
	public T setContent(String content) {
		this.content = content;
		return (T) this;
	}

	@Override
	public T addHeader(String name, String value) {
		List<String> header = headers.get(name);
		if (header == null) {
			header = new LinkedList<>();
			headers.put(name, header);
		}

		header.add(value);
		return (T) this;
	}

	@Override
	public T addHeader(Header header, Header... other) {
		addHeader(header.getName(), header.getValue());
		for (Header h : other) {
			addHeader(h.getName(), h.getValue());
		}

		return (T) this;
	}

	@Override
	public T addCookie(Cookie cookie, Cookie... other) {
		addHeader("Set-Cookie", CookieSerializer.serialize(cookie));
		for (Cookie c : other) {
			addHeader("Set-Cookie", CookieSerializer.serialize(c));
		}

		return (T) this;
	}
}
