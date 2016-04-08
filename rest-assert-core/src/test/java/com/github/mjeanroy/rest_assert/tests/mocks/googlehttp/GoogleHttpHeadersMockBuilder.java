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

package com.github.mjeanroy.rest_assert.tests.mocks.googlehttp;

import com.google.api.client.http.HttpHeaders;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instance of {@link HttpHeaders} class.
 */
public class GoogleHttpHeadersMockBuilder {

	/**
	 * Map of headers.
	 */
	private final Map<String, Collection<String>> headers;

	/**
	 * Create new builder.
	 */
	public GoogleHttpHeadersMockBuilder() {
		this.headers = new LinkedHashMap<>();
	}

	/**
	 * Add new header value.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	public GoogleHttpHeadersMockBuilder addHeader(String name, String value) {
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
	public HttpHeaders build() {
		final HttpHeaders httpHeaders = mock(HttpHeaders.class);

		when(httpHeaders.getFirstHeaderStringValue(anyString())).thenAnswer(new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				String headerName = (String) invocation.getArguments()[0];
				Collection<String> values = headers.get(headerName);
				return values == null ? null : values.iterator().next();
			}
		});

		return httpHeaders;
	}
}
