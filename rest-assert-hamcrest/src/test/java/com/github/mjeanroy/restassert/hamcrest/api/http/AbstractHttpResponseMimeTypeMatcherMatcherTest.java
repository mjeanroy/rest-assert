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

package com.github.mjeanroy.restassert.hamcrest.api.http;

import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.test.tests.TestInvocation;
import com.github.mjeanroy.restassert.tests.Function;
import org.junit.Test;

import static com.github.mjeanroy.restassert.hamcrest.tests.HamcrestTestUtils.generateHamcrestErrorMessage;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;

public abstract class AbstractHttpResponseMimeTypeMatcherMatcherTest<T> extends AbstractHttpResponseMatcherTest<T> {

	@Test
	public void it_should_pass_with_expected_mime_type() {
		Header header = getHeader();
		run(newHttpResponse(header));
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		doTest(new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				run(newHttpResponse(header));
			}
		});
	}

	private void doTest(final TestInvocation<Header> invocation) {
		final Header expectedHeader = getHeader();

		final String expectedMimeType = getMimeType();
		final String actualMimeType = expectedMimeType + "foo";

		final String expectedName = expectedHeader.getName();
		final String expectedValue = expectedHeader.getValue();
		final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
		final Header header = header(expectedName, actualValue);

		final String message = generateHamcrestErrorMessage(
			buildExpectationMessage(expectedMimeType),
			buildMismatchMessage(actualMimeType)
		);

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(header);
			}
		});
	}

	protected abstract String getMimeType();

	private Header getHeader() {
		return header("Content-Type", getMimeType() + ";charset=UTF-8");
	}

	private T newHttpResponse(Header header) {
		return getBuilder().addHeader(header).build();
	}

	private static String buildExpectationMessage(String expectedMimeType) {
		return String.format("Expecting response to have mime type %s", expectedMimeType);
	}

	private static String buildMismatchMessage(String actualMimeType) {
		return String.format("was %s", actualMimeType);
	}
}
