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
import com.github.mjeanroy.restassert.tests.models.Header;
import com.github.mjeanroy.restassert.unit.api.TestInvocation;
import org.junit.Test;

import static com.github.mjeanroy.restassert.tests.AssertionUtils.assertFailure;
import static com.github.mjeanroy.restassert.tests.models.Header.header;
import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;

public abstract class AbstractHasHttpHeaderTest<T> extends AbstractHttpAssertTest<T> {

	/**
	 * The custom message used as first parameter when optional message
	 * is specified in assertion.
	 */
	private static final String CUSTOM_MESSAGE = "foo";

	@Test
	public void it_should_pass_with_expected_header() {
		Header header = getHeader();
		invoke(newHttpResponse(header));
		invoke(CUSTOM_MESSAGE, newHttpResponse(header));
	}

	@Test
	public void it_should_fail_with_if_response_does_not_contain_header() {
		doTest(null, new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				invoke(newHttpResponse(header));
			}
		});
	}

	@Test
	public void it_should_fail_with_custom_message_if_response_does_not_contain_header() {
		doTest(CUSTOM_MESSAGE, new TestInvocation<Header>() {
			@Override
			public void invokeTest(Header header) {
				invoke(CUSTOM_MESSAGE, newHttpResponse(header));
			}
		});
	}

	/**
	 * Execute test with a failed test case.
	 *
	 * @param msg The customized error message, optional and may be {@code null}.
	 * @param invocation The test invocation.
	 */
	private void doTest(String msg, final TestInvocation<Header> invocation) {
		final Header expectedHeader = getHeader();
		final Header header = header("foo", "bar");
		final String message = firstNonNull(msg, buildErrorMessage(expectedHeader));

		assertFailure(message, new Function() {
			@Override
			public void apply() {
				invocation.invokeTest(header);
			}
		});
	}

	/**
	 * Get header that will be tested.
	 * Note that header value does not really matter, only name is important here.
	 *
	 * @return Header.
	 */
	protected abstract Header getHeader();

	/**
	 * Get expected error message when HTTP response does not have expected header.
	 *
	 * @param expectedHeader Header.
	 * @return Error message.
	 */
	private String buildErrorMessage(Header expectedHeader) {
		return format("Expecting response to have header %s", expectedHeader.getName());
	}

	/**
	 * Get the HTTP response to be tested.
	 *
	 * @param header Expected header.
	 * @return The HTTP response.
	 */
	private T newHttpResponse(Header header) {
		return getBuilder().addHeader(header).build();
	}
}