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

package com.github.mjeanroy.restassert.core.internal.data.bindings.async;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;
import org.asynchttpclient.Response;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Implementation of {@link com.github.mjeanroy.restassert.core.internal.data.HttpResponse}
 * using Async-Http (version &gt;= 2.0.0) framework as real implementation.
 */
public class AsyncHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link com.github.mjeanroy.restassert.core.internal.data.HttpResponse} using instance
	 * of {@link org.asynchttpclient.Response}.
	 *
	 * @param response Original response object.
	 * @return Http response that can be used with rest-assert.
	 */
	public static AsyncHttpResponse create(Response response) {
		return new AsyncHttpResponse(response);
	}

	/**
	 * Original Async-Http response.
	 */
	private final Response response;

	// Use static factory
	private AsyncHttpResponse(Response response) {
		this.response = response;
	}

	@Override
	public int getStatus() {
		return response.getStatusCode();
	}

	@Override
	public List<String> getHeader(String name) {
		List<String> headers = response.getHeaders(name);
		if (headers.isEmpty()) {
			return emptyList();
		}

		return unmodifiableList(headers);
	}

	@Override
	protected String doGetContent() {
		return response.getResponseBody();
	}
}
