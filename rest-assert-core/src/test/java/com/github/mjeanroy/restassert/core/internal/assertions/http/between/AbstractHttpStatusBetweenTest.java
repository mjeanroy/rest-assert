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

package com.github.mjeanroy.restassert.core.internal.assertions.http.between;

import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Range;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractHttpStatusBetweenTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	void it_should_pass_with_status_in_bounds() {
		Range range = getRange();
		for (int i = range.getStart(); i <= range.getEnd(); i++) {
			AssertionResult result = run(newResponse(i));
			checkSuccess(result);
		}
	}

	@Test
	void it_should_fail_with_response_not_in_bounds() {
		Range range = getRange();
		int start = range.getStart();
		int end = range.getEnd();

		for (int status = 100; status <= 599; status++) {
			if (status >= start && status <= end) {
				continue;
			}

			AssertionResult result = run(newResponse(status));

			checkError(
				result,
				String.format("Expecting status code to be between %s and %s but was %s", start, end, status)
			);
		}
	}

	@Test
	void it_should_fail_if_response_is_null() {
		AssertionResult result = run(null);
		checkError(result, "Expecting HTTP Response not to be null");
	}

	abstract Range getRange();

	private static HttpResponse newResponse(int status) {
		return new HttpResponseBuilderImpl().setStatus(status).build();
	}
}
