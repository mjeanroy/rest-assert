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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.hasheader;

import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader;
import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.restassert.test.data.Header.header;

public abstract class AbstractHttpHeaderTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_expected_header() {
		// GIVEN
		final Header header = getHeader();
		final HttpResponse rsp = newResponse(header);

		// WHEN
		AssertionResult result = invoke(rsp);

		// THEN
		checkSuccess(result);
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header() {
		// GIVEN
		final Header expectedHeader = getHeader();
		final String expectedName = expectedHeader.getName();
		final Header header = header(expectedHeader.getValue(), expectedName);
		final HttpResponse rsp = newResponse(header);

		// WHEN
		AssertionResult result = invoke(rsp);

		// THEN
		final String message = "Expecting response to have header %s";
		checkError(result, ShouldHaveHeader.class, message, expectedName);
	}

	/**
	 * Create fake http response with given {@code header}.
	 *
	 * @param header Header.
	 * @return Fake http response.
	 */
	private HttpResponse newResponse(Header header) {
		return new HttpResponseBuilderImpl()
				.addHeader(header)
				.build();
	}

	/**
	 * Get expected header.
	 *
	 * @return Expected header.
	 */
	protected abstract Header getHeader();
}
