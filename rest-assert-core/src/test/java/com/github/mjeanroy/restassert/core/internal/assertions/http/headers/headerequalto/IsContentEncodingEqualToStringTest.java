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

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.GZIP_CONTENT_ENCODING;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Test;

public class IsContentEncodingEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = GZIP_CONTENT_ENCODING;
	private static final String VALUE = HEADER.getValue();
	private static final String HEADER_NAME = HEADER.getName();
	private static final String FAILED_VALUE = "deflate";

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isContentEncodingEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return false;
	}

	@Override
	String failValue() {
		return FAILED_VALUE;
	}

	@Test
	public void it_should_pass_with_case_insensitive_comparison() {
		final String actual = VALUE.toUpperCase();
		final String expected = actual.toLowerCase();
		doTestSuccess(actual, expected);
	}

	@Test
	public void it_should_pass_with_list_comparison() {
		final String actual = "compress, identity";
		final String expected = actual.toUpperCase();
		doTestSuccess(actual, expected);
	}

	@Test
	public void it_should_not_pass_with_list_in_wrong_order() {
		// GIVEN
		final String expected = "compress, identity";
		final String actual = "identity, compress";
		final HttpResponse response = new HttpResponseBuilderImpl().addHeader(HEADER_NAME, actual).build();

		// WHEN
		final AssertionResult result = assertions.isContentEncodingEqualTo(response, expected);

		// THEN
		final Class<ShouldHaveHeader> klassError = ShouldHaveHeader.class;
		final String message = "Expecting response to have header %s equal to %s but was %s";
		final Object[] args = {HEADER_NAME, expected, actual};
		checkError(result, klassError, message, args);
	}

	private void doTestSuccess(String actual, String expected) {
		final HttpResponse response = new HttpResponseBuilderImpl().addHeader(HEADER_NAME, actual).build();
		final AssertionResult result = assertions.isContentEncodingEqualTo(response, expected);
		checkSuccess(result);
	}
}
