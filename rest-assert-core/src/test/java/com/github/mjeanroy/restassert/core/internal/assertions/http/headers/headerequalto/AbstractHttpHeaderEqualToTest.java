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

import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static java.util.Arrays.asList;

abstract class AbstractHttpHeaderEqualToTest extends AbstractAssertionsTest<HttpResponse> {

	static HttpResponseAssertions assertions;

	@BeforeAll
	static void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	void it_should_pass_with_expected_header() {
		// GIVEN
		Header header = getHeader();
		HttpResponse rsp = newResponse(header);

		// WHEN
		AssertionResult result = run(rsp);

		// THEN
		checkSuccess(result);
	}

	@Test
	void it_should_pass_with_expected_header_and_list_of_values() {
		// GIVEN
		Header h1 = getHeader();
		Header h2 = header(h1.getName(), h1.getValue() + "foobar");
		HttpResponse rsp = newResponse(h1, h2);
		boolean allowMultiple = allowMultipleValues();

		// WHEN
		AssertionResult result = run(rsp);

		// THEN
		if (allowMultiple) {
			checkSuccess(result);
		}
		else {
			checkError(
				result,
				"Expecting response to contains header " + fmt(h1.getName()) + " with a single value " +
					"but found: " + asList(fmt(h1.getValue()), fmt(h2.getValue()))
			);
		}
	}

	@Test
	void it_should_fail_with_if_response_does_not_contain_header_with_expected_value() {
		// GIVEN
		Header expectedHeader = getHeader();
		String expectedName = expectedHeader.getName();
		String actualValue = failValue();
		Header header = header(expectedName, actualValue);
		HttpResponse rsp = newResponse(header);

		// WHEN
		AssertionResult result = run(rsp);

		// THEN
		checkError(
			result,
			"Expecting response to have header " + fmt(expectedHeader.getName()) + " equal to " + fmt(expectedHeader.getValue()) + " but was " + fmt(header.getValue())
		);
	}

	@Test
	void it_should_fail_with_if_response_does_not_contain_header() {
		// GIVEN
		Header header = header("foo", "bar");
		HttpResponse rsp = newResponse(header);

		// WHEN
		AssertionResult result = run(rsp);

		// THEN
		checkError(
			result,
			"Expecting response to have header " + fmt(getHeader().getName())
		);
	}

	private HttpResponse newResponse(Header header, Header... other) {
		HttpResponseBuilderImpl builder = new HttpResponseBuilderImpl().addHeader(header);
		for (Header h : other) {
			builder.addHeader(h.getName(), h.getValue());
		}

		return builder.build();
	}

	abstract Header getHeader();

	abstract boolean allowMultipleValues();

	String failValue() {
		return getHeader().getValue() + "foo";
	}
}
