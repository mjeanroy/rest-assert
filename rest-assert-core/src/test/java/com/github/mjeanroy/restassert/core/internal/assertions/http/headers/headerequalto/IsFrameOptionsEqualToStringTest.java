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
import com.github.mjeanroy.restassert.core.internal.exceptions.InvalidHeaderValue;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.X_FRAME_OPTIONS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IsFrameOptionsEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = X_FRAME_OPTIONS;
	private static final String VALUE = HEADER.getValue();
	private static final String NAME = HEADER.getName();

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isFrameOptionsEqualTo(response, VALUE);
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
		return "sameorigin";
	}

	@Test
	void it_should_parse_deny_case_insensitively() {
		String actual = "DENY";
		String expected = "deny";
		doTest(actual, expected);
	}

	@Test
	void it_should_parse_sameorigin_case_insensitively() {
		String actual = "SAMEORIGIN";
		String expected = "sameorigin";
		doTest(actual, expected);
	}

	@Test
	void it_should_parse_allowfrom_case_insensitively() {
		String actual = "ALLOW-FROM https://example.com";
		String expected = "allow-from https://example.com";
		doTest(actual, expected);
	}

	@Test
	void it_should_fail_with_invalid_value() {
		String actual = "sameorigin";
		String expected = "same-origin";
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		assertThatThrownBy(() -> assertions.isFrameOptionsEqualTo(response, expected))
			.isExactlyInstanceOf(InvalidHeaderValue.class)
			.hasMessage("X-Frame-Options value 'same-origin' is not a valid one.");
	}

	private static void doTest(String actual, String expected) {
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();
		AssertionResult result = assertions.isFrameOptionsEqualTo(response, expected);
		checkSuccess(result);
	}
}
