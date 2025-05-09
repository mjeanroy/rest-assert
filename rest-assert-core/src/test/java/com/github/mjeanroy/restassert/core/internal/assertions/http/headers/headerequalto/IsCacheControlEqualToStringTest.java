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

package com.github.mjeanroy.restassert.core.internal.assertions.http.headers.headerequalto;

import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.CACHE_CONTROL;

class IsCacheControlEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = CACHE_CONTROL;
	private static final String VALUE = HEADER.getValue();
	private static final String NAME = HEADER.getName();

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isCacheControlEqualTo(response, VALUE);
	}

	@Override
	Header getHeader() {
		return HEADER;
	}

	@Override
	boolean allowMultipleValues() {
		return true;
	}

	@Override
	String failValue() {
		return "public, no-transform, max-age=0";
	}

	@Test
	void it_should_compare_case_insensitively() {
		String actual = VALUE.toUpperCase();
		String expected = VALUE.toUpperCase();
		doTest(actual, expected);
	}

	@Test
	void it_should_compare_in_different_order() {
		String actual = "public, no-transform, no-store";
		String expected = "public, no-store, no-transform";
		doTest(actual, expected);
	}

	private static void doTest(String actual, String expected) {
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();
		AssertionResult result = assertions.isCacheControlEqualTo(response, expected);
		checkSuccess(result);
	}
}
