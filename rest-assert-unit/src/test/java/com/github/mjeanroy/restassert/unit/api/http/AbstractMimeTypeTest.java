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

package com.github.mjeanroy.restassert.unit.api.http;

import com.github.mjeanroy.restassert.tests.Function;
import com.github.mjeanroy.restassert.test.data.Header;
import com.github.mjeanroy.restassert.unit.api.TestInvocation;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.test.data.Header.header;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractMimeTypeTest<T> extends AbstractHttpAssertTest<T> {

	/**
	 * The custom message used as first parameter when optional message
	 * is specified in assertion.
	 */
	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_with_expected_mime_type() {
		Header header = getHeader();
		invoke(newHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newHttpResponse(header));
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		doTest(null, new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				invoke(newHttpResponse(header));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		doTest(CUSTOM_MESSAGE, new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(header));
			}
		});
	}

	/**
	 * Invoke test with a fail test case.
	 *
	 * @param msg The custom error message, optional and may be {@code null}.
	 * @param invocation The test invocation.
	 */
	private void doTest(String msg, final TestInvocation<Header> invocation) {
		final Header expectedHeader = getHeader();

		final String expectedMimeType = getMimeType();
		final String actualMimeType = expectedMimeType + "foo";

		final String expectedName = expectedHeader.getName();
		final String expectedValue = expectedHeader.getValue();
		final String actualValue = expectedValue.replace(expectedMimeType, actualMimeType);
		final Header header = header(expectedName, actualValue);

		final String message = firstNonNull(msg, buildErrorMessage(expectedMimeType, actualMimeType));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(header);
			}
		});
	}

	/**
	 * Get expected mime type to be tested.
	 *
	 * @return Mime type.
	 */
	protected abstract String getMimeType();

	/**
	 * Generate header from mime type value.
	 *
	 * @return The header.
	 */
	private Header getHeader() {
		return header("Content-Type", getMimeType() + ";charset=UTF-8");
	}

	/**
	 * Get expected default error message.
	 *
	 * @param expectedMimeType Expected mime type.
	 * @param actualMimeType The HTTP response actual mime type.
	 * @return The expected default message.
	 */
	private String buildErrorMessage(String expectedMimeType, String actualMimeType) {
		return String.format("Expecting response to have mime type %s but was %s", expectedMimeType, actualMimeType);
	}

	/**
	 * Get the HTTP response to be tested.
	 *
	 * @param header HTTP Response header.
	 * @return The HTTP response.
	 */
	private T newHttpResponse(Header header) {
		return getBuilder().addHeader(header).build();
	}
}
