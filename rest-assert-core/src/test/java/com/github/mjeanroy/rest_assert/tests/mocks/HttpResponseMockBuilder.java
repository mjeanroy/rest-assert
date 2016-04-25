/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.tests.mocks;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.models.Header;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static java.util.Collections.addAll;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder used to create mock instance of {@link HttpResponse} class.
 */
public class HttpResponseMockBuilder {

	/**
	 * Http Response status.
	 */
	private int status;

	/**
	 * Body content.
	 */
	private String content;

	/**
	 * Map of http response headers.
	 */
	private final Map<String, List<String>> headers;

	/**
	 * List of cookies.
	 */
	private final List<Cookie> cookies;

	/**
	 * Create builder.
	 */
	public HttpResponseMockBuilder() {
		this.headers = new LinkedHashMap<>();
		this.cookies = new LinkedList<>();
	}

	/**
	 * Set {@link #status}.
	 *
	 * @param status New {@link #status}.
	 * @return Current builder.
	 */
	public HttpResponseMockBuilder setStatus(int status) {
		this.status = status;
		return this;
	}

	/**
	 * Set {@link #content}.
	 *
	 * @param content New {@link #content}.
	 * @return Current builder.
	 */
	public HttpResponseMockBuilder setContent(String content) {
		this.content = content;
		return this;
	}

	/**
	 * Add new header.
	 * Note that if an header already exist with this name, it will be override.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	public HttpResponseMockBuilder addHeader(String name, String value) {
		List<String> values = headers.get(name);
		if (values == null) {
			values = new LinkedList<>();
			headers.put(name, values);
		}

		values.add(value);
		return this;
	}

	/**
	 * Add new header.
	 * Note that if an header already exist with this name, it will be override.
	 *
	 * @param header Header.
	 * @return Current builder.
	 */
	public HttpResponseMockBuilder addHeader(Header header) {
		return addHeader(header.getName(), header.getValue());
	}

	/**
	 * Add new cookie.
	 *
	 * @param cookie Cookie to add.
	 * @return Current builder.
	 */
	public HttpResponseMockBuilder addCookie(Cookie cookie, Cookie... other) {
		this.cookies.add(cookie);
		addAll(cookies, other);
		return this;
	}

	/**
	 * Build mock of {@link HttpResponse} class.
	 *
	 * @return Mock instance.
	 */
	public HttpResponse build() {
		HttpResponse rsp = mock(HttpResponse.class);
		when(rsp.getStatus()).thenReturn(status);
		when(rsp.getContent()).thenReturn(content);

		when(rsp.getHeader(anyString())).thenAnswer(new Answer<List<String>>() {
			@Override
			public List<String> answer(InvocationOnMock invocation) throws Throwable {
				String name = (String) invocation.getArguments()[0];
				List<String> values = headers.get(name);
				if (values == null) {
					return Collections.emptyList();
				}

				return Collections.unmodifiableList(values);
			}
		});

		when(rsp.hasHeader(anyString())).thenAnswer(new Answer<Boolean>() {
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				String name = (String) invocation.getArguments()[0];
				return headers.containsKey(name);
			}
		});

		when(rsp.getCookies()).thenReturn(new ArrayList<>(cookies));

		return rsp;
	}
}
