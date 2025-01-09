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
import com.github.mjeanroy.restassert.core.data.HttpHeader;
import com.github.mjeanroy.restassert.core.data.HttpResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
		this.cookies = new ArrayList<>();
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

	/**
	 * Add new cookie.
	 *
	 * @param name Cookie name.
	 * @param value Cookie value.
	 * @return Current builder.
	 */
	public HttpResponseBuilderImpl addCookie(String name, String value) {
		addCookie(new MockCookieBuilder().setName(name).setValue(value).build());
		return this;
	}

	private void addCookie(Cookie cookie) {
		if (cookie != null) {
			this.cookies.add(cookie);
		}
	}

	@Override
	public HttpResponse build() {
		List<HttpHeader> headers = this.headers.entrySet().stream()
			.map((h) -> HttpHeader.of(h.getKey(), h.getValue()))
			.collect(Collectors.toList());

		return new MockHttpResponse(status, content, headers, cookies);
	}

	private static final class MockHttpResponse implements HttpResponse {

		private final int status;
		private final String content;
		private final List<HttpHeader> headers;
		private final List<Cookie> cookies;

		private MockHttpResponse(
			int status,
			String content,
			List<HttpHeader> headers,
			List<Cookie> cookies
		) {
			this.status = status;
			this.content = content;

			this.headers = unmodifiableList(
				new ArrayList<>(headers)
			);

			this.cookies = unmodifiableList(
				new ArrayList<>(cookies)
			);
		}

		@Override
		public int getStatus() {
			return status;
		}

		@Override
		public boolean hasHeader(String name) {
			return headers.stream().anyMatch((h) -> (
				Objects.equals(h.getName().toLowerCase(), name.toLowerCase())
			));
		}

		@Override
		public List<String> getHeader(String name) {
			return headers.stream()
				.filter((h) -> Objects.equals(h.getName().toLowerCase(), name.toLowerCase()))
				.map(HttpHeader::getValues)
				.findFirst()
				.orElseGet(Collections::emptyList);
		}

		@Override
		public List<HttpHeader> getHeaders() {
			return headers;
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
