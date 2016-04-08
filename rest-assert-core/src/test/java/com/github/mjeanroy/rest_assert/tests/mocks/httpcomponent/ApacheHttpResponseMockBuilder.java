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

package com.github.mjeanroy.rest_assert.tests.mocks.httpcomponent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.mockito.Mockito.spy;

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
	private final Map<String, LinkedList<String>> headers;

	/**
	 * Http response body.
	 */
	private HttpEntity entity;

	/**
	 * Create new builder.
	 */
	public ApacheHttpResponseMockBuilder() {
		this.statusLine = new ApacheHttpStatusLineMockBuilder().build();
		this.headers = new LinkedHashMap<>();
	}

	/**
	 * Add new header.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	public ApacheHttpResponseMockBuilder addHeader(String name, String value) {
		LinkedList<String> values = headers.get(name);
		if (values == null) {
			values = new LinkedList<>();
			headers.put(name, values);
		}

		values.add(value);
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
		HttpResponse rsp = new BasicHttpResponse(statusLine);
		rsp.setEntity(entity);

		for (Map.Entry<String, LinkedList<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (String headerValue : entry.getValue()) {
				rsp.addHeader(headerName, headerValue);
			}
		}

		return spy(rsp);
	}
}
