/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2021 Mickael Jeanroy
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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.headerequalto;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.GZIP_CONTENT_ENCODING;

class IsContentEncodingEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = GZIP_CONTENT_ENCODING;
	private static final String VALUE = HEADER.getValue();
	private static final String HEADER_NAME = HEADER.getName();
	private static final String FAILED_VALUE = "deflate";

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isContentEncodingEqualTo(response, VALUE);
	}

	@Override
	Header getHeader() {
		return HEADER;
	}

	@Override
	boolean allowMultipleValues() {
		return false;
	}

	@Override
	String failValue() {
		return FAILED_VALUE;
	}

	@Test
	void it_should_pass_with_case_insensitive_comparison() {
		String actual = VALUE.toUpperCase();
		String expected = actual.toLowerCase();
		doTestSuccess(actual, expected);
	}

	@Test
	void it_should_pass_with_list_comparison() {
		String actual = "compress, identity";
		String expected = actual.toUpperCase();
		doTestSuccess(actual, expected);
	}

	@Test
	void it_should_not_pass_with_list_in_wrong_order() {
		// GIVEN
		String expected = "compress, identity";
		String actual = "identity, compress";
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(HEADER_NAME, actual).build();

		// WHEN
		AssertionResult result = assertions.isContentEncodingEqualTo(response, expected);

		// THEN
		checkError(
			result,
			String.format("Expecting response to have header %s equal to %s but was %s", HEADER_NAME, expected, actual)
		);
	}

	private static void doTestSuccess(String actual, String expected) {
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(HEADER_NAME, actual).build();
		AssertionResult result = assertions.isContentEncodingEqualTo(response, expected);
		checkSuccess(result);
	}
}
