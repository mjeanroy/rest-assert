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

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.JSON_CONTENT_TYPE;

class IsContentTypeEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final String VALUE = JSON_CONTENT_TYPE.getValue();

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isContentTypeEqualTo(response, VALUE);
	}

	@Override
	Header getHeader() {
		return JSON_CONTENT_TYPE;
	}

	@Override
	boolean allowMultipleValues() {
		return false;
	}

	@Test
	void it_should_be_a_case_insensitive_comparison() {
		run("APPLICATION/JSON; charset=utf-8");
	}

	@Test
	void it_should_compare_with_quoted_charset() {
		run("application/json; charset='utf-8'");
	}

	private static void run(String rawValue) {
		// GIVEN
		String name = "Content-Type";
		Header header = Header.header(name, rawValue);
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(header).build();

		// WHEN
		AssertionResult result = assertions.isContentTypeEqualTo(response, VALUE);

		// THEN
		checkSuccess(result);
	}
}
