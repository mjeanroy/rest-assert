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

package com.github.mjeanroy.restassert.core.internal.data.bindings;

import com.github.mjeanroy.restassert.core.internal.common.Collections.Mapper;
import com.github.mjeanroy.restassert.core.internal.data.Cookie;
import com.github.mjeanroy.restassert.core.internal.data.Cookies;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.exceptions.NonParsableResponseBodyException;

import java.io.IOException;
import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.common.Collections.map;
import static com.github.mjeanroy.restassert.core.internal.data.HttpHeader.SET_COOKIE;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Template for {@link HttpResponse} interface.
 */
public abstract class AbstractHttpResponse implements HttpResponse {

	/**
	 * Default constructor.
	 */
	protected AbstractHttpResponse() {
	}

	@Override
	public String getContent() {
		try {
			return doGetContent();
		} catch (IOException ex) {
			throw new NonParsableResponseBodyException(ex);
		}
	}

	@Override
	public boolean hasHeader(String name) {
		return !getHeader(name).isEmpty();
	}

	@Override
	public List<Cookie> getCookies() {
		List<String> setCookieHeaders = getHeader(SET_COOKIE.getName());
		if (setCookieHeaders == null || setCookieHeaders.isEmpty()) {
			return emptyList();
		}

		// Parse header to create valid cookie object.
		List<Cookie> cookies = map(setCookieHeaders, new Mapper<String, Cookie>() {
			@Override
			public Cookie apply(String input) {
				return Cookies.parse(input);
			}
		});

		return unmodifiableList(cookies);
	}

	/**
	 * Get the content body as a string.
	 * If an {@link java.io.IOException} is thrown, it will be catched
	 * by {@link #getContent()} method and rethrows as a {@link NonParsableResponseBodyException}.
	 *
	 * @return Response body.
	 * @throws IOException If an error occurred during parsing.
	 */
	protected abstract String doGetContent() throws IOException;
}
