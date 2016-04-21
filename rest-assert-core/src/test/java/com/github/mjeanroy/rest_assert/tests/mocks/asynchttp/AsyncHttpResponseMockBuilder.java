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

package com.github.mjeanroy.rest_assert.tests.mocks.asynchttp;

import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instance of {@link Response} class.
 */
public class AsyncHttpResponseMockBuilder {

	/**
	 * Response status code.
	 */
	private int statusCode;

	/**
	 * Response body.
	 */
	private String responseBody;

	/**
	 * Response headers.
	 */
	private final Map<String, Collection<String>> headers;

	/**
	 * Create new builder.
	 */
	public AsyncHttpResponseMockBuilder() {
		this.headers = new LinkedHashMap<>();
	}

	/**
	 * Set {@link #statusCode}.
	 *
	 * @param statusCode New {@link #statusCode}.
	 * @return Current builder.
	 */
	public AsyncHttpResponseMockBuilder setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	/**
	 * Set {@link #responseBody}.
	 *
	 * @param responseBody New {@link #responseBody}.
	 * @return Current builder.
	 */
	public AsyncHttpResponseMockBuilder setResponseBody(String responseBody) {
		this.responseBody = responseBody;
		return this;
	}

	/**
	 * Add new header.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	public AsyncHttpResponseMockBuilder addHeader(String name, String value) {
		Collection<String> values = headers.get(name);
		if (values == null) {
			values = new LinkedHashSet<>();
			headers.put(name, values);
		}

		values.add(value);
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public Response build() {
		Response rsp = mock(Response.class);
		when(rsp.getStatusCode()).thenReturn(statusCode);

		try {
			when(rsp.getResponseBody()).thenReturn(responseBody);
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}

		final FluentCaseInsensitiveStringsMap map = new FluentCaseInsensitiveStringsMap(headers);
		when(rsp.getHeaders()).thenReturn(map);

		when(rsp.getHeader(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) {
				String headerName = (String) invocation.getArguments()[0];
				return map.containsKey(headerName) ? map.getFirstValue(headerName) : null;
			}
		});

		when(rsp.getHeaders(anyString())).thenAnswer(new Answer<List<String>>() {
			@Override
			public List<String> answer(InvocationOnMock invocation) {
				String headerName = (String) invocation.getArguments()[0];
				return map.get(headerName);
			}
		});

		return rsp;
	}
}
