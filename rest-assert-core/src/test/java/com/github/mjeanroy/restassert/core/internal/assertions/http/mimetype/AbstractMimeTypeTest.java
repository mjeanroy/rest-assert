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

package com.github.mjeanroy.restassert.core.internal.assertions.http.mimetype;

import com.github.mjeanroy.restassert.core.internal.error.http.ShouldHaveMimeType;
import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import com.github.mjeanroy.restassert.test.data.Header;
import org.junit.Before;
import org.junit.Test;

import static com.github.mjeanroy.restassert.test.data.Header.header;

public abstract class AbstractMimeTypeTest extends AbstractAssertionsTest<HttpResponse> {

	/**
	 * The assertion object.
	 */
	HttpResponseAssertions assertions;

	@Before
	public void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	public void it_should_pass_with_expected_mime_type() {
		// Given
		final String mimeType = getMimeType().toLowerCase();
		final Header header = getHeader(mimeType);
		final HttpResponse httpResponse = newResponse(header);

		// When
		final AssertionResult result = invoke(httpResponse);

		// Then
		checkSuccess(result);
	}

	@Test
	public void it_should_pass_with_expected_mime_type_in_a_different_case() {
		// Given
		final String mimeType = getMimeType().toUpperCase();
		final Header header = getHeader(mimeType);
		final HttpResponse httpResponse = newResponse(header);

		// When
		final AssertionResult result = invoke(httpResponse);

		// Then
		checkSuccess(result);
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		// Given
		final String expectedMimeType = getMimeType();
		final Header expectedHeader = getHeader(expectedMimeType);
		final String actualMimeType = expectedMimeType + "foo";

		final String expectedName = expectedHeader.getName();
		final String expectedValue = expectedHeader.getValue();
		final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
		final Header actualHeader = header(expectedName, actualValue);
		final HttpResponse httpResponse = newResponse(actualHeader);

		// When
		final AssertionResult result = invoke(httpResponse);

		// Then
		final Class<ShouldHaveMimeType> klassError = ShouldHaveMimeType.class;
		final String pattern = "Expecting response to have mime type %s but was %s";
		final Object[] parameters = {expectedMimeType, actualMimeType};
		checkError(result, klassError, pattern, parameters);
	}

	/**
	 * Get the mime-type to be tested.
	 *
	 * @return Mime-Type to be tested.
	 */
	protected abstract String getMimeType();

	/**
	 * Create new HTTP with given header.
	 *
	 * @param header The header.
	 * @return The HTTP response.
	 */
	private HttpResponse newResponse(Header header) {
		return new HttpResponseBuilderImpl().addHeader(header).build();
	}

	/**
	 * Generate {@code "Content-Type"} header with mime-type to be tested.
	 *
	 * @param mimeType Mime-Type to be tested.
	 * @return The {@code "Content-Type} header.
	 */
	private Header getHeader(String mimeType) {
		String value = mimeType + ";charset=UTF-8";
		return header("Content-Type", value);
	}
}
