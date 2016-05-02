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

package com.github.mjeanroy.rest_assert.tests.mocks.async;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.CookieSerializer;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseHeaders;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;
import org.asynchttpclient.netty.NettyResponse;
import org.asynchttpclient.netty.NettyResponseStatus;
import org.asynchttpclient.uri.Uri;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instance of {@link org.asynchttpclient.Response} class.
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
	private final Map<String, List<String>> headers;

	/**
	 * Create new builder.
	 */
	public AsyncHttpResponseMockBuilder() {
		this.statusCode = 200;
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
		List<String> values = headers.get(name);
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
	public AsyncHttpResponseMockBuilder addCookie(Cookie cookie) {
		return addHeader("Set-Cookie", CookieSerializer.serialize(cookie));
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public Response build() {
		Uri uri = mock(Uri.class);
		AsyncHttpClientConfig config = mock(AsyncHttpClientConfig.class);

		io.netty.handler.codec.http.HttpResponseStatus rspStatus = io.netty.handler.codec.http.HttpResponseStatus.valueOf(statusCode);
		HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, rspStatus);
		HttpResponseStatus status = new NettyResponseStatus(uri, config, httpResponse, null);

		HttpHeaders headers = new DefaultHttpHeaders();
		for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
		}

		HttpResponseHeaders rspHeaders = new HttpResponseHeaders(headers);

		final List<HttpResponseBodyPart> bodyParts;
		if (responseBody != null) {
			final byte[] body = responseBody.getBytes(defaultCharset());
			HttpResponseBodyPart part = mock(HttpResponseBodyPart.class);
			when(part.length()).thenReturn(body.length);
			when(part.getBodyPartBytes()).thenReturn(body);
			bodyParts = singletonList(part);
		} else {
			bodyParts = emptyList();
		}

		Response rsp = new NettyResponse(status, rspHeaders, bodyParts);
		return spy(rsp);
	}
}
