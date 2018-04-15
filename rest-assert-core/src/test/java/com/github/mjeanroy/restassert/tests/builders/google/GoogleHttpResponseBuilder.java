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

package com.github.mjeanroy.restassert.tests.builders.google;

import com.github.mjeanroy.restassert.tests.builders.AbstractHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;

import java.util.List;
import java.util.Map;

/**
 * Builder to create mock instance of {@link HttpResponse} class.
 */
public class GoogleHttpResponseBuilder extends AbstractHttpResponseBuilder<HttpResponse, GoogleHttpResponseBuilder> implements HttpResponseBuilder<HttpResponse> {

	/**
	 * Create builder for {@link HttpResponse} with default values.
	 */
	public GoogleHttpResponseBuilder() {
		this.status = 200;
	}

	@Override
	public HttpResponse build() {
		return createHttpResponse();
	}

	private HttpResponse createHttpResponse() {
		try {
			HttpRequest request = createHttpRequest();
			return request.execute();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	private HttpRequest createHttpRequest() {
		try {
			HttpTransport transport = new MockHttpTransportImpl(status, content, headers);
			return transport.createRequestFactory()
					.buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL)
					.setThrowExceptionOnExecuteError(false);

		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	/**
	 * A specialized implementation for {@link MockHttpTransport}.
	 */
	private static class MockHttpTransportImpl extends MockHttpTransport {
		/**
		 * Response status code.
		 */
		private final int status;

		/**
		 * Response content.
		 */
		private final String content;

		/**
		 * Response headers.
		 */
		private final Map<String, List<String>> headers;

		private MockHttpTransportImpl(int status, String content, Map<String, List<String>> headers) {
			this.status = status;
			this.content = content;
			this.headers = headers;
		}

		@Override
		public LowLevelHttpRequest buildRequest(String method, String url) {
			return new MockLowLevelHttpRequestImpl(status, content, headers);
		}
	}

	/**
	 * A specialized implementation for {@link MockLowLevelHttpRequest}.
	 */
	private static class MockLowLevelHttpRequestImpl extends MockLowLevelHttpRequest {
		/**
		 * Response status code.
		 */
		private final int status;

		/**
		 * Response content.
		 */
		private final String content;

		/**
		 * Response headers.
		 */
		private final Map<String, List<String>> headers;

		private MockLowLevelHttpRequestImpl(int status, String content, Map<String, List<String>> headers) {
			this.status = status;
			this.content = content;
			this.headers = headers;
		}

		@Override
		public LowLevelHttpResponse execute() {
			MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
			response.setStatusCode(status);
			response.setContent(content);

			// Add headers
			for (Map.Entry<String, List<String>> entry : this.headers.entrySet()) {
				String key = entry.getKey();
				for (String value : entry.getValue()) {
					response.addHeader(key, value);
				}
			}

			return response;
		}
	}
}
