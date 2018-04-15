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

package com.github.mjeanroy.restassert.tests.builders.ok;

import com.github.mjeanroy.restassert.tests.builders.AbstractHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import java.util.List;
import java.util.Map;

/**
 * Builder to create mock instance of {@link com.google.api.client.http.HttpResponse} class.
 */
public class OkHttpResponseBuilder extends AbstractHttpResponseBuilder<Response, OkHttpResponseBuilder> implements HttpResponseBuilder<Response> {

	/**
	 * Original HTTP request.
	 * This field is necessary to build a valid HTTP Response.
	 */
	private final Request request;

	/**
	 * Original HTTP protocol.
	 * This field is necessary to build a valid HTTP Response.
	 */
	private final Protocol protocol;

	/**
	 * Create builder.
	 */
	public OkHttpResponseBuilder() {
		this.protocol = Protocol.HTTP_1_0;
		this.request = new Request.Builder().url("http://www.google.fr").build();
	}

	@Override
	public Response build() {
		Response.Builder builder = new Response.Builder()
				.request(request)
				.protocol(protocol)
				.code(status);

		if (content != null) {
			builder.body(new OkHttpResponseBodyBuilder().setBody(content).build());
		}

		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (String headerValue : entry.getValue()) {
				builder.addHeader(headerName, headerValue);
			}
		}

		builder.message("OK");
		return builder.build();
	}
}
