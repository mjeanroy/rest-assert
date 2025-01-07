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
import com.github.mjeanroy.restassert.core.data.HttpResponse;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * DefaultCookieBuilder used to create mock instance of {@link HttpResponse} class.
 */
public class HttpResponseBuilderImpl extends AbstractHttpResponseBuilder<HttpResponse, HttpResponseBuilderImpl> implements HttpResponseBuilder<HttpResponse> {

	/**
	 * List of cookies.
	 */
	private final List<Cookie> cookies;

	/**
	 * Create builder.
	 */
	public HttpResponseBuilderImpl() {
		this.cookies = new LinkedList<>();
	}

	/**
	 * Add new cookie.
	 *
	 * @param cookie Cookie to add.
	 * @return Current builder.
	 */
	@Override
	public HttpResponseBuilderImpl addCookie(Cookie cookie, Cookie... other) {
		addCookie(cookie);
		for (Cookie c : other) {
			addCookie(c);
		}

		return this;
	}

	private void addCookie(Cookie cookie) {
		if (cookie != null) {
			this.cookies.add(cookie);
		}
	}

	@Override
	public HttpResponse build() {
		Map<String, List<String>> headers = new LinkedHashMap<>();
		for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
			headers.put(entry.getKey(), new LinkedList<>(entry.getValue()));
		}

		return new MockHttpResponse(status, content, headers, cookies);
	}

	private static final class MockHttpResponse implements HttpResponse {

		private final int status;
		private final String content;
		private final Map<String, List<String>> headers;
		private final List<Cookie> cookies;

		private MockHttpResponse(int status, String content, Map<String, List<String>> headers, List<Cookie> cookies) {
			this.status = status;
			this.content = content;
			this.headers = headers;
			this.cookies = cookies;
		}

		@Override
		public int getStatus() {
			return status;
		}

		@Override
		public boolean hasHeader(String name) {
			return headers.containsKey(name);
		}

		@Override
		public List<String> getHeader(String name) {
			List<String> values = headers.get(name);
			if (values == null) {
				return emptyList();
			}

			return unmodifiableList(values);
		}

		@Override
		public String getContent() {
			return content;
		}

		@Override
		public List<Cookie> getCookies() {
			return cookies;
		}
	}
}
