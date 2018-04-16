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

package com.github.mjeanroy.restassert.core.internal.data.bindings.spring;

import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.data.bindings.AbstractHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Implementation to integrate spring-test into rest-assert.
 * This implementation translate a {@link ResultActions} (result of {@link MockMvc#perform(RequestBuilder)})
 * to an {@link HttpResponse} that can be used with rest-assert.
 */
public class SpringMockMvcHttpResponse extends AbstractHttpResponse implements HttpResponse {

	/**
	 * Create new {@link HttpResponse} using instance of {@link ResultActions} (result of
	 * the {@link MockMvc#perform(RequestBuilder)} method).
	 *
	 * @param resultActions Original result instance..
	 * @return Http response that can be used with rest-assert.
	 */
	public static SpringMockMvcHttpResponse create(ResultActions resultActions) {
		return new SpringMockMvcHttpResponse(resultActions.andReturn().getResponse());
	}

	/**
	 * The spring mock implementation of HTTP Servlet Response.
	 */
	private final MockHttpServletResponse response;

	private SpringMockMvcHttpResponse(MockHttpServletResponse response) {
		this.response = response;
	}

	@Override
	public int getStatus() {
		return response.getStatus();
	}

	@Override
	public boolean hasHeader(String name) {
		return response.getHeader(name) != null;
	}

	@Override
	public List<String> getHeader(String name) {
		return unmodifiableList(response.getHeaders(name));
	}

	@Override
	protected String doGetContent() throws IOException {
		return response.getContentAsString();
	}
}
