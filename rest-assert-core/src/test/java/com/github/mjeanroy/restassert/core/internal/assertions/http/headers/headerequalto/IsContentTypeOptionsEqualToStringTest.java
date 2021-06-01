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
import com.github.mjeanroy.restassert.core.internal.exceptions.InvalidHeaderValue;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.X_CONTENT_TYPE_OPTIONS;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IsContentTypeOptionsEqualToStringTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = X_CONTENT_TYPE_OPTIONS;
	private static final String NAME = HEADER.getName();
	private static final String VALUE = X_CONTENT_TYPE_OPTIONS.getValue();

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected AssertionResult run(HttpResponse response) {
		return assertions.isContentTypeOptionsEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return true;
	}

	@Override
	public void it_should_fail_with_if_response_does_not_contain_header_with_expected_value() {
		// Can't provide failed value since only "nosniff" is a valid value.
	}

	@Test
	public void it_should_compare_values_case_insensitively() {
		// GIVEN
		String actual = VALUE.toLowerCase();
		String expected = VALUE.toUpperCase();
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN
		AssertionResult result = assertions.isContentTypeOptionsEqualTo(response, expected);

		// THEN
		checkSuccess(result);
	}

	@Test
	public void it_should_fail_with_non_authorized_value() {
		// GIVEN
		String actual = VALUE.toLowerCase();
		String expected = "no-sniff";
		HttpResponse response = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN, THEN
		assertThatThrownBy(isContentTypeOptionsEqualTo(response, expected))
				.isExactlyInstanceOf(InvalidHeaderValue.class)
				.hasMessage("X-Content-Type-Options value 'no-sniff' is not a valid one.");
	}

	private ThrowingCallable isContentTypeOptionsEqualTo(final HttpResponse response, final String expected) {
		return new ThrowingCallable() {
			@Override
			public void call() {
				assertions.isContentTypeOptionsEqualTo(response, expected);
			}
		};
	}
}
