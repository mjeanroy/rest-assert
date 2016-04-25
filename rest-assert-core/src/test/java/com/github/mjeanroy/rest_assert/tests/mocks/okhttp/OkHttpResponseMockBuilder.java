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

package com.github.mjeanroy.rest_assert.tests.mocks.okhttp;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.CookieSerializer;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.mockito.Mockito.spy;

/**
 * Builder to create mock instance of {@link com.google.api.client.http.HttpResponse} class.
 */
public class OkHttpResponseMockBuilder {

	/**
	 * Response status code.
	 */
	private int code;

	/**
	 * HTTP Response body.
	 */
	private ResponseBody body;

	/**
	 * List of headers.
	 */
	private final Map<String, LinkedList<String>> headers;

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
	public OkHttpResponseMockBuilder() {
		this.request = new Request.Builder()
				.url("http://www.google.fr")
				.build();

		this.headers = new LinkedHashMap<>();
		this.protocol = Protocol.HTTP_1_0;
	}

	/**
	 * Set {@link #code}.
	 *
	 * @param code New {@link #code}.
	 * @return Current builder.
	 */
	public OkHttpResponseMockBuilder setCode(int code) {
		this.code = code;
		return this;
	}

	/**
	 * Set {@link #body}.
	 *
	 * @param body New {@link #body}.
	 * @return Current builder.
	 */
	public OkHttpResponseMockBuilder setBody(ResponseBody body) {
		this.body = body;
		return this;
	}

	/**
	 * Add new header.
	 *
	 * @param name Header name.
	 * @param value Header value.
	 * @return Current builder.
	 */
	public OkHttpResponseMockBuilder addHeader(String name, String value) {
		LinkedList<String> values = headers.get(name);
		if (values == null) {
			values = new LinkedList<>();
			headers.put(name, values);
		}

		values.add(value);
		return this;
	}

	/**
	 * Add new cookie.
	 *
	 * @param cookie Cookie.
	 * @return Current builder.
	 */
	public OkHttpResponseMockBuilder addCookie(Cookie cookie) {
		return addHeader("Set-Cookie", CookieSerializer.serialize(cookie));
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public Response build() {
		Response.Builder builder = new Response.Builder()
				.request(request)
				.protocol(protocol)
				.code(code)
				.body(body);

		for (Map.Entry<String, LinkedList<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			for (String headerValue : entry.getValue()) {
				builder.addHeader(headerName, headerValue);
			}
		}

		Response response = builder.build();
		return spy(response);
	}
}
