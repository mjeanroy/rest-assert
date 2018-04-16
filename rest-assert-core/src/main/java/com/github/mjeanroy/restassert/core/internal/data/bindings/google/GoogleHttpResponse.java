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

package com.github.mjeanroy.restassert.core.internal.data.bindings.google;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;
import com.google.api.client.http.HttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.google.api.client.util.IOUtils.copy;
import static java.util.Collections.emptyList;

/**
 * Implementation of {@link com.github.mjeanroy.restassert.core.internal.data.HttpResponse}
 * using Google Http Client framework as real implementation.
 */
public class GoogleHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link com.github.mjeanroy.restassert.core.internal.data.HttpResponse} using instance
	 * of {@link com.ning.http.client.Response}.
	 *
	 * @param response Original response object.
	 * @return Http response that can be used with rest-assert.
	 */
	public static GoogleHttpResponse create(com.google.api.client.http.HttpResponse response) {
		return new GoogleHttpResponse(response);
	}

	/**
	 * Original Google Http Response.
	 */
	private final com.google.api.client.http.HttpResponse response;

	// Use static factory
	private GoogleHttpResponse(com.google.api.client.http.HttpResponse response) {
		this.response = response;
	}

	@Override
	public int getStatus() {
		return response.getStatusCode();
	}

	@Override
	public List<String> getHeader(String name) {
		HttpHeaders headers = response.getHeaders();
		if (headers == null || headers.isEmpty()) {
			return emptyList();
		}

		return headers.getHeaderStringValues(name);
	}

	@Override
	protected String doGetContent() throws IOException {
		try (InputStream is = response.getContent(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			copy(is, bos);
			return new String(bos.toByteArray(), response.getContentCharset());
		}
	}
}
