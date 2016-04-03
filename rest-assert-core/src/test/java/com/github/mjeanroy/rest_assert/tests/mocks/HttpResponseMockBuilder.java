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

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.tests.models.Header;

import java.util.LinkedHashMap;
import java.util.Map;

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
	private final Map<String, String> headers;

	/**
	 * Create builder.
	 */
	public HttpResponseMockBuilder() {
		this.headers = new LinkedHashMap<>();
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
		this.headers.put(name, value);
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
	 * Build mock of {@link HttpResponse} class.
	 *
	 * @return Mock instance.
	 */
	public HttpResponse build() {
		HttpResponse rsp = mock(HttpResponse.class);
		when(rsp.getStatus()).thenReturn(status);
		when(rsp.getContent()).thenReturn(content);

		for (Map.Entry<String, String> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			String headerValue = entry.getValue();

			when(rsp.hasHeader(headerName)).thenReturn(true);
			when(rsp.getHeader(headerName)).thenReturn(headerValue);
		}

		return rsp;
	}
}
