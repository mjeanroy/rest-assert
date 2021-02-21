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

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Test;

public class IsAccessControlExposeHeadersEqualToTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = ACCESS_CONTROL_EXPOSE_HEADERS;
	private static final String NAME = HEADER.getName();
	private static final String VALUE = HEADER.getValue();

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isAccessControlExposeHeadersEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return false;
	}

	@Test
	public void it_should_compare_with_single_string() {
		final String actual = "X-Foo, X-Bar";
		final String expected = "X-Bar, X-Foo";
		doTestSuccess(actual, expected);
	}

	@Test
	public void it_should_compare_case_insensitively() {
		final String actual = "x-foo, x-bar";
		final String expected = "X-Foo, X-Bar";
		doTestSuccess(actual, expected);
	}

	private void doTestSuccess(String actual, String expected) {
		final HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();
		final AssertionResult result = assertions.isAccessControlExposeHeadersEqualTo(response, expected);
		checkSuccess(result);
	}
}
