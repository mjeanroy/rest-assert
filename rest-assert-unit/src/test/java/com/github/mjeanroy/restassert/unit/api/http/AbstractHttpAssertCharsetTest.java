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
import com.github.mjeanroy.restassert.unit.api.TestInvocation;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

public abstract class AbstractHttpAssertCharsetTest<T> extends AbstractHttpAssertTest<T> {

	/**
	 * The custom message used as first parameter when optional message
	 * is specified in assertion.
	 */
	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_with_expected_mime_type() {
		invoke(newHttpResponse(getCharset()));
		invoke(CUSTOM_MESSAGE, newHttpResponse(getCharset()));
	}

	@Test
	public void it_should_fail_with_if_response_is_not_expected_mime_type() {
		invokeFailure(null, new TestInvocation<String>() {
			@Override
			public void invokeTest(String charset) {
				invoke(newHttpResponse(charset));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_is_not_expected_mime_type() {
		invokeFailure(CUSTOM_MESSAGE, new TestInvocation<String>() {
			@Override
			public void invokeTest(String charset) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(charset));
			}
		});
	}

	/**
	 * Invoke test with failure case.
	 *
	 * @param msg Expected error message, optional and may be {@code null}.
	 * @param invocation The test invocation.
	 */
	private void invokeFailure(String msg, final TestInvocation<String> invocation) {
		final String expectedCharset = getCharset();
		final String actualCharset = expectedCharset + "foo";
		final String message = firstNonNull(msg, buildErrorMessage(expectedCharset, actualCharset));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(actualCharset);
			}
		});
	}

	/**
	 * Get the charset to be tested.
	 *
	 * @return Charset value.
	 */
	protected abstract String getCharset();

	/**
	 * Create expected error message when test fail.
	 *
	 * @param expectedCharset Expected charset.
	 * @param actualCharset Actual charset in HTTP response.
	 * @return Expected error message.
	 */
	private String buildErrorMessage(String expectedCharset, String actualCharset) {
		return String.format("Expecting response to have charset %s but was %s", expectedCharset, actualCharset);
	}

	/**
	 * Create the HTTP response to be tested.
	 *
	 * @param charset The HTTP response charset.
	 * @return The HTTP response.
	 */
	private T newHttpResponse(String charset) {
		String contentType = String.format("application/json;charset=%s", charset);
		return getBuilder().addHeader("Content-Type", contentType).build();
	}
}
