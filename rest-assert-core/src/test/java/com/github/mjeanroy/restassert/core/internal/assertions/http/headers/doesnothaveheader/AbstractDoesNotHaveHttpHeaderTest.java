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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.doesnothaveheader;

import com.github.mjeanroy.restassert.core.internal.error.http.ShouldNotHaveHeader;
import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractDoesNotHaveHttpHeaderTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_empty_headers() {
		// GIVEN
		final HttpResponse rsp = newResponse();

		// WHEN
		AssertionResult result = invoke(rsp);

		// THEN
		checkSuccess(result);
	}

	@Test
	public void it_should_fail_with_if_response_contain_header() {
		// GIVEN
		final Header header = getHeader();
		final HttpResponse rsp = newResponse(header);

		// WHEN
		AssertionResult result = invoke(rsp);

		// THEN
		final String message = "Expecting response not to have header %s";
		final String args = header.getName();
		checkError(result, ShouldNotHaveHeader.class, message, args);
	}

	/**
	 * Create fake http response without any header.
	 *
	 * @return Http response.
	 */
	private HttpResponse newResponse() {
		return new HttpResponseBuilderImpl().build();
	}

	/**
	 * Create fake http header with expected header.
	 *
	 * @param header Expected header.
	 * @return Fake http response.
	 */
	private HttpResponse newResponse(Header header) {
		return new HttpResponseBuilderImpl()
				.addHeader(header)
				.build();
	}

	/**
	 * Get expected header (tested header).
	 *
	 * @return Expected header.
	 */
	protected abstract Header getHeader();
}
