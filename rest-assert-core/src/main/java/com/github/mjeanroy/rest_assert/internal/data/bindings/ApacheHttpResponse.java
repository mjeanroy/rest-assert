/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 <mickael.jeanroy@gmail.com>
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

package com.github.mjeanroy.rest_assert.internal.data.bindings;

import com.github.mjeanroy.rest_assert.internal.data.HttpResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Implementation of {@link com.github.mjeanroy.rest_assert.internal.data.HttpResponse}
 * using Apache HttpClient framework as real implementation.
 */
public class ApacheHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link com.github.mjeanroy.rest_assert.internal.data.HttpResponse} using instance
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
	public boolean hasHeader(String name) {
		return findFirstHeader(name) != null;
	}

	@Override
	public String getHeader(String name) {
		Header header = findFirstHeader(name);
		return header == null ? null : header.getValue();
	}

	@Override
	protected String doGetContent() throws IOException {
		HttpEntity entity = response.getEntity();
		return EntityUtils.toString(entity);
	}

	private Header findFirstHeader(String name) {
		Header[] headers = response.getAllHeaders();
		for (Header header : headers) {
			if (header.getName().toLowerCase().equals(name.toLowerCase())) {
				return header;
			}
		}
		return null;
	}
}
