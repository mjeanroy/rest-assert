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

package com.github.mjeanroy.rest_assert.tests.mocks.google_http;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instance of {@link HttpResponse} class.
 */
public class GoogleHttpResponseMockBuilder {

	/**
	 * Response status code.
	 */
	private int statusCode;

	/**
	 * Response content.
	 */
	private InputStream content;

	/**
	 * Response content charset.
	 */
	private Charset charset;

	/**
	 * Response headers.
	 */
	private HttpHeaders headers;

	/**
	 * Set {@link #statusCode}.
	 *
	 * @param statusCode New {@link #statusCode}.
	 * @return Current builder.
	 */
	public GoogleHttpResponseMockBuilder setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	/**
	 * Set {@link #headers}.
	 *
	 * @param headers New {@link #headers}.
	 * @return Current builder.
	 */
	public GoogleHttpResponseMockBuilder setHeaders(HttpHeaders headers) {
		this.headers = headers;
		return this;
	}

	/**
	 * Set {@link #content}.
	 *
	 * @param charset New {@link #charset}.
	 * @param content New {@link #content}.
	 * @return Current builder.
	 */
	public GoogleHttpResponseMockBuilder setContent(Charset charset, String content) {
		this.content = new ByteArrayInputStream(content.getBytes());
		this.charset = charset;
		return this;
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public HttpResponse build() {
		HttpResponse rsp = mock(HttpResponse.class);
		when(rsp.getStatusCode()).thenReturn(statusCode);
		when(rsp.getHeaders()).thenReturn(headers);

		try {
			when(rsp.getContent()).thenReturn(content);
			when(rsp.getContentCharset()).thenReturn(charset);
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}

		return rsp;
	}
}
