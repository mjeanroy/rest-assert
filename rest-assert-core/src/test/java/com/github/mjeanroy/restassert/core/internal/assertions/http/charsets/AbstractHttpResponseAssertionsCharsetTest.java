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

package com.github.mjeanroy.restassert.core.internal.assertions.http.charsets;

import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static java.lang.String.format;

abstract class AbstractHttpResponseAssertionsCharsetTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	void it_should_pass() {
		HttpResponse response = newResponse(expectedCharset());
		AssertionResult result = run(response);
		checkSuccess(result);
	}

	@Test
	void it_should_fail() {
		String expectedCharset = expectedCharset();
		String actualCharset = expectedCharset + "foo";
		HttpResponse httpResponse = newResponse(actualCharset);

		AssertionResult result = run(httpResponse);

		checkError(
			result,
			"Expecting response to have charset " + fmt(expectedCharset) + " but was " + fmt(actualCharset)
		);
	}

	@Test
	void it_should_fail_if_response_is_null() {
		AssertionResult result = run(null);
		checkError(result, "Expecting HTTP Response not to be null");
	}

	abstract String expectedCharset();

	private static HttpResponse newResponse(String charset) {
		String contentType = format("application/json; charset=%s", charset);
		return new HttpResponseBuilderImpl().addHeader("Content-Type", contentType).build();
	}
}
