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

package com.github.mjeanroy.restassert.core.internal.data.bindings.apache;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Implementation of {@link com.github.mjeanroy.restassert.core.internal.data.HttpResponse}
 * using Apache HttpClient framework as real implementation.
 */
public class ApacheHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link com.github.mjeanroy.restassert.core.internal.data.HttpResponse} using instance
	 * of {@link org.apache.http.HttpResponse}.
	 *
	 * @param response Original response object.
	 * @return Http response that can be used with rest-assert.
	 */
	public static ApacheHttpResponse create(org.apache.http.HttpResponse response) {
		return new ApacheHttpResponse(response);
	}

	/**
	 * Original http response.
	 */
	private final org.apache.http.HttpResponse response;

	// Use static factory
	private ApacheHttpResponse(org.apache.http.HttpResponse response) {
		this.response = response;
	}

	@Override
	public int getStatus() {
		return response.getStatusLine().getStatusCode();
	}

	@Override
	public List<String> getHeader(String name) {
		Header[] headers = response.getHeaders(name);

		int size = headers == null ? 0 : headers.length;
		if (size == 0) {
			return emptyList();
		}

		List<String> results = new ArrayList<>(size);
		for (Header header : headers) {
			results.add(header.getValue());
		}

		return unmodifiableList(results);
	}

	@Override
	protected String doGetContent() throws IOException {
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}
}
