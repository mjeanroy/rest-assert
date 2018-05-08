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

import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveHeader;
import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveSingleHeader;
import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.restassert.test.data.Header.header;
import static java.util.Arrays.asList;

/**
 * Test that http response contains expected header with expected value.
 * Each sub-class must specify which header has to be tested.
 */
public abstract class AbstractHttpHeaderEqualToTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_expected_header() {
		// GIVEN
		final Header header = getHeader();
		final HttpResponse rsp = newResponse(header);

		// WHEN
		final AssertionResult result = invoke(rsp);

		// THEN
		checkSuccess(result);
	}

	@Test
	public void it_should_pass_with_expected_header_and_list_of_values() {
		// GIVEN
		final Header h1 = getHeader();
		final Header h2 = header(h1.getName(), h1.getValue() + "foobar");
		final HttpResponse rsp = newResponse(h1, h2);
		final boolean allowMultiple = allowMultipleValues();

		// WHEN
		final AssertionResult result = invoke(rsp);

		// THEN
		if (allowMultiple) {
			checkSuccess(result);
		} else {
			final Class<ShouldHaveSingleHeader> klassError = ShouldHaveSingleHeader.class;
			final String message = "Expecting response to contains header %s with a single value but found: %s";
			final Object[] args = {h1.getName(), asList(h1.getValue(), h2.getValue())};
			checkError(result, klassError, message, args);
		}
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header_with_expected_value() {
		// GIVEN
		final Header expectedHeader = getHeader();
		final String expectedName = expectedHeader.getName();
		final String actualValue = failValue();
		final Header header = header(expectedName, actualValue);
		final HttpResponse rsp = newResponse(header);

		// WHEN
		final AssertionResult result = invoke(rsp);

		// THEN
		final String message = "Expecting response to have header %s equal to %s but was %s";
		final Object[] args = {expectedHeader.getName(), expectedHeader.getValue(), header.getValue()};
		checkError(result, ShouldHaveHeader.class, message, args);
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header() {
		// GIVEN
		final Header header = header("foo", "bar");
		final HttpResponse rsp = newResponse(header);

		// WHEN
		final AssertionResult result = invoke(rsp);

		// THEN
		final String message = "Expecting response to have header %s";
		final String args = getHeader().getName();
		checkError(result, ShouldHaveHeader.class, message, args);
	}

	/**
	 * Create fake response with list of headers.
	 *
	 * @param header Header, required.
	 * @param other Other, optional, headers.
	 * @return Fake http response.
	 */
	private HttpResponse newResponse(Header header, Header... other) {
		HttpResponseBuilderImpl builder = new HttpResponseBuilderImpl().addHeader(header);
		for (Header h : other) {
			builder.addHeader(h.getName(), h.getValue());
		}

		return builder.build();
	}

	/**
	 * Get expected header (<strong>must be implemented by each tests.</strong>).
	 *
	 * @return The header.
	 */
	protected abstract Header getHeader();

	/**
	 * If multiple headers with same name is allowed.
	 *
	 * @return {@code true} if multiple values is allowed, {@code false} otherwise.
	 */
	protected abstract boolean allowMultipleValues();

	/**
	 * Get fail value (used in test where header should not match actual value).
	 *
	 * @return Failed value.
	 */
	String failValue() {
		return getHeader().getValue() + "foo";
	}
}
