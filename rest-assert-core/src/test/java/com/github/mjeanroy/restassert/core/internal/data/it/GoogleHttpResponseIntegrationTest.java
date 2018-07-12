/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014-2018 Mickael Jeanroy
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.restassert.core.internal.data.it;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.google.GoogleHttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.ning.NingHttpResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import java.util.concurrent.Future;

public class GoogleHttpResponseIntegrationTest extends AbstractHttpResponseIntegrationTest {

	@Override
	HttpResponse createHttpResponse(String url) throws Exception {
		HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
		HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
		return GoogleHttpResponse.create(request.execute());
	}
}
