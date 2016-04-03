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

package com.github.mjeanroy.rest_assert.tests.mocks.apache_http_client;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Collections.addAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Create mock instance of {@link HttpResponse} class.
 */
public class ApacheHttpResponseMockBuilder {

	/**
	 * Http status code.
	 */
	private StatusLine statusLine;

	/**
	 * Set of http response headers.
	 */
	private final Set<Header> headers;

	/**
	 * Http response body.
	 */
	private HttpEntity entity;

	public ApacheHttpResponseMockBuilder() {
		this.headers = new LinkedHashSet<>();
	}

	/**
	 * Add new header.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	public ApacheHttpResponseMockBuilder addHeader(String name, String value) {
		return addHeaders(new ApacheHttpHeaderMockBuilder()
			.setName(name)
			.setValue(value)
			.build());
	}

	/**
	 * Add new headers.
	 *
	 * @param header Header.
	 * @param other Optional other headers.
	 * @return Current builder.
	 */
	public ApacheHttpResponseMockBuilder addHeaders(Header header, Header... other) {
		headers.add(header);
		addAll(headers, other);
		return this;
	}

	/**
	 * Set {@link #entity}.
	 *
	 * @param entity New {@link #entity}.
	 * @return Current builder.
	 */
	public ApacheHttpResponseMockBuilder setEntity(HttpEntity entity) {
		this.entity = entity;
		return this;
	}

	/**
	 * Set {@link #statusLine}.
	 *
	 * @param statusLine New {@link #statusLine}.
	 * @return Current builder.
	 */
	public ApacheHttpResponseMockBuilder setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
		return this;
	}

	/**
	 * Create mock instance of {@link HttpResponse} class.
	 *
	 * @return Mock instance.
	 */
	public HttpResponse build() {
		HttpResponse rsp = mock(HttpResponse.class);
		when(rsp.getStatusLine()).thenReturn(statusLine);
		when(rsp.getAllHeaders()).thenReturn(headers.toArray(new Header[headers.size()]));
		when(rsp.getEntity()).thenReturn(entity);
		return rsp;
	}
}
