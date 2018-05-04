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
import com.github.mjeanroy.restassert.core.internal.common.Collections.Mapper;
import com.github.mjeanroy.restassert.core.internal.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.mjeanroy.restassert.core.internal.common.Collections.map;
import static com.github.mjeanroy.restassert.test.data.Header.header;

public abstract class AbstractMimeTypeInTest extends AbstractAssertionsTest<HttpResponse> {

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
		List<String> mimeTypes = map(getMimeTypes(), new Mapper<String, String>() {
			@Override
			public String apply(String input) {
				return input.toLowerCase();
			}
		});

		for (Header header : getHeaders(mimeTypes)) {
			final HttpResponse httpResponse = newResponse(header);
			final AssertionResult result = invoke(httpResponse);
			checkSuccess(result);
		}
	}

	@Test
	public void it_should_pass_with_expected_mime_type_in_a_different_case() {
		List<String> mimeTypes = map(getMimeTypes(), new Mapper<String, String>() {
			@Override
			public String apply(String input) {
				return input.toUpperCase();
			}
		});

		for (Header header : getHeaders(mimeTypes)) {
			final HttpResponse httpResponse = newResponse(header);
			final AssertionResult result = invoke(httpResponse);
			checkSuccess(result);
		}
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		final List<String> mimeTypes = getMimeTypes();
		final List<Header> headers = getHeaders(mimeTypes);
		final List<String> mimeType = getMimeTypes();

		int i = 0;
		for (Header h : headers) {
			// Given
			final String expectedMimeType = mimeType.get(i);
			final String actualMimeType = expectedMimeType + "foo";

			final String expectedName = h.getName();
			final String expectedValue = h.getValue();
			final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
			final Header header = header(expectedName, actualValue);

			// When
			final AssertionResult result = invoke(newResponse(header));

			// Then
			final Class<ShouldHaveMimeType> klassError = ShouldHaveMimeType.class;
			final String pattern = "Expecting response to have mime type in %s but was %s";
			final Object[] parameters = {mimeType, actualMimeType};
			checkError(result, klassError, pattern, parameters);

			i++;
		}
	}

	/**
	 * Get the list of mime-type to be tested.
	 *
	 * @return The list of mime-type.
	 */
	protected abstract List<String> getMimeTypes();

	/**
	 * Create HTTP response with given header.
	 *
	 * @param header The header.
	 * @return The HTTP response.
	 */
	private HttpResponse newResponse(Header header) {
		return new HttpResponseBuilderImpl().addHeader(header).build();
	}

	/**
	 * Create all {@code "Content-Type"} headers corresponding to the list
	 * of mime-type to be tested.
	 *
	 * @return All {@code "Content-Type"} headers.
	 */
	private List<Header> getHeaders(List<String> mimeTypes) {
		return map(mimeTypes, new Mapper<String, Header>() {
			@Override
			public Header apply(String input) {
				return header("Content-Type", input + ";charset=UTF-8");
			}
		});
	}
}
