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

package com.github.mjeanroy.restassert.core.internal.assertions.http.mimetype;

import com.github.mjeanroy.restassert.core.internal.assertions.AbstractAssertionsTest;
import com.github.mjeanroy.restassert.core.internal.assertions.AssertionResult;
import com.github.mjeanroy.restassert.core.internal.assertions.HttpResponseAssertions;
import com.github.mjeanroy.restassert.core.data.HttpResponse;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.tests.builders.HttpResponseBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mjeanroy.restassert.test.commons.StringTestUtils.fmt;
import static com.github.mjeanroy.restassert.test.data.Header.header;

abstract class AbstractMimeTypeTest extends AbstractAssertionsTest<HttpResponse> {

	HttpResponseAssertions assertions;

	@BeforeEach
	void setUp() {
		assertions = HttpResponseAssertions.instance();
	}

	@Test
	void it_should_pass_with_expected_mime_type() {
		String mimeType = getMimeType().toLowerCase();
		Header header = getHeader(mimeType);
		HttpResponse httpResponse = newResponse(header);
		AssertionResult result = run(httpResponse);
		checkSuccess(result);
	}

	@Test
	void it_should_pass_with_expected_mime_type_in_a_different_case() {
		String mimeType = getMimeType().toUpperCase();
		Header header = getHeader(mimeType);
		HttpResponse httpResponse = newResponse(header);
		AssertionResult result = run(httpResponse);
		checkSuccess(result);
	}

	@Test
	void it_should_fail_with_if_response_is_not_expected_mime_type() {
		String expectedMimeType = getMimeType();
		Header expectedHeader = getHeader(expectedMimeType);
		String actualMimeType = expectedMimeType + "foo";

		String expectedName = expectedHeader.getName();
		String expectedValue = expectedHeader.getValue();
		String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
		Header actualHeader = header(expectedName, actualValue);
		HttpResponse httpResponse = newResponse(actualHeader);

		AssertionResult result = run(httpResponse);

		checkError(
			result,
			"Expecting response to have mime type " + fmt(expectedMimeType) + " but was " + fmt(actualMimeType)
		);
	}

	@Test
	void it_should_fail_if_response_is_null() {
		AssertionResult result = run(null);
		checkError(result, "Expecting HTTP Response not to be null");
	}

	abstract String getMimeType();

	private static HttpResponse newResponse(Header header) {
		return new HttpResponseBuilderImpl().addHeader(header).build();
	}

	private static Header getHeader(String mimeType) {
		String value = mimeType + ";charset=UTF-8";
		return header("Content-Type", value);
	}
}
