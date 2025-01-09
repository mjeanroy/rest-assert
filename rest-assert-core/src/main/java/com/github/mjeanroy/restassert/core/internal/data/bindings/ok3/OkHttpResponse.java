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

package com.github.mjeanroy.restassert.core.internal.data.bindings.ok3;

import com.github.mjeanroy.restassert.core.data.HttpHeader;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static java.util.Collections.unmodifiableList;

/**
 * Implementation of {@link HttpResponse} using OkHttp framework as real implementation.
 */
public class OkHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link HttpResponse} using instance of {@link okhttp3.Response},
	 * or returns {@code null} if {@code response} is {@code null}.
	 *
	 * @param response Original response object.
	 * @return Http response that can be used with rest-assert.
	 */
	public static OkHttpResponse create(Response response) {
		return response == null ? null : new OkHttpResponse(response);
	}

	/**
	 * Original response.
	 */
	private final Response response;

	// Use static factory
	private OkHttpResponse(Response response) {
		this.response = notNull(response, "Response must not be null");
	}

	@Override
	protected String doGetContent() throws IOException {
		ResponseBody body = response.body();
		return body == null ? "" : body.string();
	}

	@Override
	public int getStatus() {
		return response.code();
	}

	@Override
	public List<String> getHeader(String name) {
		List<String> values = response.headers(name);
		return unmodifiableList(values);
	}

	@Override
	public List<HttpHeader> getHeaders() {
		return unmodifiableList(
			response.headers().names().stream()
				.map((name) -> HttpHeader.of(name, getHeader(name)))
				.collect(Collectors.toList())
		);
	}
}
