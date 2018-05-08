/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
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
import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Test;

import static com.github.mjeanroy.restassert.test.fixtures.TestHeaders.LOCATION;

public class IsLocationEqualToTest extends AbstractHttpHeaderEqualToTest {

	private static final Header HEADER = LOCATION;
	private static final String VALUE = HEADER.getValue();
	private static final String NAME = HEADER.getName();

	@Override
	protected Header getHeader() {
		return HEADER;
	}

	@Override
	protected AssertionResult invoke(HttpResponse response) {
		return assertions.isLocationEqualTo(response, VALUE);
	}

	@Override
	protected boolean allowMultipleValues() {
		return false;
	}

	@Test
	public void it_should_fail_with_same_value_but_different_case() {
		// GIVEN
		final String expected = VALUE.toUpperCase();
		final String actual = VALUE.toLowerCase();
		final HttpResponse httpResponse = new HttpResponseBuilderImpl().addHeader(NAME, actual).build();

		// WHEN
		final AssertionResult result = assertions.isLocationEqualTo(httpResponse, expected);

		// THEN
		final Class<ShouldHaveHeader> klassError = ShouldHaveHeader.class;
		final String message = "Expecting response to have header %s equal to %s but was %s";
		final Object[] args = {NAME, expected, actual};
		checkError(result, klassError, message, args);
	}
}
