/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2025 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.data.bindings.junitservers;

import com.github.mjeanroy.junit.servers.client.HttpHeader;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.mjeanroy.restassert.core.internal.common.PreConditions.notNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

/**
 * Implementation to integrate junit-servers into rest-assert.
 */
public class JunitServersHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link HttpResponse} using instance of {@link com.github.mjeanroy.junit.servers.client.HttpResponse},
	 * or returns {@code null} if {@code response} is {@code null}.
	 *
	 * @param response Original response instance..
	 * @return Http response that can be used with rest-assert.
	 */
	public static JunitServersHttpResponse create(com.github.mjeanroy.junit.servers.client.HttpResponse response) {
		return response == null ? null : new JunitServersHttpResponse(response);
	}

	/**
	 * The original response.
	 */
	private final com.github.mjeanroy.junit.servers.client.HttpResponse response;

	// Use static factory
	private JunitServersHttpResponse(com.github.mjeanroy.junit.servers.client.HttpResponse response) {
		this.response = notNull(response, "Response must not be null");
	}

	@Override
	protected String doGetContent() {
		return response.body();
	}

	@Override
	public int getStatus() {
		return response.status();
	}

	@Override
	public List<String> getHeader(String name) {
		HttpHeader header = response.getHeader(name);

		if (header == null) {
			return emptyList();
		}

		return unmodifiableList(
			header.getValues()
		);
	}

	@Override
	public List<com.github.mjeanroy.restassert.core.data.HttpHeader> getHeaders() {
		return unmodifiableList(
			response.getHeaders().stream()
				.map(h -> com.github.mjeanroy.restassert.core.data.HttpHeader.of(h.getName(), h.getValues()))
				.collect(Collectors.toList())
		);
	}
}
