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

package com.github.mjeanroy.restassert.tests.builders.ning;

import com.github.mjeanroy.restassert.tests.builders.AbstractHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Builder to create mock instance of {@link Response} class.
 */
public class NingHttpResponseBuilder extends AbstractHttpResponseBuilder<Response, NingHttpResponseBuilder> implements HttpResponseBuilder<Response> {

	@Override
	public Response build() {
		Uri uri = mock(Uri.class);
		AsyncHttpProvider provider = mock(AsyncHttpProvider.class);
		AsyncHttpClientConfig config = mock(AsyncHttpClientConfig.class);
		HttpURLConnection conn = mock(HttpURLConnection.class);

		when(conn.getHeaderFields()).thenReturn(headers);

		try {
			final int status = this.status;
			when(conn.getResponseCode()).thenAnswer(new Answer<Integer>() {
				@Override
				public Integer answer(InvocationOnMock invocation) {
					return status;
				}
			});
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}

		HttpResponseStatus status = new ResponseStatus(uri, config, conn);
		HttpResponseHeaders headers = new ResponseHeaders(uri, conn, provider);

		final List<HttpResponseBodyPart> bodyParts;
		if (content != null) {
			byte[] body = content.getBytes(defaultCharset());
			HttpResponseBodyPart part = new ResponseBodyPart(body, true);
			bodyParts = singletonList(part);
		} else {
			bodyParts = emptyList();
		}

		return new JDKResponse(status, headers, bodyParts);
	}
}
