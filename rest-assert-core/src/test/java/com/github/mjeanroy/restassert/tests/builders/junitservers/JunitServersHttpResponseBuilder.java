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

package com.github.mjeanroy.restassert.tests.builders.junitservers;

import com.github.mjeanroy.junit.servers.client.HttpHeader;
import com.github.mjeanroy.junit.servers.client.HttpResponse;
import com.github.mjeanroy.junit.servers.client.impl.AbstractHttpResponse;
import com.github.mjeanroy.restassert.tests.builders.AbstractHttpResponseBuilder;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Builder to create instance of {@link HttpResponse} class.
 */
public class JunitServersHttpResponseBuilder extends AbstractHttpResponseBuilder<HttpResponse, JunitServersHttpResponseBuilder> implements HttpResponseBuilder<HttpResponse> {

	@Override
	public HttpResponse build() {
		return new HttpResponseImpl(status, content, headers);
	}

	private static class HttpResponseImpl extends AbstractHttpResponse {
		private final int status;
		private final String content;
		private final Map<String, List<String>> headers;

		private HttpResponseImpl(int status, String content, Map<String, List<String>> headers) {
			this.status = status;
			this.content = content;
			this.headers = new LinkedHashMap<>(headers);
		}

		@Override
		public long getRequestDuration() {
			return 0;
		}

		@Override
		public long getRequestDurationInMillis() {
			return 0;
		}

		@Override
		public int status() {
			return status;
		}

		@Override
		public String body() {
			return content;
		}

		@Override
		public HttpHeader getHeader(String name) {
			if (!headers.containsKey(name)) {
				return null;
			}

			return HttpHeader.header(name, headers.get(name));
		}
	}
}
