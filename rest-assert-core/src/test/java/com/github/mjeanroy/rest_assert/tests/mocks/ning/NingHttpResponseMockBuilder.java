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

package com.github.mjeanroy.rest_assert.tests.mocks.ning;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.mjeanroy.rest_assert.internal.data.Cookie;
import com.github.mjeanroy.rest_assert.tests.CookieSerializer;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpProvider;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;
import com.ning.http.client.Response;
import com.ning.http.client.providers.jdk.JDKResponse;
import com.ning.http.client.providers.jdk.ResponseBodyPart;
import com.ning.http.client.providers.jdk.ResponseHeaders;
import com.ning.http.client.providers.jdk.ResponseStatus;
import com.ning.http.client.uri.Uri;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instance of {@link Response} class.
 */
public class NingHttpResponseMockBuilder {

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
	public NingHttpResponseMockBuilder() {
		this.statusCode = 200;
		this.headers = new LinkedHashMap<>();
	}

	/**
	 * Set {@link #statusCode}.
	 *
	 * @param statusCode New {@link #statusCode}.
	 * @return Current builder.
	 */
	public NingHttpResponseMockBuilder setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	/**
	 * Set {@link #responseBody}.
	 *
	 * @param responseBody New {@link #responseBody}.
	 * @return Current builder.
	 */
	public NingHttpResponseMockBuilder setResponseBody(String responseBody) {
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
	public NingHttpResponseMockBuilder addHeader(String name, String value) {
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
	public NingHttpResponseMockBuilder addCookie(Cookie cookie) {
		return addHeader("Set-Cookie", CookieSerializer.serialize(cookie));
	}

	/**
	 * Create mock instance.
	 *
	 * @return Mock instance.
	 */
	public Response build() {
		Uri uri = mock(Uri.class);
		AsyncHttpProvider provider = mock(AsyncHttpProvider.class);
		AsyncHttpClientConfig config = mock(AsyncHttpClientConfig.class);
		HttpURLConnection conn = mock(HttpURLConnection.class);

		when(conn.getHeaderFields()).thenReturn(headers);

		try {
			when(conn.getResponseCode()).thenAnswer(new Answer<Integer>() {
				@Override
				public Integer answer(InvocationOnMock invocation) throws Throwable {
					return statusCode;
				}
			});
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}

		HttpResponseStatus status = new ResponseStatus(uri, config, conn);
		HttpResponseHeaders headers = new ResponseHeaders(uri, conn, provider);

		final List<HttpResponseBodyPart> bodyParts;
		if (responseBody != null) {
			byte[] body = responseBody.getBytes(defaultCharset());
			HttpResponseBodyPart part = new ResponseBodyPart(body, true);
			bodyParts = singletonList(part);
		} else {
			bodyParts = emptyList();
		}

		Response rsp = new JDKResponse(status, headers, bodyParts);
		return spy(rsp);
	}
}
