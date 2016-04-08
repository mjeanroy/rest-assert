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

package com.github.mjeanroy.rest_assert.internal.data.bindings.okhttp;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import com.github.mjeanroy.rest_assert.internal.data.bindings.AbstractHttpResponse;
import okhttp3.Response;

import java.io.IOException;

/**
 * Implementation of {@link HttpResponse} using OkHttp framework as real implementation.
 */
public class OkHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link HttpResponse} using instance of {@link okhttp3.Response}.
	 *
	 * @param response Original response object.
	 * @return Http response that can be used with rest-assert.
	 */
	public static OkHttpResponse create(Response response) {
		return new OkHttpResponse(response);
	}

	/**
	 * Original response.
	 */
	private final Response response;

	// Use static factory
	private OkHttpResponse(Response response) {
		this.response = response;
	}

	@Override
	protected String doGetContent() throws IOException {
		return response.body().string();
	}

	@Override
	public int getStatus() {
		return response.code();
	}

	@Override
	public boolean hasHeader(String name) {
		return getHeader(name) != null;
	}

	@Override
	public String getHeader(String name) {
		return response.header(name);
	}
}
